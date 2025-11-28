package view;

import entity.Contact;
import entity.User;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelState;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class CreateChatView extends JPanel implements PropertyChangeListener {

    private final Session sessionmanager;
    private final JButton createChatButton;
    private AddChatChannelController addChatChannelController = null;
    private final baseUIViewModel baseUIViewModel;
    private final baseUIController baseUIController;

    public CreateChatView(Session sessionmanager,
                          AddChatChannelController addChatChannelController,
                          baseUIViewModel baseUIViewModel,
                          baseUIController baseUIController) {

        this.sessionmanager = sessionmanager;
        this.addChatChannelController = addChatChannelController;
        this.baseUIViewModel = baseUIViewModel;
        this.baseUIController = baseUIController;

        final User currentUser = sessionmanager.getMainUser();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // BACK BUTTON
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            try {
                baseUIController.displayUI(); // triggers presenter → viewmanager switching
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // TOP PANEL WITH LABEL + BACK BUTTON
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select a user to start a chat with:");
        label.setFont(new Font("Arial", Font.BOLD, 16));

        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // LIST MODELS
        DefaultListModel<Object> model = new DefaultListModel<>();
        JList<Object> userList = new JList<>(model);

        DefaultListModel<String> model2 = new DefaultListModel<>();

        for (Contact contact : currentUser.getContacts()) {
            model.addElement(contact.getContact().getUsername());
            model.addElement(contact.getUser());
            model2.addElement(contact.getUser().getUsername());
        }

        JList<String> userListnames = new JList<>(model2);
        userListnames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);
        add(scrollPane, BorderLayout.CENTER);

        // Chat Name Field
        JPanel bottomTextPanel = new JPanel(new BorderLayout());
        JLabel chatnameLabel = new JLabel("Type your chat name:");
        JTextField chatname = new JTextField();
        bottomTextPanel.add(chatnameLabel, BorderLayout.NORTH);
        bottomTextPanel.add(chatname, BorderLayout.CENTER);

        add(bottomTextPanel, BorderLayout.SOUTH);

        // Create Chat Button
        createChatButton = new JButton("Create Chat");
        createChatButton.setEnabled(false);

        createChatButton.addActionListener(e -> {
            String selectedUser = userListnames.getSelectedValue();
            if (selectedUser != null) {
                User contactUser = null;

                for (int i = 0; i < model.size(); i++) {
                    if (model.getElementAt(i).equals(selectedUser)) {
                        contactUser = (User) model.getElementAt(i + 1);
                        break;
                    }
                }

                try {
                    addChatChannelController.CreateChannel(
                            contactUser.getUsername(),
                            chatname.getText(),
                            currentUser.getUserID(),
                            contactUser.getUserID()
                    );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        userListnames.addListSelectionListener(e -> {
            createChatButton.setEnabled(
                    !chatname.getText().trim().isEmpty() &&
                            userListnames.getSelectedIndex() != -1
            );
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createChatButton);
        add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddChatChannelState state = (AddChatChannelState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "Chat Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void setAddChatChannelController(AddChatChannelController addChatChannelController) {
        this.addChatChannelController = addChatChannelController;
    }
}
