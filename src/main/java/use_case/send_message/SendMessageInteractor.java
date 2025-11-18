package use_case.send_message;

import SendBirdAPI.MessageSender;
import data_access.ChatChannelDataAccessObject;
import data_access.MessageDataAccessObject;
import data_access.UserDataAccessObject;
import entity.DirectChatChannel;
import entity.Message;
import entity.MessageFactory;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import session.Session;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SendMessageInteractor implements SendMessageInputBoundary {
    private SendMessageOutputBoundary presenter;
    private UserDataAccessObject userDataAccessObject;
    private ChatChannelDataAccessObject chatChannelDataAccessObject;
    private MessageDataAccessObject messageDataAccessObject;
    private Session sessionManager;
    private MessageSender messageSender;
    private Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    public SendMessageInteractor(SendMessageOutputBoundary presenter,
                                 UserDataAccessObject userDataAccessObject,
                                 ChatChannelDataAccessObject chatChannelDataAccessObject,
                                 MessageDataAccessObject messageDataAccessObject,
                                 Session sessionManager,
                                 MessageSender messageSender) {
        this.presenter = presenter;
        this.userDataAccessObject = userDataAccessObject;
        this.chatChannelDataAccessObject = chatChannelDataAccessObject;
        this.messageDataAccessObject = messageDataAccessObject;
        this.sessionManager = sessionManager;
        this.messageSender = messageSender;
    }


    @Override
    public void execute(SendMessageInputData request) {
        final User currentUser = sessionManager.getMainUser();
        Message lastMessage;
        User receiver;
        DirectChatChannel chatChannel;
        try {
            receiver = userDataAccessObject.getUserFromID(request.getReceiverID());
            chatChannel = chatChannelDataAccessObject.getDirectChatChannelByURL(
                    request.getChannelUrl());
            lastMessage = chatChannelDataAccessObject.getLastMessage(chatChannel.getChatURL());
        } catch (SQLException e) {
            presenter.prepareSendMessageFailView("DB read fail");
            return;
        }

        final Integer newID;

        if (lastMessage != null) {
            newID = lastMessage.getMessageID() + 1;
        } else {
            newID = 0;
        }

        Message<String> message = MessageFactory.createTextMessage(newID,
                request.getChannelUrl(),
                currentUser,
                receiver,
                "pending",
                Timestamp.valueOf(LocalDateTime.now()),
                request.getMessage());

        String status = messageSender.sendMessage(message.getContent(),
                dotenv.get("MSG_TOKEN"),
                request.getChannelUrl(),
                currentUser);

        if (status.equals("fail")) {
            presenter.prepareSendMessageFailView("sendbird write fail");
            return;
        }

        try {
            messageDataAccessObject.addMessage(message);
        } catch (SQLException e) {
            presenter.prepareSendMessageFailView("DB write fail");
            return;
        }

        try {
            chatChannel = chatChannelDataAccessObject.getDirectChatChannelByURL(request.getChannelUrl());
        } catch (SQLException e) {
            presenter.prepareSendMessageFailView("DB read fail");
            return;
        }

        SendMessageOutputData response = new SendMessageOutputData(
                chatChannel.getMessages(),
                chatChannel.getMessageIDs(),
                currentUser.getUserID(),
                receiver.getUserID(),
                request.getChannelUrl()
        );

        presenter.prepareSendMessageSuccessView(response);
    }
}
