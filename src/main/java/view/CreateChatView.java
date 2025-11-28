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
import java.util.List;

public class CreateChatView extends JPanel implements PropertyChangeListener {

    private final Session sessionManager;
    private final JButton createChatButton;
    private AddChatChannelController addChatChannelController = null;
    private final baseUIViewModel baseUIViewModel;
    private final baseUIController baseUIController;

    private final DefaultListModel<Contact> contactListModel = new DefaultListModel<>();
    private final JList<Contact> contactList = new JList<>(contactListModel);

    public CreateChatView(Session sessionManager,
                          AddChatChannelController addChatChannelController,
                          baseUIViewModel baseUIViewModel,
                          baseUIController baseUIController) {

        this.sessionManager = sessionManager;
        this.addChatChannelController = addChatChannelController;
        this.baseUIViewModel = baseUIViewModel;
        this.baseUIController = baseUIController;

        User currentUser = sessionManager.getMainUser();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel with label and back button
        JButton backButton = new JButton("Back");
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(96, 179, 120));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(140, 42));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(96, 179, 120), 1, true));

        backButton.addActionListener(e -> {
            try {
                baseUIController.displayUI();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select a user to start a chat with:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Contact list
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            User friend = value.getUser().equals(currentUser) ? value.getContact() : value.getUser();
            JLabel lbl = new JLabel(friend.getUsername());
            lbl.setOpaque(true);
            if (isSelected) lbl.setBackground(list.getSelectionBackground());
            return lbl;
        });
        JScrollPane scrollPane = new JScrollPane(contactList);
        add(scrollPane, BorderLayout.CENTER);

        // Chat name panel
        JPanel bottomTextPanel = new JPanel(new BorderLayout());
        JLabel chatNameLabel = new JLabel("Type your chat name:");
        JTextField chatNameField = new JTextField();
        bottomTextPanel.add(chatNameLabel, BorderLayout.NORTH);
        bottomTextPanel.add(chatNameField, BorderLayout.CENTER);
        add(bottomTextPanel, BorderLayout.SOUTH);

        // Create chat button
        createChatButton = new JButton("Create Chat");
        createChatButton.setEnabled(false);
        createChatButton.addActionListener(e -> {
            Contact selectedContact = contactList.getSelectedValue();
            if (selectedContact != null) {
                User friend = selectedContact.getUser().equals(currentUser) ? selectedContact.getContact() : selectedContact.getUser();
                try {
                    addChatChannelController.CreateChannel(
                            friend.getUsername(),
                            chatNameField.getText(),
                            currentUser.getUserID(),
                            friend.getUserID()
                    );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        contactList.addListSelectionListener(e -> {
            createChatButton.setEnabled(
                    contactList.getSelectedIndex() != -1 && !chatNameField.getText().trim().isEmpty()
            );
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createChatButton);
        add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

        // Initial contact load
        reloadContacts(currentUser.getContacts());

        // Listen for contact updates
        this.baseUIViewModel.addPropertyChangeListener(evt -> {
            if ("contacts_updated".equals(evt.getPropertyName())) {
                baseUIState state = (baseUIState) evt.getNewValue();
                reloadContacts(state.getContacts());
            }
        });
    }

    private void reloadContacts(List<Contact> contacts) {
        contactListModel.clear();
        if (contacts == null) return;
        for (Contact contact : contacts) {
            contactListModel.addElement(contact);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof AddChatChannelState) {
            AddChatChannelState state = (AddChatChannelState) evt.getNewValue();
            if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Chat Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void setAddChatChannelController(AddChatChannelController addChatChannelController) {
        this.addChatChannelController = addChatChannelController;
    }
}
