package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import entity.DirectChatChannel;
import interface_adapter.base_UI.BaseUiController;
import interface_adapter.base_UI.BaseUiState;
import interface_adapter.base_UI.BaseUiViewModel;
import interface_adapter.chat_channel.ChatChannelViewModel;
import interface_adapter.chat_channel.SendMessageController;
import interface_adapter.logout.LogoutController;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import session.SessionManager;

/**
 * Base UI View.
 */
public class BaseUiView extends JPanel implements PropertyChangeListener {

    private final BaseUiViewModel viewModel;
    private ChatChannelView chatChannelView;
    private final ChatChannelViewModel chatChannelViewModel;
    private final interface_adapter.ViewManagerModel viewManagerModel;

    private final DefaultListModel<String> chatListModel;
    private final JList<String> chatList;

    private final JButton createChatButton = new JButton("Create Chat");
    private final JButton friendRequestsButton = new JButton("Requests");
    private final JButton addFriendButton = new JButton("Add Friend");
    private final JButton logoutButton = new JButton("Logout");
    private final JButton profileEditButton = new JButton("Profile Edit");
    private final JButton initiateChatButton = new JButton("Initiate Chat");

    public BaseUiView(BaseUiViewModel viewModel, BaseUiController controller,
                      UpdateChatChannelViewModel updateChatChannelViewModel, ChatChannelViewModel chatChannelViewModel,
                      interface_adapter.ViewManagerModel viewManagerModel, SessionManager sessionManager,
                      ViewManager viewManager, SendMessageController sendMessageController,
                      UpdateChatChannelController updateChatChannelController, LogoutController logoutController)
            throws SQLException {
        this.viewModel = viewModel;
        this.chatChannelViewModel = chatChannelViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);
        final String font = "SansSerif";

        // Main layout styling
        this.setLayout(new BorderLayout());
        final Color backgroundColor = new Color(245, 248, 250);
        this.setBackground(backgroundColor);
        this.setBorder(null);

