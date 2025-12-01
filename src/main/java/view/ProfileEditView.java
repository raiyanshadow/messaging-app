package view;

import entity.User;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.profile_edit.ProfileEditController;
import interface_adapter.profile_edit.ProfileEditState;
import interface_adapter.profile_edit.ProfileEditViewModel;
//import interface_adapter.search_contact.SearchContactController;
//import interface_adapter.search_contact.SearchContactState;
//import interface_adapter.search_contact.SearchContactViewModel;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

public class ProfileEditView extends JPanel implements PropertyChangeListener {

    public final String viewName = "profile edit view";
    private final ProfileEditViewModel profileEditViewModel;
    private ProfileEditController profileEditController = null;
    private final baseUIController baseUIController;
    private final Session session;

    private final JLabel usernameLabel = new JLabel("Username:");
    private final JLabel passwordLabel = new JLabel("Password:");
    private final JLabel languageLabel = new JLabel("Language:");
    private final JTextField usernameField = new JTextField(20);;
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JComboBox<String> languages = new JComboBox<>(new String[]{"English", "French", "Spanish"});
    private final JButton saveChanges = new JButton("Save");
    private final JButton backButton = new JButton("Back");

    public ProfileEditView(ProfileEditViewModel profileEditViewModel,
                           baseUIController baseUIController,
                           Session session) {

        this.profileEditViewModel = profileEditViewModel;
        this.baseUIController = baseUIController;
        this.session = session;
        profileEditViewModel.addPropertyChangeListener(this);

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Edit Profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        titlePanel.setLayout(new GridLayout(1, 3));
//        backButton.setHorizontalAlignment(JLabel.RIGHT);
        backButton.setSize(new Dimension(50, 20));
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(new JLabel(" ")); // Spacer
        titlePanel.add(title);
        titlePanel.add(backButton);

        JPanel usernamePanel = new JPanel();
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        usernamePanel.setLayout(new GridLayout(1, 2, 15, 2));
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel();
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        passwordPanel.setLayout(new GridLayout(1, 2, 15, 2));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel languagesPanel = new JPanel();
        languageLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        languagesPanel.setLayout(new GridLayout(1, 2, 15, 2));
        languagesPanel.setBackground(Color.WHITE);
        languagesPanel.add(languageLabel);
        languagesPanel.add(languages);

        JPanel editProfilePanel = new JPanel(new GridLayout(3, 1, 15, 30));
        editProfilePanel.setPreferredSize(new Dimension(700, 400));
        editProfilePanel.setBackground(Color.WHITE);
        editProfilePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        editProfilePanel.add(usernamePanel);
        editProfilePanel.add(passwordPanel);
        editProfilePanel.add(languagesPanel);

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        buttons.add(saveChanges);

        // Action Listeners

        languages.addActionListener(e ->
                profileEditViewModel.getState().setPreferredLanguage((String) languages.getSelectedItem()));

        saveChanges.addActionListener(evt ->
        {
            try {
                profileEditController.editProfile(session.getMainUser().getUserID(), usernameField.getText(),
                new String(passwordField.getPassword()), (String) languages.getSelectedItem());
                if (profileEditViewModel.getState().getError() != null) {
                    JOptionPane.showMessageDialog(this, profileEditViewModel.getState().getError());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        backButton.addActionListener(e -> {
            try {
                baseUIController.displayUI();
            } catch (SQLException ex) {
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
                    baseUIController.displayUI();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setProfileEditController(ProfileEditController profileEditController) {
        this.profileEditController = profileEditController;
    }
}

