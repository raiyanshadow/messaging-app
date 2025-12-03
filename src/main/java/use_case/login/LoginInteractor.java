package use_case.login;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.DirectChatChannel;
import entity.User;
import interface_adapter.base_UI.BaseUiState;
import interface_adapter.base_UI.BaseUiViewModel;

/**
 * Interactor for the login use case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccess;
    private final LoginChatChannelDataAccessInterface chatChannelDataAccessObject;
    private final LoginOutputBoundary userPresenter;
    private BaseUiViewModel baseUiViewModel;
    private final String dbReadFailString;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccess, LoginOutputBoundary userPresenter,
                           LoginChatChannelDataAccessInterface chatChannelDataAccessObject,
                           BaseUiViewModel baseUiViewModel) {
        this.userDataAccess = userDataAccess;
        this.userPresenter = userPresenter;
        this.chatChannelDataAccessObject = chatChannelDataAccessObject;
        this.baseUiViewModel = baseUiViewModel;
        dbReadFailString = "DB read fail";
    }

    @Override
    public void logIn(LoginInputData data) {
        final String username = data.getUsername();
        final String password = data.getPassword();

        boolean isValid = false;
        try {
            isValid = userDataAccess.validateCredentials(username, password);
        }
        catch (SQLException ex) {
            userPresenter.prepareFailureView(dbReadFailString);
        }

        if (isValid) {
            User user = null;
            try {
                user = userDataAccess.getUserFromName(username);
            }
            catch (SQLException ex) {
                userPresenter.prepareFailureView(dbReadFailString);
                return;
            }
            final List<String> chatUrls;
            try {
                chatUrls = chatChannelDataAccessObject.getChatUrlsByUserId(user.getUserID());
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                userPresenter.prepareFailureView(dbReadFailString);
                return;
            }
            final List<DirectChatChannel> chatEntities = new ArrayList<>();
            final List<String> chatNames = new ArrayList<>();
            for (int i = 0; i < chatUrls.size(); i++) {
                try {
                    chatEntities.add(chatChannelDataAccessObject.getDirectChatChannelByUrl(chatUrls.get(i)));
                    chatNames.add(chatEntities.get(i).getChatName());
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    userPresenter.prepareFailureView(dbReadFailString);
                    return;
                }
            }
            final BaseUiState state = new BaseUiState();
            state.setChatnames(chatNames);
            state.setChatEntities(chatEntities);
            baseUiViewModel.setState(state);
            baseUiViewModel.firePropertyChange();
            final LoginOutputData outputData = new LoginOutputData(user);
            userPresenter.prepareSuccessView(outputData);
        }
        else {
            userPresenter.prepareFailureView("Invalid username or password.");
        }
    }

    @Override
    public void switchToSignupView() {
        userPresenter.switchToSignUpView();
    }
}
