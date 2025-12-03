package use_case.send_message;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import entity.MessageFactory;
import entity.TextMessage;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import session.Session;

/**
 * Interactor for the send message use case.
 */
public class SendMessageInteractor implements SendMessageInputBoundary {
    private final SendMessageOutputBoundary presenter;
    private final SendMessageDataAccessInterface messageDataAccessObject;
    private final Session sessionManager;
    private final SendMessageApiAccessInterface messageSender;
    private final Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    public SendMessageInteractor(SendMessageOutputBoundary presenter,
                                 SendMessageDataAccessInterface messageDataAccessObject,
                                 Session sessionManager,
                                 SendMessageApiAccessInterface messageSender) {
        this.presenter = presenter;
        this.messageDataAccessObject = messageDataAccessObject;
        this.sessionManager = sessionManager;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(SendMessageInputData request) {
        final User currentUser = sessionManager.getMainUser();

        final Long messageId = messageSender.sendMessage(request.getMessage(),
                dotenv.get("MSG_TOKEN"),
                request.getChannelUrl(),
                currentUser.getUserID());

        final TextMessage message = MessageFactory.createTextMessage(messageId,
                request.getChannelUrl(),
                currentUser.getUserID(),
                request.getReceiverID(),
                "pending",
                Timestamp.valueOf(LocalDateTime.now()),
                request.getMessage());

        if (messageId == null) {
            presenter.prepareSendMessageFailView("sendbird write fail");
            return;
        }

        try {
            messageDataAccessObject.addMessage(message);
        }
        catch (SQLException ex) {
            presenter.prepareSendMessageFailView("DB write fail");
            return;
        }

        final SendMessageOutputData response = new SendMessageOutputData(
                messageId,
                currentUser.getUserID(),
                request.getReceiverID(),
                request.getChannelUrl(),
                request.getMessage(),
                message.getTimestamp()
        );

        presenter.prepareSendMessageSuccessView(response);
    }
}
