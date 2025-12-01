package use_case.signup;

import sendbirdapi.SendbirdUserCreator;
import data_access.UserDataAccessObject;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.openapitools.client.model.SendbirdUser;

import java.sql.SQLException;

import static java.lang.System.*;

/**
 * Interactor handling the signup use case, including validation,
 * user creation, and Sendbird integration.
 */
public class SignupInteractor implements SignupInputBoundary {

    /** Magic number replaced with constant. */
    private static final int DEFAULT_USER_ID = 56;

    /** DAO for user persistence. */
    private final UserDataAccessObject userDataAccessObject;

    /** Presenter for preparing output data. */
    private final SignupOutputBoundary userPresenter;

    /** Creator responsible for interacting with the Sendbird API. */
    private final SendbirdUserCreator sendbirdUserCreator;

    /** Environment variable loader. */
    private final Dotenv dotenv;

    /**
     * Constructs a SignupInteractor.
     *
     * @param userDataAccessObject the DAO implementation
     * @param userPresenter the presenter for preparing output
     * @param sendbirdUserCreator the Sendbird API helper
     */
    public SignupInteractor(final UserDataAccessObject userDataAccessObject,
                            final SignupOutputBoundary userPresenter,
                            final SendbirdUserCreator sendbirdUserCreator) {

        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.sendbirdUserCreator = sendbirdUserCreator;

        this.dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();
    }

    /**
     * Executes the signup process.
     *
     * @param inputData the input signup data
     * @throws SQLException thrown when database errors occur
     */
    @Override
    public void execute(final SignupInputData inputData) throws SQLException {

        final String username = inputData.getUsername();
        final String password = inputData.getPassword();
        final String repeatPassword = inputData.getRepeatPassword();
        final String preferredLanguage = inputData.getPreferredLanguage();

        if (username.isEmpty()) {
            this.userPresenter.prepareFailView("Username cannot be empty");
            return;
        }
        if (password.isEmpty()) {
            this.userPresenter.prepareFailView("Password cannot be empty");
            return;
        }
        if (!password.equals(repeatPassword)) {
            this.userPresenter.prepareFailView("Passwords don't match.");
            return;
        }
        if (this.userDataAccessObject.existsByName(username)) {
            this.userPresenter.prepareFailView("User already exists.");
            return;
        }

        User user = null;

        try {
            user =
                    new User(DEFAULT_USER_ID, username, password,
                            preferredLanguage);

            final Integer userId = this.userDataAccessObject.save(user);

            final String apiToken = this.dotenv.get("MSG_TOKEN");
            out.println("Sendbird token: " + apiToken);

            final SendbirdUser sbUser =
                    this.sendbirdUserCreator.createUser(apiToken, userId,
                            username);

            out.println(
                    "Sendbird user created: "
                            + (sbUser != null ? sbUser.getUserId() : "null")
            );

            if (sbUser == null) {
                this.userDataAccessObject.deleteByUsername(username);
                this.userPresenter.prepareFailView(
                        "Sendbird signup failed");
                return;
            }

            final SignupOutputData outputData =
                    new SignupOutputData(user.getUsername());
            this.userPresenter.prepareSuccessView(outputData);

        } catch (SQLException e) {

            this.userPresenter.prepareFailView(
                    "Database error: " + e.getMessage());

        } catch (Exception e) {

            if (user != null) {
                try {
                    this.userDataAccessObject.deleteByUsername(username);
                } catch (SQLException ex) {
                    err.println(
                            "Failed to rollback DB user: "
                                    + ex.getMessage());
                }
            }

            this.userPresenter.prepareFailView(
                    "Signup failed: " + e.getMessage());
        }
    }

    /**
     * Switches to the login view.
     * Intended to be overridden in subclasses if needed.
     */
    @Override
    public void switchToLoginView() {
        this.userPresenter.switchToLoginView();
    }
}
