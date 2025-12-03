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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * Login View.
 */
public class LoginView extends JPanel implements PropertyChangeListener {
    private final String viewName = "login";
    private final LoginViewModel loginViewModel;
    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private LoginController loginController;
    private final String font = "SansSerif";

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        loginViewModel.addPropertyChangeListener(this);

        final JButton loginButton = new JButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        final JButton toSignupButton = new JButton(LoginViewModel.TO_SIGNUP_BUTTON_LABEL);

        final Color backgroundColour = new Color(245, 248, 250);
        final int backgroundTop = 40;
        final int backgroundLeft = 80;
        final int backgroundBottom = 40;
        final int backgroundRight = 80;
        this.setLayout(new BorderLayout());
        this.setBackground(backgroundColour);
        this.setBorder(BorderFactory.createEmptyBorder(backgroundTop, backgroundLeft,
                backgroundBottom, backgroundRight));

        final JLabel title = new JLabel(LoginViewModel.TITLE_LABEL, SwingConstants.CENTER);
        final int titleFontSize = 28;
        title.setFont(new Font(font, Font.BOLD, titleFontSize));

        final Color formPanelColour = new Color(220, 220, 220);
        final int formPanelTop = 30;
        final int formPanelLeft = 50;
        final int formPanelBottom = 30;
        final int formPanelRight = 50;
        final JPanel formPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(formPanelColour),
                BorderFactory.createEmptyBorder(formPanelTop, formPanelLeft, formPanelBottom, formPanelRight)
        ));

        formPanel.add(createLabelField(LoginViewModel.USERNAME_LABEL, usernameInputField));
        formPanel.add(createLabelField(LoginViewModel.PASSWORD_LABEL, passwordInputField));

        final int buttonPanelHgap = 20;
        final int buttonPanelVgap = 10;
        final Color buttonPanelBackgroundColour = new Color(245, 248, 250);
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, buttonPanelHgap, buttonPanelVgap));
        buttonPanel.setBackground(buttonPanelBackgroundColour);

        final int buttonFontSize = 14;
        final Font buttonFont = new Font(font, Font.BOLD, buttonFontSize);

        loginButton.setFont(buttonFont);
        toSignupButton.setFont(buttonFont);

        final Color loginButtonColour = new Color(70, 130, 180);
        final Dimension loginButtonDimension = new Dimension(120, 40);
        loginButton.setBackground(loginButtonColour);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(loginButtonDimension);

        final Color toSignupButtonColour = new Color(240, 240, 240);
        final Dimension toSignupButtonDimension = new Dimension(120, 40);
        toSignupButton.setBackground(toSignupButtonColour);
        toSignupButton.setFocusPainted(false);
        toSignupButton.setPreferredSize(toSignupButtonDimension);

        buttonPanel.add(loginButton);
        buttonPanel.add(toSignupButton);

        // Add panels to main
        this.add(title, BorderLayout.NORTH);
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(evt -> {
            final LoginState state = loginViewModel.getState();
            try {
                loginController.logIn(
                        state.getUsername(),
                        state.getPassword()
                );
                this.usernameInputField.setText("");
                this.passwordInputField.setText("");
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
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
                final LoginState state = loginViewModel.getState();
                state.setUsername(usernameInputField.getText());
                loginViewModel.setState(state);
            }

            @Override public void insertUpdate(DocumentEvent e) {
                updateState();
            }

            @Override public void removeUpdate(DocumentEvent e) {
                updateState();
            }

            @Override public void changedUpdate(DocumentEvent e) {
                updateState();
            }
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final LoginState state = loginViewModel.getState();
                state.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(state);
            }

            @Override public void insertUpdate(DocumentEvent e) {
                updateState();
            }

            @Override public void removeUpdate(DocumentEvent e) {
                updateState();
            }

            @Override public void changedUpdate(DocumentEvent e) {
                updateState();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

    public String getViewName() {
        return this.viewName;
    }
}
