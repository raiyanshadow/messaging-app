package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * Presenter for the signup use case.
 * Implements SignupOutputBoundary to update the view models based on the result
 * of the signup operation.
 */
public class SignupPresenter implements SignupOutputBoundary {

    /** View model for the signup view. */
    private final SignupViewModel signupViewModel;

    /** View model for the login view. */
    private final LoginViewModel loginViewModel;

    /** Manager for switching between views. */
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a SignupPresenter.
     *
     * @param viewManagerModel the view manager model
     * @param signupViewModel the signup view model
     * @param loginViewModel the login view model
     */
    public SignupPresenter(final ViewManagerModel viewManagerModel,
                           final SignupViewModel signupViewModel,
                           final LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    /**
     * Called when signup is successful.
     * Updates the login view model with the new username and switches to login view.
     *
     * @param response the signup output data
     */
    @Override
    public void prepareSuccessView(final SignupOutputData response) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        loginViewModel.firePropertyChange();

        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    /**
     * Called when signup fails.
     * Updates the signup view model with the error message.
     *
     * @param error the error message to display
     */
    @Override
    public void prepareFailView(final String error) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(error);
        signupViewModel.firePropertyChange();
    }

    /**
     * Switches the current view to the login view.
     */
    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
