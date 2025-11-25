package view;

import entity.Contact;
import entity.User;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelState;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class CreateChatView extends JPanel implements PropertyChangeListener {

    private final Session sessionmanager;
    private final JButton createChatButton;
    private AddChatChannelController addChatChannelController = null;
    private final baseUIViewModel baseUIViewModel;
    private final baseUIController baseUIController;
    private JLabel errorLabel = new JLabel();
    private final AddChatChannelViewModel addChatChannelViewModel;

    public CreateChatView(Session sessionmanager,
                          AddChatChannelController addChatChannelController,
                          baseUIViewModel baseUIViewModel,
                          baseUIController baseUIController, AddChatChannelViewModel addChatChannelViewModel) {

        this.sessionmanager = sessionmanager;
        this.addChatChannelController = addChatChannelController;
        this.baseUIViewModel = baseUIViewModel;
        this.baseUIController = baseUIController;
        this.addChatChannelViewModel = addChatChannelViewModel;
        addChatChannelViewModel.addPropertyChangeListener(this);

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
//        DefaultListModel<Object> model1 = new DefaultListModel<>();
//        DefaultListModel<String> model2 = new DefaultListModel<>();
        java.util.List<Contact> contacts = currentUser.getContacts();

        DefaultListModel<User> userModel = new DefaultListModel<>();
        for (Contact contact : contacts) {
            userModel.addElement(contact.getContact());
        }
        JList<User> userList = new JList<>(userModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label1 = new JLabel(value.getUsername());
            label1.setOpaque(true);
            if (isSelected) {
                label1.setBackground(list.getSelectionBackground());
                label1.setForeground(list.getSelectionForeground());
            }
            return label1;
        });

        JScrollPane scrollPane = new JScrollPane(userList);
//
//        JList<String> userListnames = new JList<>(model2);
//        JList<Object> userList = new JList<>(model1);
//        System.out.println(model1);
//        System.out.println(model2);
//        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // CENTER MAIN PANEL FOR LIST + TEXT + BUTTON
        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));

        // Scroll list
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        //text and label
        JPanel namePanel = new JPanel(new BorderLayout());
        JLabel chatnameLabel = new JLabel("Type your chat name:");
        JTextField chatname = new JTextField();
        namePanel.add(chatnameLabel, BorderLayout.NORTH);
        namePanel.add(chatname, BorderLayout.CENTER);
        centerPanel.add(namePanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom grouped panel
        JPanel bottomPanel = new JPanel(new BorderLayout(5,5));
        createChatButton = new JButton("Create Chat");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createChatButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        createChatButton.setEnabled(false);

        add(bottomPanel, BorderLayout.SOUTH);



        chatname.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateButton();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateButton();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateButton();
            }

            private void updateButton() {
                createChatButton.setEnabled(
                        userList.getSelectedValue() != null &&
                                !chatname.getText().trim().isEmpty()
                );
            }
        });

        createChatButton.addActionListener(e -> {
            User selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {

                try {
                    addChatChannelController.CreateChannel(
                            selectedUser.getUsername(),
                            chatname.getText(),
                            currentUser.getUserID(),
                            selectedUser.getUserID()
                    );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddChatChannelState state = (AddChatChannelState) evt.getNewValue();
        System.out.println("works1" + " " + state.getErrorMessage());

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
