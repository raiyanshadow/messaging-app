package use_case.edit_message;

import java.sql.SQLException;
import java.sql.Timestamp;

import entity.AbstractMessage;
import io.github.cdimascio.dotenv.Dotenv;
import sendbirdapi.MessageEditor;

/**
 * Interactor for the edit message use case.
 */
public class EditMessageInteractor implements EditMessageInputBoundary {
    private final EditMessageOutputBoundary presenter;
    private final EditMessageDataAccessInterface messageDataAccessObject;
    private final MessageEditor messageEditor;

    private final Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    public EditMessageInteractor(EditMessageOutputBoundary presenter,
                                 EditMessageDataAccessInterface messageDataAccessObject,
                                 MessageEditor messageEditor) {
        this.presenter = presenter;
        this.messageDataAccessObject = messageDataAccessObject;
        this.messageEditor = messageEditor;
    }

    @Override
    public void execute(EditMessageInputData inputData) {
        final String newMessage = inputData.getNewMessage();
        final String channelUrl = inputData.getChannelUrl();
        final Long messageId = inputData.getMessageId();

        final String status = messageEditor.editMessage(dotenv.get("MSG_TOKEN"), newMessage, channelUrl, messageId);

        if ("fail".equals(status)) {
            presenter.prepareEditMessageFailView("Sendbird write fail");
            return;
        }

        final Timestamp oldTimestamp;
        try {
            oldTimestamp = messageDataAccessObject.editMessage(newMessage, channelUrl, messageId);
        }
        catch (SQLException ex) {
            presenter.prepareEditMessageFailView("DB write fail");
            return;
        }

        final AbstractMessage<String> message;
        try {
            message = messageDataAccessObject.getMessageFromID(messageId);
        }
        catch (SQLException ex) {
            presenter.prepareEditMessageFailView("DB read fail");
            return;
        }

        final EditMessageOutputData outputData = new EditMessageOutputData(message.getMessageID(),
                message.getChannelUrl(),
                message.getContent(),
                message.getSenderId(),
                message.getReceiverId(),
                oldTimestamp,
                message.getTimestamp());

        presenter.prepareEditMessageSuccessView(outputData);
    }
}
