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

import interface_adapter.base_UI.BaseUiController;
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
    private BaseUiController baseUiController;

    // GUI components
    private final JLabel chatName;
    private final JPanel messageContainer;
    private final JScrollPane scrollPane;
    private final JTextField content = new JTextField(15);
    private final JButton send;
    private final JButton back;

    // Variables to call interactors
    private final String chatUrl;
    private final Integer senderID;
    private final Integer receiverID;
    private final String senderUsername;
    private final String receiverUsername;
    private int lastRenderedCount;
    private boolean firstOpen = true;
    private Thread thread;
    private boolean running;

    public ChatChannelView(UpdateChatChannelViewModel updateChatChannelViewModel) {
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
        final int verticalScrollBarIncrement = 30;
        scrollPane.getVerticalScrollBar().setUnitIncrement(verticalScrollBarIncrement);
        final Dimension scrollPaneDimension = new Dimension(400, 350);
        scrollPane.setPreferredSize(scrollPaneDimension);
        send = new JButton("Send");
        back = new JButton("Back");
        final ChatPreviewPanel chatPreview = new ChatPreviewPanel(chatName, scrollPane, content, send, back);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chatPreview);

        back.addActionListener(event -> {
            dispose();
            try {
                baseUiController.displayUi();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Send button
        send.addActionListener(evt -> {
            // Set new message state
            final String message = content.getText();
            final MessageState messageState = new MessageState();
            messageState.setSenderID(this.senderID);
            messageState.setReceiverID(this.receiverID);
            messageState.setSenderName(this.senderUsername);
            messageState.setChannelUrl(chatUrl);
            messageState.setContent(message);

            // Execute the controller to send
            sendMessageController.execute(message, messageState.getChannelUrl(), messageState.getReceiverID());
            content.setText("");
            SwingUtilities.invokeLater(() -> {
                scrollToBottom(scrollPane);
            });
        });

        messageContainer.removeAll();
        lastRenderedCount = 0;
        firstOpen = true;

        startThread();
    }

    /**
     * Dispose all messges in a chat channel.
     */
    public void dispose() {
        running = false;
        if (thread != null) {
            thread.interrupt();
            try {
                final int threadJoinTimeMs = 50;
                thread.join(threadJoinTimeMs);
            }
            catch (InterruptedException ignored) {
                // empty catch
            }
        }

        updateChatChannelViewModel.removePropertyChangeListener(this);

        if (messageViewModel != null) {
            messageViewModel.removePropertyChangeListener(this);
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

    /**
     * Start a thread to update the chat channel and its contents.
     */
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
                    final int threadSleepTimeMs = 200;
                    Thread.sleep(threadSleepTimeMs);
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

    /**
     * Redraw messages onto JPanel.
     * @param messages messages to redraw
     */
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
            final String name;
            if (isSelf) {
                name = senderUsername;
            }
            else {
                name = receiverUsername;
            }

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
            final int messageContainerVerticalStructHeight = 8;
            messageContainer.add(Box.createVerticalStrut(messageContainerVerticalStructHeight));
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
                }); }
        );
    }

    private boolean isAtBottom(JScrollPane newScrollPane) {
        final JScrollBar scrollBar = newScrollPane.getVerticalScrollBar();
        final int value = scrollBar.getValue();
        final int extent = scrollBar.getModel().getExtent();
        final int max = scrollBar.getMaximum();
        final int threshold = 20;

        return value + extent >= max - threshold;
    }

    private void scrollToBottom(JScrollPane newScrollPane) {
        SwingUtilities.invokeLater(() -> {
            final JScrollBar sb = newScrollPane.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        });
    }

    public void setBaseUiController(BaseUiController baseUiController) {
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
