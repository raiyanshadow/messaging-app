package use_case.signup;

import SendBirdAPI.SendbirdUserCreator;
import data_access.UserDataAccessObject;
import entity.User;
import entity.UserFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.openapitools.client.model.SendbirdUser;

import java.sql.SQLException;

public class SignupInteractor implements SignupInputBoundary {

    private final UserDataAccessObject userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final SendbirdUserCreator sendbirdUserCreator;
    private final Dotenv dotenv;

    public SignupInteractor(UserDataAccessObject userDataAccessObject,
                            SignupOutputBoundary userPresenter,
                            SendbirdUserCreator sendbirdUserCreator) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.sendbirdUserCreator = sendbirdUserCreator;
        this.dotenv = Dotenv.configure()
                .directory("./assets") // adjust if your env file is elsewhere
                .filename("env")
                .load();
    }

    @Override
    public void execute(SignupInputData inputData) throws SQLException {
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        String repeatPassword = inputData.getRepeatPassword();
        String preferredLanguage = inputData.getPreferredLanguage();

        if (username.isEmpty()) {
            userPresenter.prepareFailView("Username cannot be empty");
            return;
        }
        if (password.isEmpty()) {
            userPresenter.prepareFailView("Password cannot be empty");
            return;
        }
        if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView("Passwords don't match.");
            return;
        }
        if (userDataAccessObject.existsByName(username)) {
            userPresenter.prepareFailView("User already exists.");
            return;
        }

        User user = null;

        try {
            user = new User(56, username, password, preferredLanguage); // assume auto-generated ID
            userDataAccessObject.save(user);

            String apiToken = dotenv.get("MSG_TOKEN");
            System.out.println("Sendbird token: " + apiToken);

            SendbirdUser sbUser = sendbirdUserCreator.createUser(apiToken, username, username);
            System.out.println("Sendbird user created: " + (sbUser != null ? sbUser.getUserId() : "null"));

            if (sbUser == null || sbUser.getUserId() == null) {
                // Sendbird creation failed, rollback DB
                userDataAccessObject.deleteByUsername(username);
                userPresenter.prepareFailView("Sendbird signup failed");
                return;
            }

            userPresenter.prepareSuccessView(new SignupOutputData(user.getUsername()));

        } catch (SQLException e) {
            userPresenter.prepareFailView("Database error: " + e.getMessage());
        } catch (Exception e) {
            if (user != null) {
                try {
                    userDataAccessObject.deleteByUsername(username);
                } catch (SQLException ex) {
                    System.err.println("Failed to rollback DB user: " + ex.getMessage());
                }
            }
            userPresenter.prepareFailView("Signup failed: " + e.getMessage());
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
