package use_case.login;

public interface LoginInputBoundary {
    /**
     * Method to log in a user with the provided input data.
     *
     * @param data The input data required for logging in.
     */
    void logIn(LoginInputData data);
}
