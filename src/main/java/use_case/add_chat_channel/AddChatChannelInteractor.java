package use_case.add_chat_channel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.access.UserDataAccessObject;
import entity.DirectChatChannel;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import sendbirdapi.ChannelCreator;
import session.Session;

/**
 * The interactor for the add chat channel use case.
 */
public class AddChatChannelInteractor implements AddChatChannelInputBoundary {
    private AddChatChannelOutputBoundary presenter;
    private AddChatChannelDataAccessInterface chatChannelDataAccess;
    private UserDataAccessObject userDataAccess;
    private Session sessionManager;
    private ChannelCreator channelCreator;
    private String url = "";

    private final Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    public AddChatChannelInteractor(AddChatChannelOutputBoundary presenter,
                                    AddChatChannelDataAccessInterface chatChannelDataAccess,
                                    UserDataAccessObject userDataAccess,
                                    Session sessionManager, ChannelCreator channelCreator) {
        this.presenter = presenter;
        this.chatChannelDataAccess = chatChannelDataAccess;
        this.userDataAccess = userDataAccess;
        this.sessionManager = sessionManager;
        this.channelCreator = channelCreator;
    }

    @Override
    public void createChannel(AddChatChannelInputData request) throws SQLException {
        final User currentUser = sessionManager.getMainUser();
        final User toAdd = userDataAccess.getUserFromID(request.getReceiverID());
        final List<Integer> contactIds = currentUser.getContactIds();

        // create response model for any new info needed for view
        final AddChatChannelOutputData response = new AddChatChannelOutputData(
                request.getChatName(),
                "",
                request.getSenderID(),
                request.getReceiverID(),
                contactIds);

        final List<String> currentUserChannels = chatChannelDataAccess.getChatUrlsByUserId(currentUser.getUserID());
        final List<String> toAddUserChannels = chatChannelDataAccess.getChatUrlsByUserId(toAdd.getUserID());
        boolean newChat = true;

        // check if chat is already made with contact user
        for (String currentUserChannel : currentUserChannels) {
            for (String toAddUserChannel : toAddUserChannels) {
                if (currentUserChannel.equals(toAddUserChannel)) {
                    newChat = false;
                }
            }
        }
        if (newChat) {
            // create direct channel entity
            final String chatUrl = channelCreator.sendbirdChannelCreator(dotenv.get("MSG_TOKEN"), request.getChatName(),
                    request.getSenderID(), request.getReceiverID());
            System.out.println("chatUrl: " + chatUrl);
            final DirectChatChannel newChannel = new DirectChatChannel(request.getChatName(),
                    currentUser, toAdd, chatUrl, new ArrayList<>());

            url = chatUrl;

            // add channel entity to db
            chatChannelDataAccess.addChat(newChannel);
        }
        else {
            newChat = false;
        }

        response.setChatUrl(url);
        response.setNewChat(newChat);
        // call presenter to update view
        presenter.presentChat(response);
    }
}
