package use_case.send_message;

import entity.Message;
import entity.MessageFactory;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import session.Session;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SendMessageInteractor implements SendMessageInputBoundary {
    private final SendMessageOutputBoundary presenter;
    private final SendMessageDataAccessInterface messageDataAccessObject;
    private final Session sessionManager;
    private final SendMessageAPIAccessInterface messageSender;
    private final Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    public SendMessageInteractor(SendMessageOutputBoundary presenter,
                                 SendMessageDataAccessInterface messageDataAccessObject,
                                 Session sessionManager,
                                 SendMessageAPIAccessInterface messageSender) {
        this.presenter = presenter;
        this.messageDataAccessObject = messageDataAccessObject;
        this.sessionManager = sessionManager;
        this.messageSender = messageSender;
    }


    @Override
    public void execute(SendMessageInputData request) {
        final User currentUser = sessionManager.getMainUser();

        Long messageId = messageSender.sendMessage(request.getMessage(),
                dotenv.get("MSG_TOKEN"),
                request.getChannelUrl(),
                currentUser.getUserID());

        Message<String> message = MessageFactory.createTextMessage(messageId,
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
        } catch (SQLException e) {
            presenter.prepareSendMessageFailView("DB write fail");
            return;
        }

        SendMessageOutputData response = new SendMessageOutputData(
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
