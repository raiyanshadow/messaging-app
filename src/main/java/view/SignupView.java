package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class SignupView extends JPanel implements PropertyChangeListener {

    private final String viewName = "signup";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(20);
    private final JComboBox<String> languageDropdown = new JComboBox<>(new String[]{"English", "French", "Spanish"});
    private SignupController signupController = null;

    private final JButton signUp;
    private final JButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Initialize buttons
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);

        // Panel setup
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 248, 250));
        this.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // itle
        JLabel title = new JLabel(SignupViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));

        formPanel.add(createLabelField(SignupViewModel.USERNAME_LABEL, usernameInputField));
        formPanel.add(createLabelField(SignupViewModel.PASSWORD_LABEL, passwordInputField));
        formPanel.add(createLabelField(SignupViewModel.REPEAT_PASSWORD_LABEL, repeatPasswordInputField));
        formPanel.add(createLabelField("Preferred Language", languageDropdown));

        // Default language in state
        signupViewModel.getState().setPreferredLanguage((String) languageDropdown.getSelectedItem());

        languageDropdown.addActionListener(e -> {
            signupViewModel.getState().setPreferredLanguage((String) languageDropdown.getSelectedItem());
            signupViewModel.firePropertyChange();
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 248, 250));

        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        signUp.setFont(buttonFont);
        toLogin.setFont(buttonFont);

        signUp.setBackground(new Color(70, 130, 180));
        signUp.setForeground(Color.WHITE);
        signUp.setFocusPainted(false);
        signUp.setPreferredSize(new Dimension(120, 40));

        toLogin.setBackground(new Color(240, 240, 240));
        toLogin.setFocusPainted(false);
        toLogin.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(toLogin);
        buttonPanel.add(signUp);

        // Add panels to main
        this.add(title, BorderLayout.NORTH);
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        signUp.addActionListener(evt -> {
            SignupState state = signupViewModel.getState();
            try {
                signupController.execute(
                        state.getUsername(),
                        state.getPassword(),
                        state.getRepeatPassword(),
                        (String) languageDropdown.getSelectedItem()
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        toLogin.addActionListener(evt -> signupController.switchToLoginView());

        // Input field listeners
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
    }

    private JPanel createLabelField(String labelText, JComponent inputField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputField.setPreferredSize(new Dimension(250, 30));
        panel.add(label, BorderLayout.NORTH);
        panel.add(inputField, BorderLayout.CENTER);
        return panel;
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                SignupState state = signupViewModel.getState();
                state.setUsername(usernameInputField.getText());
                signupViewModel.setState(state);
            }

            @Override public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override public void changedUpdate(DocumentEvent e) { updateState(); }
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                SignupState state = signupViewModel.getState();
                state.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(state);
            }

            @Override public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override public void changedUpdate(DocumentEvent e) { updateState(); }
        });
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                SignupState state = signupViewModel.getState();
                state.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(state);
            }

            @Override public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override public void changedUpdate(DocumentEvent e) { updateState(); }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() { return viewName; }

    public void setSignupController(SignupController controller) { this.signupController = controller; }

}
