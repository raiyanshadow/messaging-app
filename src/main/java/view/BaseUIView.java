package view;

import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class BaseUIView extends JPanel implements PropertyChangeListener {

    private final baseUIViewModel viewModel;
    private final JList<String> chatList;
    private final DefaultListModel<String> chatListModel;
    private final baseUIController controller;

    private final JButton createChatButton = new JButton("Create Chat");
    private final JButton addContactButton = new JButton("Add Contact");
    private final JButton logoutButton = new JButton("Logout");

    public BaseUIView(baseUIViewModel viewModel, baseUIController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));


        JLabel title = new JLabel("Your Chats", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);


        chatListModel = new DefaultListModel<>();
        chatList = new JList<>(chatListModel);
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(chatList), BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createChatButton);
        bottomPanel.add(addContactButton);
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        createChatButton.addActionListener(e -> {
            try {
                controller.newChat();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        baseUIState state = (baseUIState) evt.getNewValue();

        chatListModel.clear();
        for (String chatName : state.getChatnames()) {
            chatListModel.addElement(chatName);
        }
    }

    public JButton getCreateChatButton() { return createChatButton; }
    public JButton getAddContactButton() { return addContactButton; }
    public JButton getLogoutButton() { return logoutButton; }
    public JList<String> getChatList() { return chatList; }
}


