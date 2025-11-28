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

    public ChatChannelView(UpdateChatChannelViewModel updateChatChannelViewModel,
                           UpdateChatChannelController updateChatChannelController, SendMessageController sendMessageController) {

        this.updateChatChannelViewModel = updateChatChannelViewModel;
        this.updateChatChannelViewModel.addPropertyChangeListener(this);
        this.messageViewModel = new MessageViewModel();
        this.messageViewModel.addPropertyChangeListener(this);
        // OLD:
//        this.senderID = senderID;
//        this.receiverID = receiverID;
//        this.senderUsername = senderUsername;
//        this.receiverUsername = receiverUsername;
//        this.chatURL = chatURL;

        this.senderID = updateChatChannelViewModel.getState().getUser1ID();
        this.receiverID = updateChatChannelViewModel.getState().getUser2ID();
        this.senderUsername = updateChatChannelViewModel.getState().getUser1Name();
        this.receiverUsername = updateChatChannelViewModel.getState().getUser2Name();
        this.chatURL = updateChatChannelViewModel.getState().getChatURL();

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
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);
        scrollPane.setPreferredSize(new Dimension(400, 350));
        send = new JButton("Send");
        back = new JButton("Back");
        ChatPreviewPanel chatPreview = new ChatPreviewPanel(chatName, scrollPane, content, send, back);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chatPreview);

        // Back button
        back.addActionListener(event -> {
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
            messageState.setSenderID(senderID);
            messageState.setReceiverID(receiverID);
            messageState.setSenderName(senderUsername);
            messageState.setChannelURL(chatURL);
            messageState.setContent(message);

            // Execute the controller to send
            sendMessageController.execute(message, messageState.getChannelURL(), messageState.getSenderID(), messageState.getReceiverID());
            content.setText("");
            System.out.println("message sent");
            SwingUtilities.invokeLater(() -> {
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                boolean shouldScroll = vertical.getValue() + vertical.getVisibleAmount() >= vertical.getMaximum() - 20;
                if (shouldScroll) {
                    vertical.setValue(vertical.getMaximum());
                }
            });
        });
        startThread();
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
            chatName.setText(receiverUsername);
//            chatName.setText(state.getChatChannelName());
            updateMessage(state.getMessages());
        }
    }

    // Ensures receiver can see new message
    private void startThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (updateChatChannelController != null) {
                        UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
                        updateChatChannelController.execute(updateChatChannelState.getChatURL());
                        pending = false; // temporary message stored to database, safe to redraw
                    }
                    Thread.sleep(200);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // Redraw messages
    private void updateMessage(List<MessageViewModel> messages) {
        if (messages == null || messages.isEmpty()) {
            return;
        }
        if (!pending) { // Draw only after temporary message updates in database
            for (int i = lastRenderedCount; i < messages.size(); i++) {
                MessageViewModel message = messages.get(i);
                boolean isSelf = (message.getState().getSenderID().equals(senderID));
                String name = isSelf ? senderUsername : receiverUsername;
                MessagePanel messagePanel = new MessagePanel(new JLabel(name), new JLabel(message.getState().getContent()),
                        new JLabel(message.getState().getTimestamp().toString()));
                JPanel bubble = messagePanel.createBubble(
                        name,
                        message.getState().getContent(),
                        message.getState().getTimestamp().toString(),
                        isSelf
                );
                messageContainer.add(bubble);
                messageContainer.add(Box.createVerticalStrut(8));
                messageContainer.revalidate();
                messageContainer.repaint();
            }
            lastRenderedCount = messages.size();
            // Scroll only if user is at bottom
            JScrollBar v = scrollPane.getVerticalScrollBar();
            boolean stickToBottom = v.getValue() + v.getVisibleAmount() >= v.getMaximum() - 20;

            if (firstOpen) {
                firstOpen = false;

                SwingUtilities.invokeLater(() ->
                        SwingUtilities.invokeLater(() -> {
                            JScrollBar bar = scrollPane.getVerticalScrollBar();
                            bar.setValue(bar.getMaximum());
                        })
                );
            } else if (stickToBottom) {
            SwingUtilities.invokeLater(() -> v.setValue(v.getMaximum()));
            }

        }

//        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
//        scrollPane.revalidate();
//        scrollPane.repaint();
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