        // Title
        final JPanel titlePanel = new JPanel(new BorderLayout());
        final Color titlePanelBackgroundColour = new Color(245, 248, 250);
        final int titlePanelTop = 10;
        final int titlePanelLeft = 0;
        final int titlePanelBottom = 25;
        final int titlePanelRight = 0;
        titlePanel.setBackground(titlePanelBackgroundColour);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(titlePanelTop, titlePanelLeft, titlePanelBottom,
                titlePanelRight));

        final JLabel title = new JLabel("Chats", SwingConstants.CENTER);
        final int titleFontSize = 28;
        title.setFont(new Font(font, Font.BOLD, titleFontSize));
        titlePanel.add(title, BorderLayout.CENTER);

        this.add(titlePanel, BorderLayout.NORTH);

        // Chat List Area
        final Color listPanelColour = new Color(220, 220, 220);
        final int listPanelTop = 30;
        final int listPanelLeft = 40;
        final int listPanelBottom = 30;
        final int listPanelRight = 40;
        final JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(listPanelColour),
                BorderFactory.createEmptyBorder(listPanelTop, listPanelLeft, listPanelBottom, listPanelRight)
        ));

        chatListModel = new DefaultListModel<>();
        chatList = new JList<>(chatListModel);
        final int chatListFontSize = 16;
        chatList.setFont(new Font(font, Font.PLAIN, chatListFontSize));
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listPanel.add(new JScrollPane(chatList), BorderLayout.CENTER);
        this.add(listPanel, BorderLayout.CENTER);

        // Add Initiate Chat Button under scroll
        final Color initiateChatButtonColour = new Color(30, 144, 255);
        final int initiateChatButtonSize = 13;
        styleRoundedButton(initiateChatButton, initiateChatButtonColour, Color.WHITE,
                new Font(font, Font.BOLD, initiateChatButtonSize));
        final Dimension initiateChatButtonDimension = new Dimension(130, 32);
        initiateChatButton.setPreferredSize(initiateChatButtonDimension);
        initiateChatButton.setEnabled(false);

        final JPanel initiatePanel = new JPanel();
        initiatePanel.setBackground(Color.WHITE);
        initiatePanel.add(initiateChatButton);
        listPanel.add(initiatePanel, BorderLayout.SOUTH);

        // Buttons Panel
        final Color buttonPanelBackgroundColour = new Color(245, 248, 250);
        final int buttonPanelHgap = 20;
        final int buttonPanelVgap = 20;
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, buttonPanelHgap, buttonPanelVgap));
        buttonPanel.setBackground(buttonPanelBackgroundColour);

        final int buttonFontSize = 14;
        final Font buttonFont = new Font(font, Font.BOLD, buttonFontSize);

        final Color createChatButtonColour = new Color(70, 130, 180);
        styleRoundedButton(createChatButton, createChatButtonColour, Color.WHITE, buttonFont);

        // Same styling for friend and profile buttons
        final Color friendButtonColor = new Color(255, 165, 0);
        final Color profileButtonColor = new Color(130, 255, 100);
        styleRoundedButton(friendRequestsButton, friendButtonColor, Color.WHITE, buttonFont);
        styleRoundedButton(addFriendButton, friendButtonColor, Color.WHITE, buttonFont);
        styleRoundedButton(profileEditButton, profileButtonColor, Color.WHITE, buttonFont);

        final Color logoutButtonColour = new Color(240, 240, 240);
        styleRoundedButton(logoutButton, logoutButtonColour, Color.BLACK, buttonFont);

        // Add in button order
        buttonPanel.add(createChatButton);
        buttonPanel.add(addFriendButton);
        buttonPanel.add(friendRequestsButton);
        buttonPanel.add(profileEditButton);
        buttonPanel.add(logoutButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        createChatButton.addActionListener(evt -> {
            try {
                controller.newChat();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        chatList.addListSelectionListener(evt -> {
            initiateChatButton.setEnabled(true);
        });

        friendRequestsButton.addActionListener(evt -> {
            try {
                controller.switchToFriendRequestView();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        addFriendButton.addActionListener(evt -> {
            try {
                controller.switchToAddContact();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        profileEditButton.addActionListener(evt -> {
            try {
                controller.switchToProfileEdit();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        initiateChatButton.addActionListener(evt -> {
            final BaseUiState state = viewModel.getState();
            final java.util.List<String> chatnames = state.getChatnames();
            final java.util.List<DirectChatChannel> chatEntities = state.getChatEntities();

            final String name = chatList.getSelectedValue();
            final Integer index = chatnames.indexOf(name);
            System.out.println(index);
            System.out.println(chatnames);

            final DirectChatChannel chat = chatEntities.get(index);

            if (this.chatChannelView != null) {
                this.chatChannelView.dispose();
            }
            try {
                updateChatChannelController.execute(chat.getChatUrl());
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (updateChatChannelViewModel.getState().getError() != null) {
                JOptionPane.showMessageDialog(this, updateChatChannelViewModel.getState().getError());
                return;
            }
            final ChatChannelView newChatChannelView = new ChatChannelView(updateChatChannelViewModel);
            newChatChannelView.setUpdateChatChannelController(updateChatChannelController);
            newChatChannelView.setSendMessageController(sendMessageController);
            newChatChannelView.setBaseUiController(controller);
            final SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    return null;
                }

                @Override
                protected void done() {
                    SwingUtilities.invokeLater(() -> {
                        SwingUtilities.invokeLater(() -> {
                            final JScrollBar vbar = newChatChannelView.getScrollPane().getVerticalScrollBar();
                            vbar.setValue(vbar.getMaximum());
                        });
                    });
                }
            };
            worker.execute();
            this.chatChannelView = newChatChannelView;
            viewManager.addView(chatChannelView, chatChannelViewModel.getViewName());
            this.switchView(this.viewManagerModel, this.chatChannelViewModel);
        });

        logoutButton.addActionListener(evt -> {
            logoutController.logoutUser(sessionManager.getMainUser());
        });

        reloadChats(sessionManager.getMainUser().getUserChats());
    }

    private void styleRoundedButton(JButton button, Color backgroundColour, Color foregroundColour, Font font) {
        button.setFont(font);
        button.setBackground(backgroundColour);
        button.setForeground(foregroundColour);
        button.setFocusPainted(false);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        final Dimension buttonDimension = new Dimension(145, 45);
        button.setPreferredSize(buttonDimension);

        final int buttonThickness = 2;
        final boolean buttonIsRounded = true;
        final int buttonTop = 8;
        final int buttonLeft = 16;
        final int buttonBottom = 8;
        final int buttonRight = 16;
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(backgroundColour, buttonThickness, buttonIsRounded),
                BorderFactory.createEmptyBorder(buttonTop, buttonLeft, buttonBottom, buttonRight)
        ));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final BaseUiState state = viewModel.getState();
        chatListModel.clear();
        for (String chatName : state.getChatnames()) {
            chatListModel.addElement(chatName);
        }
        chatList.revalidate();
        chatList.repaint();
        this.revalidate();
        this.repaint();
    }

    /**
     * Switches views.
     * @param newViewManagerModel input view manager model,
     * @param newChatChannelViewModel input chat channel view model.
     */
    public void switchView(interface_adapter.ViewManagerModel newViewManagerModel,
                           ChatChannelViewModel newChatChannelViewModel) {
        newChatChannelViewModel.firePropertyChange();
        newViewManagerModel.setState(newChatChannelViewModel.getViewName());
        newViewManagerModel.firePropertyChange();
    }

    /**
     * Reloads all chats for the current user.
     * @param chatNames chat names for the current user.
     */
    private void reloadChats(List<String> chatNames) {
        chatListModel.clear();
        if (chatNames == null) {
            return;
        }
        for (String chatName : chatNames) {
            chatListModel.addElement(chatName);
        }
    }
}
