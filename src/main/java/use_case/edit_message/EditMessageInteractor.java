package use_case.edit_message;

import entity.Message;
import io.github.cdimascio.dotenv.Dotenv;
import SendBirdAPI.MessageEditor;

import java.sql.SQLException;
import java.sql.Timestamp;

public class EditMessageInteractor implements EditMessageInputBoundary {
    private final EditMessageOutputBoundary presenter;
    private final EditMessageDataAccessInterface messageDataAccessObject;
    private final MessageEditor messageEditor;

    private final Dotenv dotenv =  Dotenv.configure()
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

        String status = messageEditor.editMessage(dotenv.get("MSG_TOKEN"), newMessage, channelUrl, messageId);

        if (status.equals("fail")) {
            presenter.prepareEditMessageFailView("Sendbird write fail");
            return;
        }

        Timestamp oldTimestamp;
        try {
            oldTimestamp = messageDataAccessObject.editMessage(newMessage, channelUrl, messageId);
        } catch (SQLException e) {
            presenter.prepareEditMessageFailView("DB write fail");
            return;
        }

        Message<String> message;
        try {
            message = messageDataAccessObject.getMessageFromID(messageId);
        } catch (SQLException e) {
            presenter.prepareEditMessageFailView("DB read fail");
            return;
        }

        EditMessageOutputData outputData = new EditMessageOutputData(message.getMessageID(),
                message.getChannelUrl(),
                message.getContent(),
                message.getSenderId(),
                message.getReceiverId(),
                oldTimestamp,
                message.getTimestamp());

        presenter.prepareEditMessageSuccessView(outputData);



    }
}
