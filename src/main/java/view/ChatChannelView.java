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
    private boolean pending;

    public ChatChannelView(UpdateChatChannelViewModel updateChatChannelViewModel, Integer senderID, Integer receiverID, String senderUsername, String chatURL) {
        // Initialize variables
        this.updateChatChannelViewModel = updateChatChannelViewModel;
        this.updateChatChannelViewModel.addPropertyChangeListener(this);
        this.messageViewModel = new MessageViewModel();
        this.messageViewModel.addPropertyChangeListener(this);
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.senderUsername = senderUsername;
        this.chatURL = chatURL;

//        this.chatURL = updateChatChannelViewModel.getState().getChatURL();
//        this.senderID = updateChatChannelViewModel.getState().getUser1ID();
//        this.recieverID = updateChatChannelViewModel.getState().getUser2ID();

        // Set layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title of screen
//        final JLabel title = new JLabel("Chat Display");
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
//        this.add(title);

        // Chat Preview Panel
        chatName = new JLabel("");
        messageContainer = new JPanel();
        messageContainer.setLayout(new BoxLayout(messageContainer, BoxLayout.Y_AXIS));
        messageContainer.setAlignmentY(Component.TOP_ALIGNMENT);
        messageContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane = new JScrollPane(messageContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 200));
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
//                    UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
//                    try {
//                        updateChatChannelController.execute(updateChatChannelState.getChatURL());
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
            MessageState messageState = new MessageState();
//                    System.out.println("messages length:" + updateChatChannelState.getMessages().size());
//                    messageState.setSenderName(updateChatChannelState.getUser1Name());
            messageState.setSenderID(senderID);
            messageState.setReceiverID(receiverID);
            messageState.setChannelURL(chatURL);
            messageState.setContent(message);
            messageViewModel.setState(messageState);

            // Display message instantly (overcomes lag)
            MessagePanel instantMessagePanel = new MessagePanel(new JLabel(senderUsername), new JLabel(messageState.getContent()),
                    new JLabel(LocalDateTime.now().toString()));
            instantMessagePanel.setLayout(new BoxLayout(instantMessagePanel, BoxLayout.Y_AXIS));
            instantMessagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            instantMessagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            messageContainer.add(instantMessagePanel);
            messageContainer.revalidate();
            messageContainer.repaint();
            pending = true; // This prevents the updateMessage from redrawing and creating a lag

            // Execute the controller to send
            sendMessageController.execute(message, messageState.getChannelURL(), messageState.getSenderID(), messageState.getReceiverID());
            content.setText("");
            System.out.println("message sent");
            SwingUtilities.invokeLater(() -> {
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }); // TODO: Fix where scrolling occurs

//                    System.out.println("messages length:" + updateChatChannelState.getMessages().size());
//                    try {
//                        updateChatChannelController.execute(updateChatChannelState.getChatURL());
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
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
            chatName.setText(state.getChatChannelName());
            updateMessage(state.getMessages());
        }
    }

    // Ensures receiver can see new message
    private void startThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
                    updateChatChannelController.execute(updateChatChannelState.getChatURL());
                    pending = false; // temporary message stored to database, safe to redraw
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
        System.out.println("entered updateMessage");
        if (messages == null || messages.isEmpty()) {
            messageContainer.revalidate();
            messageContainer.repaint();
            return;
        }
        if (!pending) { // Draw only after temporary message updates in database
            messageContainer.removeAll();
//        List<MessageViewModel> messages = updateChatChannelViewModel.getState().getMessages();
            for (MessageViewModel message : messages) {
                MessagePanel messagePanel = new MessagePanel(new JLabel(message.getState().getSenderName()), new JLabel(message.getState().getContent()),
                        new JLabel(message.getState().getTimestamp().toString()));
                messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
                messagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                messageContainer.add(messagePanel);
            }
            messageContainer.revalidate();
            messageContainer.repaint();
            SwingUtilities.invokeLater(() -> { // TODO: Fix scrolling after
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            });
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
}
