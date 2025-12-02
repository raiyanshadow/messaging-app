package view;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import interface_adapter.base_UI.baseUIController;
import interface_adapter.chat_channel.MessageState;
import interface_adapter.chat_channel.MessageViewModel;
import interface_adapter.chat_channel.SendMessageController;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelState;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;

/**
 * View is for the user to see their chats.
 */
public class ChatChannelView extends JPanel implements PropertyChangeListener {
    private final UpdateChatChannelViewModel updateChatChannelViewModel;
    private UpdateChatChannelController updateChatChannelController;
    private SendMessageController sendMessageController;
    private MessageViewModel messageViewModel;
    private baseUIController baseUiController;

    // GUI components
    private final JLabel chatName;
    private final JPanel messageContainer;
    private final JScrollPane scrollPane;
    private final JTextField content = new JTextField(15);

    // Variables to call interactors
    private final String chatUrl;
    private final Integer senderID;
    private final Integer receiverID;
    private final String senderUsername;
    private final String receiverUsername;
    private int lastRenderedCount;
    private boolean firstOpen = true;
    private Thread thread;
    private volatile boolean running;

    public ChatChannelView(UpdateChatChannelViewModel updateChatChannelViewModel) {
        // Initialize variables
        this.updateChatChannelViewModel = updateChatChannelViewModel;
        this.updateChatChannelViewModel.addPropertyChangeListener(this);
        this.messageViewModel = new MessageViewModel();
        this.messageViewModel.addPropertyChangeListener(this);
        this.senderID = updateChatChannelViewModel.getState().getUser1ID();
        this.receiverID = updateChatChannelViewModel.getState().getUser2ID();
        this.senderUsername = updateChatChannelViewModel.getState().getUser1Name();
        this.receiverUsername = updateChatChannelViewModel.getState().getUser2Name();
        this.chatUrl = updateChatChannelViewModel.getState().getChatUrl();

        // Set layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Chat Preview Panel
        chatName = new JLabel("");
        messageContainer = new JPanel();
        messageContainer.setLayout(new BoxLayout(messageContainer, BoxLayout.Y_AXIS));
        messageContainer.setAlignmentY(Component.TOP_ALIGNMENT);
        messageContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane = new JScrollPane(messageContainer);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);
        scrollPane.setPreferredSize(new Dimension(400, 430));
        final JButton sendButton = new JButton("Send");
        final JButton backButton = new JButton("Back");
        final ChatPreviewPanel chatPreview = new ChatPreviewPanel(chatName, scrollPane, content, sendButton, backButton);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chatPreview);

        backButton.addActionListener(event -> {
            dispose();
            try {
                baseUiController.displayUI();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Send button
        sendButton.addActionListener(evt -> {
            // Set new message state
            final String message = content.getText();
            final MessageState messageState = new MessageState();
            messageState.setSenderID(this.senderID);
            messageState.setReceiverID(this.receiverID);
            messageState.setSenderName(this.senderUsername);
            messageState.setChannelURL(chatUrl);
            messageState.setContent(message);

            // Execute the controller to send
            sendMessageController.execute(message, messageState.getChannelURL(), messageState.getReceiverID());
            content.setText("");
            SwingUtilities.invokeLater(() -> {
                scrollToBottom(scrollPane); // check
            });
        });

        messageContainer.removeAll();
        lastRenderedCount = 0;
        firstOpen = true;

        startThread();
    }

    /**
     * Clear messages when you exit the view.
     */
    public void clearMessages() {
        SwingUtilities.invokeLater(() -> {
            messageContainer.removeAll();
            messageContainer.revalidate();
            messageContainer.repaint();
        });
        lastRenderedCount = 0;
        firstOpen = true;
    }

    /**
     * Interrupt and dispose the thread.
     */
    public void dispose() {
        running = false;
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join(50);
            }
            catch (InterruptedException ignored) {
                // Do nothing if thread cannot be joined
            }
        }

        try {
            updateChatChannelViewModel.removePropertyChangeListener(this);
        }
        catch (Exception ignored) {
            // Do nothing if property change listener is not removed
        }

        try {
            if (messageViewModel != null) {
                messageViewModel.removePropertyChangeListener(this);
            }
        }
        catch (Exception ignored) {
            // Do nothing if property change listener is not removed
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final UpdateChatChannelState state = (UpdateChatChannelState) evt.getNewValue();
            chatName.setText(receiverUsername);
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
                        final UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
                        if (updateChatChannelState != null && updateChatChannelState.getChatUrl() != null) {
                            updateChatChannelController.execute(updateChatChannelState.getChatUrl());
                        }
                    }
                    Thread.sleep(200);
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                catch (InterruptedException ex) {
                    break;
                }
            }
        }, "ChatChannelView-poller");
        thread.setDaemon(true);
        thread.start();
    }

    // Redraw messages
    private void updateMessage(List<MessageViewModel> messages) {
        if (messages == null) {
            return;
        }

        if (messages.size() == lastRenderedCount) {
            return;
        }

        for (int i = lastRenderedCount; i < messages.size(); i++) {
            final MessageViewModel message = messages.get(i);
            final boolean isSelf = message.getState().getSenderID().equals(this.senderID);
            System.out.println(this.senderID);
            System.out.println(this.receiverID);
            final String name = isSelf ? senderUsername : receiverUsername;

            final MessagePanel messagePanel = new MessagePanel(new JLabel(name),
                    new JLabel(message.getState().getContent()),
                    new JLabel(message.getState().getTimestamp().toString()));
            final JPanel bubble = messagePanel.createBubble(
                    message.getState().getSenderName(),
                    message.getState().getContent(),
                    message.getState().getTimestamp().toString(),
                    isSelf
            );

            final JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
            wrapper.setOpaque(false);

            if (isSelf) {
                wrapper.add(Box.createHorizontalGlue());
                wrapper.add(bubble);
            }
            else {
                wrapper.add(bubble);
                wrapper.add(Box.createHorizontalGlue());
            }

            messageContainer.add(wrapper);
            messageContainer.add(Box.createVerticalStrut(8));
        }

        lastRenderedCount = messages.size();

        final boolean wasAtBottom = isAtBottom(scrollPane);

        messageContainer.revalidate();
        messageContainer.repaint();

        SwingUtilities.invokeLater(() -> {
            SwingUtilities.invokeLater(() -> {
                if (firstOpen || wasAtBottom) {
                    firstOpen = false;
                    scrollToBottom(scrollPane);
                }
            });
        });
    }

    private boolean isAtBottom(JScrollPane scrollPane) {
        final JScrollBar sb = scrollPane.getVerticalScrollBar();
        final int value = sb.getValue();
        final int extent = sb.getModel().getExtent();
        final int max = sb.getMaximum();

        return value + extent >= max - 20;
    }

    private void scrollToBottom(JScrollPane scrollPane) {
        SwingUtilities.invokeLater(() -> {
            final JScrollBar sb = scrollPane.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        });
    }

    public void setBaseUiController(baseUIController baseUiController) {
        this.baseUiController = baseUiController;
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

    public String getViewName() {
        // Variables required for view
        return "update chat channel";
    }
}
