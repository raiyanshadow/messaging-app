package use_case.signup;

import java.sql.SQLException;

public interface SignupInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data from the UI
     */
    void execute(SignupInputData signupInputData) throws SQLException;

    /**
     * Switches the view to the login screen.
     */
    void switchToLoginView();
}
