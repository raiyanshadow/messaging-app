package use_case.login;

import data_access.ChatChannelDataAccessObject;
import entity.DirectChatChannel;
import entity.User;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccess;
    private final ChatChannelDataAccessObject chatChannelDataAccessObject;
    private final LoginOutputBoundary userPresenter;
    private baseUIViewModel baseUIViewModel;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccess, LoginOutputBoundary userPresenter,
                           ChatChannelDataAccessObject chatChannelDataAccessObject, baseUIViewModel baseUIViewModel) {
        this.userDataAccess = userDataAccess;
        this.userPresenter = userPresenter;
        this.chatChannelDataAccessObject = chatChannelDataAccessObject;
        this.baseUIViewModel = baseUIViewModel;
    }

    @Override
    public void logIn(LoginInputData data) {
        String username = data.getUsername();
        String password = data.getPassword();

        boolean isValid = false;
        try {
            isValid = userDataAccess.validateCredentials(username, password);
        } catch (SQLException e) {
            userPresenter.prepareFailureView("DB read fail");
        }

        if (isValid) {
            User user = null;
            try {
                user = userDataAccess.getUserFromName(username);
            } catch (SQLException e) {
                userPresenter.prepareFailureView("DB read fail");
                return;
            }

            List<String> chatUrls;
            try {
                chatUrls = chatChannelDataAccessObject.getChatURLsByUserId(user.getUserID());
            } catch (SQLException e) {
                userPresenter.prepareFailureView("DB read fail");
                return;
            }

            List<DirectChatChannel> chatEntities = new ArrayList<>();
            List<String> chatNames = new ArrayList<>();

            for (String url : chatUrls) {
                try {
                    DirectChatChannel channel = chatChannelDataAccessObject.getDirectChatChannelByURL(url);
                    chatEntities.add(channel);
                    chatNames.add(channel.getChatName());
                } catch (SQLException e) {
                    userPresenter.prepareFailureView("DB read fail");
                    return;
                }
            }

            baseUIState state = new baseUIState();
            state.setChatnames(chatNames);
            state.setChatEntities(chatEntities);
            baseUIViewModel.setState(state);
            baseUIViewModel.firePropertyChange();

            LoginOutputData outputData = new LoginOutputData(user);
            userPresenter.prepareSuccessView(outputData);

        } else {
            userPresenter.prepareFailureView("Invalid username or password.");
        }
    }

    @Override
    public void switchToSignupView() {
        userPresenter.switchToSignUpView();
    }
}
