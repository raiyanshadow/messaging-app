package view;

import entity.Contact;
import entity.User;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelState;
import interface_adapter.login.LoginState;
import session.Session;
import use_case.add_chat_channel.AddChatChannelInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

public class CreateChatView extends JFrame implements PropertyChangeListener {
    private final Session sessionmanager;
    private final JButton createChatButton;
    private AddChatChannelController addChatChannelController = null;

    public CreateChatView(Session sessionmanager, AddChatChannelController addChatChannelController) {
        this.sessionmanager = sessionmanager;
        this.addChatChannelController = addChatChannelController;
        final User currentUser = sessionmanager.getMainUser();

        setTitle("Create Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel label = new JLabel("Select a user to start a chat with:");
        panel.add(label, BorderLayout.NORTH);

        //get available users from main User

        DefaultListModel<Object> model = new DefaultListModel<>();
        JList<Object> userList = new JList<>(model);

        DefaultListModel<String> model2 = new DefaultListModel<>();
        JList<String> userListnames = new JList<>(model2);

        for (Contact contact : sessionmanager.getMainUser().getContacts()) {
            model.addElement(contact.getContact().getUsername());
            model.addElement(contact.getUser());

            model2.addElement(contact.getUser().getUsername());

        }

        userListnames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel chatnameLabel = new JLabel("Type your chat name:");
        panel.add(chatnameLabel, BorderLayout.SOUTH);
        JTextField chatname = new JTextField();
        panel.add(chatname, BorderLayout.SOUTH);

        createChatButton = new JButton("Create Chat");
        createChatButton.setEnabled(false); // disabled until selection

        createChatButton.addActionListener(e -> {
            String selectedUser = userListnames.getSelectedValue();
            if (selectedUser != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Creating chat with " + selectedUser,
                        "Chat Created",
                        JOptionPane.INFORMATION_MESSAGE
                );
                User contactUser = null;
                for (int i = 0; i < model.size(); i++) {
                    if (model.getElementAt(i).equals(selectedUser)) {
                        contactUser = (User) model.getElementAt(i+1);
                        break;
                    }
                }
                try {
                    addChatChannelController.CreateChannel(contactUser.getUsername(), chatname.getText(),
                            currentUser.getUserID(), contactUser.getUserID());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        userListnames.addListSelectionListener(e -> {
            createChatButton.setEnabled(!chatname.getText().trim().isEmpty() &&
                    (!(userListnames.getSelectedIndex() != -1)));
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createChatButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public void setAddChatChannelController(AddChatChannelController addChatChannelController) {
        this.addChatChannelController = addChatChannelController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddChatChannelState state = (AddChatChannelState) evt.getNewValue();
    }


}