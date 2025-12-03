package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * Signup view panel for user registration.
 * Displays input fields for username, password, repeat password, and preferred language.
 * Communicates with the SignupController and updates the SignupViewModel.
 */
public class SignupView extends JPanel implements PropertyChangeListener {

    /** Name identifier for this view. */
    private final String viewName = "signup";

    private final String font = "SansSerif";

    /** The view model storing the state of the signup form. */
    private final SignupViewModel signupViewModel;

    /** Input fields. */
    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(20);
    private final JComboBox<String> languageDropdown =
            new JComboBox<>(new String[]{"English", "French", "Spanish"});

    /** Controller for executing signup actions. */
    private SignupController signupController;

    /** Buttons. */
    private final JButton signUp;
    private final JButton toLogin;

    /**
     * Constructs the SignupView with the provided view model.
     *
     * @param signupViewModel the signup view model
     */
    public SignupView(final SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Initialize buttons
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);

        // Panel setup
        this.setLayout(new BorderLayout());
        final Color signupViewBackgroundColour = new Color(245, 248, 250);
        final int signupTop = 40;
        final int signupLeft = 80;
        final int signupBottom = 40;
        final int signupRight = 80;
        this.setBackground(signupViewBackgroundColour);
        this.setBorder(BorderFactory.createEmptyBorder(signupTop, signupLeft, signupBottom, signupRight));

        // Title
        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL,
                SwingConstants.CENTER);
        final int titleFontSize = 28;
        title.setFont(new Font(font, Font.BOLD, titleFontSize));

        // Form panel
        final int formPanelGridRow = 4;
        final int formPanelGridColumn = 1;
        final int formPanelHgap = 15;
        final int formPanelVgap = 15;
        final Color formPanelBackgroundColour = new Color(220, 220, 220);
        final JPanel formPanel = new JPanel(new GridLayout(formPanelGridRow, formPanelGridColumn, formPanelHgap,
                formPanelVgap));
        formPanel.setBackground(Color.WHITE);
        final int formPanelTop = 30;
        final int formPanelLeft = 50;
        final int formPanelBottom = 30;
        final int formPanelRight = 50;
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(formPanelBackgroundColour),
                BorderFactory.createEmptyBorder(formPanelTop, formPanelLeft, formPanelBottom, formPanelRight)
        ));

        formPanel.add(createLabelField(SignupViewModel.USERNAME_LABEL,
                usernameInputField));
        formPanel.add(createLabelField(SignupViewModel.PASSWORD_LABEL,
                passwordInputField));
        formPanel.add(createLabelField(SignupViewModel.REPEAT_PASSWORD_LABEL,
                repeatPasswordInputField));
        formPanel.add(createLabelField("Preferred Language",
                languageDropdown));

        // Default language in state
        signupViewModel.getState().setPreferredLanguage(
                (String) languageDropdown.getSelectedItem());

        languageDropdown.addActionListener(evt -> {
            final SignupState state = signupViewModel.getState();
            state.setPreferredLanguage(
                    (String) languageDropdown.getSelectedItem());
            signupViewModel.firePropertyChange();
        });

        // Button panel
        final JPanel buttonPanel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 20, 10));
        final Color buttonPanelBackgroundColour = new Color(245, 248, 250);
        buttonPanel.setBackground(buttonPanelBackgroundColour);

        final int buttonFontSize = 14;
        final Font buttonFont = new Font(this.font, Font.BOLD, buttonFontSize);

        signUp.setFont(buttonFont);
        toLogin.setFont(buttonFont);

        final Color signupBackgroundColour = new Color(70, 130, 180);
        final Dimension signupDimension = new Dimension(120, 40);
        signUp.setBackground(signupBackgroundColour);
        signUp.setForeground(Color.WHITE);
        signUp.setFocusPainted(false);
        signUp.setPreferredSize(signupDimension);

        final Color toLoginBackgroundColour = new Color(240, 240, 240);
        final Dimension toLoginDimension = new Dimension(120, 40);
        toLogin.setBackground(toLoginBackgroundColour);
        toLogin.setFocusPainted(false);
        toLogin.setPreferredSize(toLoginDimension);

        buttonPanel.add(toLogin);
        buttonPanel.add(signUp);

        // Add panels to main
        this.add(title, BorderLayout.NORTH);
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        signUp.addActionListener(evt -> {
            final SignupState state = signupViewModel.getState();
            try {
                signupController.execute(
                        state.getUsername(),
                        state.getPassword(),
                        state.getRepeatPassword(),
                        (String) languageDropdown.getSelectedItem()
                );
            }
            catch (final SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        toLogin.addActionListener(evt -> signupController.switchToLoginView());

        // Input field listeners
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
    }

    /**
     * Creates a panel containing a label and input field.
     *
     * @param labelText  the text for the label
     * @param inputField the input field component
     * @return a JPanel containing the label and input
     */
    private JPanel createLabelField(final String labelText,
                                    final JComponent inputField) {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        final JLabel label = new JLabel(labelText);
        final int labelFontSize = 16;
        final int inputFieldFontSize = 16;
        final Dimension inputFieldDimension = new Dimension(250, 30);
        label.setFont(new Font(this.font, Font.PLAIN, labelFontSize));
        inputField.setFont(new Font(this.font, Font.PLAIN, inputFieldFontSize));
        inputField.setPreferredSize(inputFieldDimension);
        panel.add(label, BorderLayout.NORTH);
        panel.add(inputField, BorderLayout.CENTER);
        return panel;
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final SignupState state = signupViewModel.getState();
                state.setUsername(usernameInputField.getText());
                signupViewModel.setState(state);
            }

            @Override public void insertUpdate(final DocumentEvent e) {
                updateState();
            }

            @Override public void removeUpdate(final DocumentEvent e) {
                updateState();
            }

            @Override public void changedUpdate(final DocumentEvent e) {
                updateState();
            }
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final SignupState state = signupViewModel.getState();
                state.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(state);
            }

            @Override public void insertUpdate(final DocumentEvent e) {
                updateState();
            }

            @Override public void removeUpdate(final DocumentEvent e) {
                updateState();
            }

            @Override public void changedUpdate(final DocumentEvent e) {
                updateState();
            }
        });
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(
                new DocumentListener() {
                    private void updateState() {
                        final SignupState state = signupViewModel.getState();
                        state.setRepeatPassword(
                                new String(repeatPasswordInputField.getPassword()));
                        signupViewModel.setState(state);
                    }

                    @Override public void insertUpdate(final DocumentEvent e) {
                        updateState();
                    }

                    @Override public void removeUpdate(final DocumentEvent e) {
                        updateState();
                    }

                    @Override public void changedUpdate(final DocumentEvent e) {
                        updateState();
                    }
                });
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    /**
     * Returns the name of this view.
     *
     * @return view name
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the signup controller.
     *
     * @param controller the SignupController instance
     */
    public void setSignupController(final SignupController controller) {
        this.signupController = controller;
    }
}
