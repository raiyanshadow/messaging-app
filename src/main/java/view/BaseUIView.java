package view;

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
    private final baseUIController controller;

    private final DefaultListModel<String> chatListModel;
    private final JList<String> chatList;

    private final JButton createChatButton = new JButton("Create Chat");
    private final JButton addContactButton = new JButton("Add Contact");
    private final JButton friendRequestsButton = new JButton("Friend Requests");
    private final JButton logoutButton = new JButton("Logout");

    public BaseUIView(baseUIViewModel viewModel, baseUIController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewModel.addPropertyChangeListener(this);

        // Main layout styling
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 248, 250));
        this.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Title with spacing
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 248, 250));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 25, 0));

        JLabel title = new JLabel("Chats", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        titlePanel.add(title, BorderLayout.CENTER);

        this.add(titlePanel, BorderLayout.NORTH);

        // Chat list styled container
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        chatListModel = new DefaultListModel<>();
        chatList = new JList<>(chatListModel);
        chatList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listPanel.add(new JScrollPane(chatList), BorderLayout.CENTER);
        this.add(listPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 248, 250));

        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        styleRoundedButton(createChatButton, new Color(70, 130, 180), Color.WHITE, buttonFont);
        styleRoundedButton(addContactButton, new Color(96, 179, 120), Color.WHITE, buttonFont);
        styleRoundedButton(friendRequestsButton, new Color(255, 165, 0), Color.WHITE, buttonFont);
        styleRoundedButton(logoutButton, new Color(240, 240, 240), Color.BLACK, buttonFont);

        buttonPanel.add(addContactButton);
        buttonPanel.add(createChatButton);
        buttonPanel.add(friendRequestsButton);
        buttonPanel.add(logoutButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        createChatButton.addActionListener(e -> {
            try {
                controller.newChat();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        friendRequestsButton.addActionListener(e -> {
            controller.switchToFriendRequestView();
        });
    }

    private void styleRoundedButton(JButton button, Color bg, Color fg, Font font) {
        button.setFont(font);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 42));
        button.setBorder(BorderFactory.createLineBorder(bg, 1, true));
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
    public JButton getFriendRequestsButton() { return friendRequestsButton; }
    public JButton getLogoutButton() { return logoutButton; }
    public JList<String> getChatList() { return chatList; }
}
