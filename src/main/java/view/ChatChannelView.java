package view;

import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.chat_channel.MessageState;
import interface_adapter.update_chat_channel.UpdateChatChannelState;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.chat_channel.SendMessageController;
import interface_adapter.chat_channel.MessageViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * View is for the user to see their chats.
 */
public class ChatChannelView extends JPanel implements PropertyChangeListener {
    // Variables required for view
    private final String viewName = "update chat channel";
    private final UpdateChatChannelViewModel updateChatChannelViewModel;
    private UpdateChatChannelController updateChatChannelController = null;
    private SendMessageController sendMessageController = null;
    private MessageViewModel messageViewModel;
    private baseUIViewModel baseUIViewModel;
    private baseUIController baseUIController = null;

    // GUI components
    private final JLabel chatName;
    private final JPanel messageContainer;
    private final JScrollPane scrollPane;
    private final JTextField content = new JTextField(15);
    private final JButton send;
    private final JButton back;

    // Variables to call interactors
    private String chatURL;
    private Integer senderID;
    private Integer receiverID;
    private String senderUsername;
    private String receiverUsername;
    private boolean pending;
    private int lastRenderedCount = 0;
    private boolean firstOpen = true;
    private Thread thread;
    private volatile boolean running = false;

    public ChatChannelView(UpdateChatChannelViewModel updateChatChannelViewModel, Integer senderID, Integer receiverID, String senderUsername, String receiverUsername, String chatURL,
                           UpdateChatChannelController updateChatChannelController, SendMessageController sendMessageController) {
        // Initialize variables
        this.updateChatChannelViewModel = updateChatChannelViewModel;
        this.updateChatChannelViewModel.addPropertyChangeListener(this);
        this.messageViewModel = new MessageViewModel();
        this.messageViewModel.addPropertyChangeListener(this);
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.chatURL = chatURL;
        this.updateChatChannelController = updateChatChannelController;
        this.sendMessageController = sendMessageController;

        // Set layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Chat Preview Panel
        chatName = new JLabel("");
        messageContainer = new JPanel();
        messageContainer.setLayout(new BoxLayout(messageContainer, BoxLayout.Y_AXIS));
        messageContainer.setAlignmentY(Component.TOP_ALIGNMENT);
        messageContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane = new JScrollPane(messageContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);  // <-- ADD THIS
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);
        scrollPane.setPreferredSize(new Dimension(400, 350));
        send = new JButton("Send");
        back = new JButton("Back");
        ChatPreviewPanel chatPreview = new ChatPreviewPanel(chatName, scrollPane, content, send, back);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chatPreview);

        back.addActionListener(event -> {
            dispose();
            try {
                baseUIController.displayUI();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // Send button
        send.addActionListener(evt -> {
            // Set new message state
            String message = content.getText();
            MessageState messageState = new MessageState();
            messageState.setSenderID(this.senderID);
            messageState.setReceiverID(this.receiverID);
            messageState.setSenderName(this.senderUsername);
            messageState.setChannelURL(chatURL);
            messageState.setContent(message);

            // Execute the controller to send
            sendMessageController.execute(message, messageState.getChannelURL(), messageState.getSenderID(), messageState.getReceiverID());
            content.setText("");
            SwingUtilities.invokeLater(() ->
                    scrollToBottom(scrollPane)
            );
        });

        messageContainer.removeAll();
        lastRenderedCount = 0;
        firstOpen = true;

        startThread();
    }

    public void clearMessages() {
        SwingUtilities.invokeLater(() -> {
            messageContainer.removeAll();
            messageContainer.revalidate();
            messageContainer.repaint();
        });
        lastRenderedCount = 0;
        firstOpen = true;
    }

    public void dispose() {
        running = false;
        if (thread != null) {
            thread.interrupt();
            try { thread.join(50); } catch (InterruptedException ignored) {}
        }

        try {
            updateChatChannelViewModel.removePropertyChangeListener(this);
        } catch (Exception ignored) {}

        try {
            if (messageViewModel != null) messageViewModel.removePropertyChangeListener(this);
        } catch (Exception ignored) {}
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final UpdateChatChannelState state = (UpdateChatChannelState) evt.getNewValue();
//            if (chatURL == null) {
//                chatURL = state.getChatURL();
//                senderID = state.getUser1ID();
//                receiverID = state.getUser2ID();
//                senderUsername = state.getUser1Name();
//            }
            chatName.setText(state.getChatChannelName());
//            chatName.setText(state.getChatChannelName());
            updateMessage(state.getMessages());
        }
    }

    // Ensures receiver can see new message
    private void startThread() {
        running = true;
        this.thread = new Thread(() -> {
            while (running) {
                try {
                    if (updateChatChannelController != null) {
                        UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
                        if (updateChatChannelState != null && updateChatChannelState.getChatURL() != null) {
                            updateChatChannelController.execute(updateChatChannelState.getChatURL());
                            pending = false;
                        }
                    }
                    Thread.sleep(200);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "ChatChannelView-poller");
        thread.setDaemon(true);
        thread.start();
    }

    // Redraw messages
    private void updateMessage(List<MessageViewModel> messages) {
        if (messages == null) return;

        if (messages.size() == lastRenderedCount) return;

        for (int i = lastRenderedCount; i < messages.size(); i++) {
            MessageViewModel message = messages.get(i);
            boolean isSelf = (message.getState().getSenderID().equals(this.senderID));
            String name = isSelf ? senderUsername : receiverUsername;

            MessagePanel messagePanel = new MessagePanel(new JLabel(name),
                    new JLabel(message.getState().getContent()),
                    new JLabel(message.getState().getTimestamp().toString()));
            JPanel bubble = messagePanel.createBubble(
                    message.getState().getSenderName(),
                    message.getState().getContent(),
                    message.getState().getTimestamp().toString(),
                    isSelf
            );

            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
            wrapper.setOpaque(false);

            if (isSelf) {
                wrapper.add(Box.createHorizontalGlue());
                wrapper.add(bubble);
            } else {
                wrapper.add(bubble);
                wrapper.add(Box.createHorizontalGlue());
            }

            messageContainer.add(wrapper);
            messageContainer.add(Box.createVerticalStrut(8));
        }

        lastRenderedCount = messages.size();

        boolean wasAtBottom = isAtBottom(scrollPane);

        messageContainer.revalidate();
        messageContainer.repaint();

        SwingUtilities.invokeLater(() ->
                SwingUtilities.invokeLater(() -> {
                    if (firstOpen || wasAtBottom) {
                        firstOpen = false;
                        scrollToBottom(scrollPane);
                    }
                })
        );
    }

    private boolean isAtBottom(JScrollPane scrollPane) {
        JScrollBar sb = scrollPane.getVerticalScrollBar();
        int value = sb.getValue();
        int extent = sb.getModel().getExtent();
        int max = sb.getMaximum();

        return value + extent >= max - 20;
    }

    private void scrollToBottom(JScrollPane scrollPane) {
        SwingUtilities.invokeLater(() -> {
            JScrollBar sb = scrollPane.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        });
    }

    private void smoothScrollToBottom(JScrollPane scrollPane) {
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        int target = bar.getMaximum();

        final int[] step = {0};
        final int steps = 10; // number of animation frames (higher = slower/smoother)

        Runnable animator = new Runnable() {
            @Override
            public void run() {
                step[0]++;
                int current = bar.getValue();
                int next = current + (target - current) / (steps - step[0] + 1);

                bar.setValue(next);

                if (step[0] < steps) {
                    SwingUtilities.invokeLater(this);
                } else {
                    // force-final align
                    bar.setValue(target);
                }
            }
        };

        SwingUtilities.invokeLater(animator);
    }

    public void setBaseUIController(baseUIController baseUIController) {
        this.baseUIController = baseUIController;
    }

    public void setUpdateChatChannelController(UpdateChatChannelController updateChatChannelController) {
        this.updateChatChannelController = updateChatChannelController;
    }

    public void setSendMessageController(SendMessageController sendMessageController) {
        this.sendMessageController = sendMessageController;
    }

    public String getContent() {
        return content.getText();
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }
}