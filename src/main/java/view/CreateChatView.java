package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import entity.Contact;
import entity.User;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelState;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.base_UI.BaseUiController;
import session.Session;

/**
 * Create chat channel view.
 */
public class CreateChatView extends JPanel implements PropertyChangeListener {

    private final Session sessionManager;
    private final JButton createChatButton;
    private final BaseUiController baseUiController;
    private AddChatChannelController addChatChannelController;

    private final DefaultListModel<Contact> contactListModel = new DefaultListModel<>();
    private final JList<Contact> contactList = new JList<>(contactListModel);

    public CreateChatView(Session sessionManager,
                          AddChatChannelController addChatChannelController,
                          BaseUiController baseUiController,
                          AddChatChannelViewModel addChatChannelViewModel) {

        System.out.println("Hello");

        this.sessionManager = sessionManager;
        this.addChatChannelController = addChatChannelController;
        this.baseUiController = baseUiController;

        addChatChannelViewModel.addPropertyChangeListener(this);

        final User currentUser = sessionManager.getMainUser();

        final BorderLayout borderLayout = new BorderLayout(10, 10);
        final int borderLayoutTop = 10;
        final int borderLayoutLeft = 10;
        final int borderLayoutBottom = 10;
        final int borderLayoutRight = 10;
        setLayout(borderLayout);
        setBorder(BorderFactory.createEmptyBorder(borderLayoutTop, borderLayoutLeft, borderLayoutBottom,
                borderLayoutRight));

        // Top panel with label and back button
        final JButton backButton = new JButton("Back");
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        final Color backButtonColour = new Color(96, 179, 120);
        final int backButtonThickness = 1;
        final boolean isBackButtonRounded = true;
        final Dimension backButtonDimension = new Dimension(140, 42);
        backButton.setBackground(backButtonColour);
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(backButtonDimension);
        backButton.setBorder(BorderFactory.createLineBorder(backButtonColour,
                backButtonThickness, isBackButtonRounded));

        backButton.addActionListener(evt -> {
            try {
                baseUiController.displayUi();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        final JPanel topPanel = new JPanel(new BorderLayout());
        final JLabel label = new JLabel("Select a user to start a chat with:");
        final int labelFontSize = 16;
        label.setFont(new Font("Arial", Font.BOLD, labelFontSize));
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Contact list
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer((list, value, index, isSelected,
                                     cellHasFocus) -> {
            final User friend;
            if (value.getUser().equals(currentUser)) {
                friend = value.getContact();
            }
            else {
                friend = value.getUser();
            }
            final JLabel lbl = new JLabel(friend.getUsername());
            lbl.setOpaque(true);
            if (isSelected) {
                lbl.setBackground(list.getSelectionBackground());
            }
            return lbl;
        });
        final JScrollPane scrollPane = new JScrollPane(contactList);
        add(scrollPane, BorderLayout.CENTER);

        // Chat name panel
        final JPanel bottomTextPanel = new JPanel(new BorderLayout());
        final JLabel chatNameLabel = new JLabel("Type your chat name:");
        final JTextField chatNameField = new JTextField();
        bottomTextPanel.add(chatNameLabel, BorderLayout.NORTH);
        bottomTextPanel.add(chatNameField, BorderLayout.CENTER);

        chatNameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void update() {
                createChatButton.setEnabled(
                        contactList.getSelectedIndex() != -1
                                && !chatNameField.getText().trim().isEmpty()
                );
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }
        });

        // Create chat button
        createChatButton = new JButton("Create Chat");
        createChatButton.setEnabled(true);
        createChatButton.addActionListener(evt -> {
            final Contact selectedContact = contactList.getSelectedValue();
            System.out.println(selectedContact);
            if (selectedContact != null) {
                final User friend;
                if (selectedContact.getUser().equals(currentUser)) {
                    friend = selectedContact.getContact();
                }
                else {
                    friend = selectedContact.getUser();
                }
                try {
                    addChatChannelController.createChannel(
                            friend.getUsername(),
                            chatNameField.getText(),
                            currentUser.getUserID(),
                            friend.getUserID()
                    );

                    baseUiController.displayUi();

                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        contactList.addListSelectionListener(evt -> {
            createChatButton.setEnabled(
                    contactList.getSelectedIndex() != -1 && !chatNameField.getText().trim().isEmpty()
            );
        });

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(createChatButton);

        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        southPanel.add(bottomTextPanel);
        southPanel.add(buttonPanel);

        add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Update contact list logic
        contactListModel.clear();
        if (sessionManager.getMainUser() != null) {
            final List<Contact> contacts = sessionManager.getMainUser().getContacts();
            if (contacts != null) {
                for (Contact contact : contacts) {
                    contactListModel.addElement(contact);
                }
            }
        }

        if (evt.getNewValue() != null) {
            final AddChatChannelState state = (AddChatChannelState) evt.getNewValue();
            System.out.print(state.getErrorMessage());
            // 1. Handle Error Popup
            if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(),
                        "Chat Creation Failed", JOptionPane.ERROR_MESSAGE);
                state.setErrorMessage(null);
            }

            // 2. Handle Success -> Switch Screen
            else if (state.isCreationSuccess()) {
                state.setCreationSuccess(false);
                try {
                    // THIS is where we switch back to the home page
                    baseUiController.displayUi();
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void setAddChatChannelController(AddChatChannelController addChatChannelController) {
        this.addChatChannelController = addChatChannelController;
    }
}
