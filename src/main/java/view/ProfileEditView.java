package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import interface_adapter.base_UI.BaseUiController;
import interface_adapter.profile_edit.ProfileEditController;
import interface_adapter.profile_edit.ProfileEditState;
import interface_adapter.profile_edit.ProfileEditViewModel;
import session.Session;

/**
 * Profile edit view.
 */
public class ProfileEditView extends JPanel implements PropertyChangeListener {
    private final ProfileEditViewModel profileEditViewModel;
    private ProfileEditController profileEditController;
    private final BaseUiController baseUiController;
    private final Session session;

    private final JLabel usernameLabel = new JLabel("Username:");
    private final JLabel passwordLabel = new JLabel("Password:");
    private final JLabel languageLabel = new JLabel("Language:");
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JComboBox<String> languages = new JComboBox<>(new String[]{"English", "French", "Spanish"});
    private final JButton saveChanges = new JButton("Save");
    private final JButton backButton = new JButton("Back");
    private final String font = "SansSerif";

    public ProfileEditView(ProfileEditViewModel profileEditViewModel,
                           BaseUiController baseUiController,
                           Session session) {

        this.profileEditViewModel = profileEditViewModel;
        this.baseUiController = baseUiController;
        this.session = session;
        profileEditViewModel.addPropertyChangeListener(this);

        final JPanel titlePanel = new JPanel();
        final JLabel title = new JLabel("Edit Profile");
        final int titleFontSize = 28;
        final int titlePanelGridRow = 1;
        final int titlePanelGridColumn = 3;
        title.setFont(new Font(this.font, Font.BOLD, titleFontSize));
        titlePanel.setLayout(new GridLayout(titlePanelGridRow, titlePanelGridColumn));

        final Dimension backButtonDimension = new Dimension(50, 20);
        backButton.setSize(backButtonDimension);
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(new JLabel(" "));
        titlePanel.add(title);
        titlePanel.add(backButton);

        final JPanel usernamePanel = new JPanel();
        final int usernameLabelFontSize = 20;
        final int usernamePanelGridRow = 1;
        final int usernamePanelGridColumn = 2;
        final int usernamePanelHgap = 15;
        final int usernamePanelVgap = 2;
        usernameLabel.setFont(new Font(this.font, Font.BOLD, usernameLabelFontSize));
        usernamePanel.setLayout(new GridLayout(usernamePanelGridRow, usernamePanelGridColumn, usernamePanelHgap,
                usernamePanelVgap));
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        final JPanel passwordPanel = new JPanel();
        final int passwordLabelFontSize = 20;
        final int passwordPanelGridRow = 1;
        final int passwordPanelGridColumn = 2;
        final int passwordPanelHgap = 15;
        final int passwordPanelVgap = 2;
        passwordLabel.setFont(new Font(this.font, Font.BOLD, passwordLabelFontSize));
        passwordPanel.setLayout(new GridLayout(passwordPanelGridRow, passwordPanelGridColumn, passwordPanelHgap,
                passwordPanelVgap));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        final JPanel languagesPanel = new JPanel();
        final int languagesPanelFontSize = 20;
        final int languagesPanelGridRow = 1;
        final int languagesPanelGridColumn = 2;
        final int languagesPanelHgap = 15;
        final int languagesPanelVgap = 2;
        languageLabel.setFont(new Font(this.font, Font.BOLD, languagesPanelFontSize));
        languagesPanel.setLayout(new GridLayout(languagesPanelGridRow, languagesPanelGridColumn, languagesPanelHgap,
                languagesPanelVgap));
        languagesPanel.setBackground(Color.WHITE);
        languagesPanel.add(languageLabel);
        languagesPanel.add(languages);

        final Dimension editProfilePanelDimension = new Dimension(700, 400);
        final JPanel editProfilePanel = new JPanel(new GridLayout(3, 1, 15, 30));
        editProfilePanel.setPreferredSize(editProfilePanelDimension);
        editProfilePanel.setBackground(Color.WHITE);
        final Color editProfilePanelColor = new Color(220, 220, 220);
        final int editProfilePanelTop = 30;
        final int editProfilePanelLeft = 50;
        final int editProfilePanelBottom = 30;
        final int editProfilePanelRight = 50;
        editProfilePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(editProfilePanelColor),
                BorderFactory.createEmptyBorder(editProfilePanelTop, editProfilePanelLeft, editProfilePanelBottom,
                        editProfilePanelRight)
        ));
        editProfilePanel.add(usernamePanel);
        editProfilePanel.add(passwordPanel);
        editProfilePanel.add(languagesPanel);

        final JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        buttons.add(saveChanges);

        // Action Listeners

        languages.addActionListener(evt -> {
            profileEditViewModel.getState().setPreferredLanguage((String) languages.getSelectedItem());
        });

        saveChanges.addActionListener(evt -> {
            try {
                profileEditController.editProfile(session.getMainUser().getUserID(), usernameField.getText(),
                new String(passwordField.getPassword()), (String) languages.getSelectedItem());
                if (profileEditViewModel.getState().getError() != null) {
                    JOptionPane.showMessageDialog(this, profileEditViewModel.getState().getError());
                }
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.addActionListener(evt -> {
            try {
                baseUiController.displayUi();
                usernameField.setText("");
                passwordField.setText("");
                languages.setSelectedIndex(0);
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titlePanel);
        this.add(editProfilePanel);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ProfileEditState state = (ProfileEditState) evt.getNewValue();
            if (state.getError() == null) {
                try {
                    baseUiController.displayUi();
                    usernameField.setText("");
                    passwordField.setText("");
                    languages.setSelectedIndex(0);
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void setProfileEditController(ProfileEditController profileEditController) {
        this.profileEditController = profileEditController;
    }
}

