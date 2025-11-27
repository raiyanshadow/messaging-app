package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class LoginView extends JPanel implements PropertyChangeListener {
    private final String viewName = "login";
    private final LoginViewModel loginViewModel;
    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        loginViewModel.addPropertyChangeListener(this);

        JButton loginButton = new JButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        JButton toSignupButton = new JButton(LoginViewModel.TO_SIGNUP_BUTTON_LABEL);

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 248, 250));
        this.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel title = new JLabel(LoginViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));

        formPanel.add(createLabelField(LoginViewModel.USERNAME_LABEL, usernameInputField));
        formPanel.add(createLabelField(LoginViewModel.PASSWORD_LABEL, passwordInputField));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 248, 250));

        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        loginButton.setFont(buttonFont);
        toSignupButton.setFont(buttonFont);

        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 40));

        toSignupButton.setBackground(new Color(240, 240, 240));
        toSignupButton.setFocusPainted(false);
        toSignupButton.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(loginButton);
        buttonPanel.add(toSignupButton);

        // Add panels to main
        this.add(title, BorderLayout.NORTH);
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(evt -> {
            LoginState state = loginViewModel.getState();
            try {
                loginController.logIn(
                        state.getUsername(),
                        state.getPassword()
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        toSignupButton.addActionListener(evt -> loginController.switchToSignupView());

        addUsernameListener();
        addPasswordListener();
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
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
                LoginState state = loginViewModel.getState();
                state.setUsername(usernameInputField.getText());
                loginViewModel.setState(state);
            }

            @Override public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override public void changedUpdate(DocumentEvent e) { updateState(); }
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                LoginState state = loginViewModel.getState();
                state.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(state);
            }

            @Override public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override public void changedUpdate(DocumentEvent e) { updateState(); }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

    public String getViewName() {
        return this.viewName;
    }
}
