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
    private final JButton friendRequestsButton = new JButton("Friend Requests");
    private final JButton addFriendButton = new JButton("Add Friend");
    private final JButton logoutButton = new JButton("Logout");

    public BaseUIView(baseUIViewModel viewModel, baseUIController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewModel.addPropertyChangeListener(this);

        // Main layout styling
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 248, 250));
        this.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));

        // Title
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 248, 250));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 25, 0));

        JLabel title = new JLabel("Chats", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        titlePanel.add(title, BorderLayout.CENTER);

        this.add(titlePanel, BorderLayout.NORTH);

        // Chat List Area
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

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(245, 248, 250));

        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        styleRoundedButton(createChatButton, new Color(70, 130, 180), Color.WHITE, buttonFont);

        // Same styling for friend buttons
        Color friendButtonColor = new Color(255, 165, 0);
        styleRoundedButton(friendRequestsButton, friendButtonColor, Color.WHITE, buttonFont);
        styleRoundedButton(addFriendButton, friendButtonColor, Color.WHITE, buttonFont);

        styleRoundedButton(logoutButton, new Color(240, 240, 240), Color.BLACK, buttonFont);

        // Add in button order
        buttonPanel.add(createChatButton);
        buttonPanel.add(addFriendButton);
        buttonPanel.add(friendRequestsButton);
        buttonPanel.add(logoutButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        createChatButton.addActionListener(e -> {
            try {
                controller.newChat();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        friendRequestsButton.addActionListener(e -> {
            try {
                controller.switchToFriendRequestView();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        addFriendButton.addActionListener(e -> {
            try {
                controller.switchToAddContact();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void styleRoundedButton(JButton button, Color bg, Color fg, Font font) {
        button.setFont(font);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);

        button.setOpaque(true);
        button.setContentAreaFilled(true); // allow background repaint
        button.setPreferredSize(new Dimension(145, 45));

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg, 2, true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        baseUIState state = (baseUIState) evt.getNewValue();
        chatListModel.clear();
        for (String chatName : state.getChatnames()) {
            chatListModel.addElement(chatName);
        }
    }

    public JButton getAddFriendButton() { return addFriendButton; }
    public JButton getFriendRequestsButton() { return friendRequestsButton; }
    public JButton getCreateChatButton() { return createChatButton; }
    public JButton getLogoutButton() { return logoutButton; }
    public JList<String> getChatList() { return chatList; }
}
