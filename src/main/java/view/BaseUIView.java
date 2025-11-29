package view;

import entity.Contact;
import entity.DirectChatChannel;
import interface_adapter.ViewManagerModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.chat_channel.ChatChannelViewModel;
import interface_adapter.chat_channel.SendMessageController;
import interface_adapter.logout.LogoutController;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelState;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import session.SessionManager;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseUIView extends JPanel implements PropertyChangeListener {

    private final baseUIViewModel viewModel;
    private final baseUIController controller;
    private final SendMessageController sendMessageController;
    private final UpdateChatChannelController updateChatChannelController;
    private final UpdateChatChannelViewModel updateChatChannelViewModel;
    private final LogoutController logoutController;
    private ChatChannelView chatChannelView = null;
    private final ChatChannelViewModel chatChannelViewModel;
    private final ViewManagerModel viewManagerModel;
    private ViewManager viewManager;

    private final SessionManager sessionManager;
    private final DefaultListModel<String> chatListModel;
    private final JList<String> chatList;

    private final JButton createChatButton = new JButton("Create Chat");
    private final JButton friendRequestsButton = new JButton("Requests");
    private final JButton addFriendButton = new JButton("Add Friend");
    private final JButton logoutButton = new JButton("Logout");
    private final JButton initiateChatButton = new JButton("Initiate Chat");

    public BaseUIView(baseUIViewModel viewModel, baseUIController controller,
                      UpdateChatChannelViewModel updateChatChannelViewModel, ChatChannelViewModel chatChannelViewModel,
                      ViewManagerModel viewManagerModel, SessionManager sessionManager, ViewManager viewManager,
                      SendMessageController sendMessageController, UpdateChatChannelController updateChatChannelController,
                      LogoutController logoutController) throws SQLException {
        this.viewModel = viewModel;
        this.controller = controller;
        this.updateChatChannelViewModel = updateChatChannelViewModel;
        this.chatChannelViewModel = chatChannelViewModel;
        this.viewManagerModel = viewManagerModel;
        this.sessionManager = sessionManager;
        this.viewModel.addPropertyChangeListener(this);
        this.viewManager = viewManager;
        this.sendMessageController = sendMessageController;
        this.updateChatChannelController = updateChatChannelController;
        this.logoutController = logoutController;

        // Main layout styling
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 248, 250));
        this.setBorder(null);

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

        // Add Initiate Chat Button under scroll
        styleRoundedButton(initiateChatButton, new Color(30, 144, 255), Color.WHITE, new Font("SansSerif", Font.BOLD, 13));
        initiateChatButton.setPreferredSize(new Dimension(130, 32)); // smaller size
        initiateChatButton.setEnabled(false);

        JPanel initiatePanel = new JPanel();
        initiatePanel.setBackground(Color.WHITE);
        initiatePanel.add(initiateChatButton);
        listPanel.add(initiatePanel, BorderLayout.SOUTH);


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

        chatList.addListSelectionListener(e ->{
            initiateChatButton.setEnabled(true);
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

        initiateChatButton.addActionListener(e -> {
            baseUIState state = viewModel.getState();
            java.util.List<String> chatnames = state.getChatnames();
            java.util.List<DirectChatChannel> chatEntities = state.getChatEntities();

            String name =  chatList.getSelectedValue();
            Integer index = chatnames.indexOf(name);
            System.out.println(index);
            System.out.println(chatnames);

            DirectChatChannel chat = chatEntities.get(index);

//            Integer senderID;
//            Integer receiverID;
//            String senderUsername;
//            String receiverUsername;
//            if (chat.getUser1().getUsername().equals(sessionManager.getMainUser().getUsername())) {
//                senderID = sessionManager.getMainUser().getUserID();
//                receiverID = chat.getUser2().getUserID();
//                senderUsername = sessionManager.getMainUser().getUsername();
//                receiverUsername = chat.getUser2().getUsername();
//            }
//            else {
//                senderID = sessionManager.getMainUser().getUserID();
//                receiverID = chat.getUser1().getUserID();
//                senderUsername = sessionManager.getMainUser().getUsername();
//                receiverUsername = chat.getUser1().getUsername();
//            }

            if (this.chatChannelView != null) {
                try { this.chatChannelView.dispose(); } catch (Exception ignored) {}
            }
            try {
                updateChatChannelController.execute(chat.getChatUrl());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (updateChatChannelViewModel.getState().getError() != null) {
                JOptionPane.showMessageDialog(this, updateChatChannelViewModel.getState().getError());
                return;
            }
            ChatChannelView newChatChannelView = new ChatChannelView(updateChatChannelViewModel,
                    updateChatChannelController, sendMessageController);
            newChatChannelView.setBaseUIController(controller);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    return null;
                }

                @Override
                protected void done() {
                    SwingUtilities.invokeLater(() -> {
                        SwingUtilities.invokeLater(() -> {
                            JScrollBar v = newChatChannelView.getScrollPane().getVerticalScrollBar();
                            v.setValue(v.getMaximum());
                        });
                    });
                }
            };
            worker.execute();
            this.chatChannelView = newChatChannelView;
            viewManager.addView(chatChannelView, chatChannelViewModel.getViewName());
            this.switchView(this.viewManagerModel, this.chatChannelViewModel);
        });

        logoutButton.addActionListener(e -> {
            logoutController.logoutUser(sessionManager.getMainUser());
        });

        reloadChats(sessionManager.getMainUser().getUserChats());
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
        baseUIState state = viewModel.getState();
        System.out.println("UPDATE: " + state.getChatnames());
        System.out.println("ENTITIES: " + state.getChatEntities());
        chatListModel.clear();
        for (String chatName : state.getChatnames()) {
            chatListModel.addElement(chatName);
        }
        chatList.revalidate();
        chatList.repaint();
        this.revalidate();
        this.repaint();
    }

    public void switchView(ViewManagerModel viewManagerModel, ChatChannelViewModel chatChannelViewModel) {
        chatChannelViewModel.firePropertyChange();
        viewManagerModel.setState(chatChannelViewModel.getViewName());
        viewManagerModel.firePropertyChange();

    }

    private void reloadChats(List<String> chatNames) {
        chatListModel.clear();
        if (chatNames == null) return;
        for (String chatName : chatNames) {
            chatListModel.addElement(chatName);
        }
    }

    public JButton getAddFriendButton() { return addFriendButton; }
    public JButton getFriendRequestsButton() { return friendRequestsButton; }
    public JButton getCreateChatButton() { return createChatButton; }
    public JButton getLogoutButton() { return logoutButton; }
    public JList<String> getChatList() { return chatList; }
}
