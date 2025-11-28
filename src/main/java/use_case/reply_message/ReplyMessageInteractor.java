package use_case.reply_message;

import SendBirdAPI.MessageSender;
import data_access.MessageDataAccessObject;
import data_access.UserDataAccessObject;
import entity.Message;
import entity.MessageFactory;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import session.Session;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReplyMessageInteractor implements ReplyMessageInputBoundary {
    private ReplyMessageOutputBoundary presenter;
    private MessageDataAccessObject messageDataAccessObject;
    private MessageSender messageSender;
    private Session sessionManager;
    private UserDataAccessObject userDataAccessObject;
    private Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();;

    public ReplyMessageInteractor(ReplyMessageOutputBoundary presenter,
                                  MessageDataAccessObject messageDataAccessObject,
                                  MessageSender messageReplier,
                                  Session sessionManager,
                                  UserDataAccessObject userDataAccessObject) {
        this.presenter = presenter;
        this.messageDataAccessObject = messageDataAccessObject;
        this.messageSender = messageSender;
        this.sessionManager = sessionManager;
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void execute(ReplyMessageInputData inputData) {
        final User currentUser = sessionManager.getMainUser();

        Long messageId = messageSender.sendMessage(dotenv.get("MSG_TOKEN"), inputData.getMessage(),
                inputData.getChannelUrl(), inputData.getSenderId());
        if (messageId == null) {
            presenter.prepareReplyMessageFailView("Sendbird write fail");
            return;
        }

        Message message = MessageFactory.createTextMessage(
            messageId, inputData.getParentMessageId(), inputData.getChannelUrl(),
            currentUser.getUserID(), inputData.getReceiverId(), "pending", Timestamp.valueOf(LocalDateTime.now()), inputData.getMessage()
        );

        final Long childMessageId;

        try {
            childMessageId = messageDataAccessObject.addMessage(message);
        } catch (SQLException e) {
            presenter.prepareReplyMessageFailView(e.getMessage());
            return;
        }

        ReplyMessageOutputData outputData = new ReplyMessageOutputData(
                inputData.getParentMessageId(), childMessageId,
                inputData.getMessage(), inputData.getChannelUrl(),
                inputData.getSenderId(), inputData.getReceiverId(),
                message.getTimestamp()
        );

        presenter.prepareReplyMessageSuccessView(outputData);
    }
}
