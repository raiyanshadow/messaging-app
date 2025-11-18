package view;

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
import java.util.List;

/**
 * View is for the user to see their chats.
 */
public class ChatChannelView extends JPanel implements PropertyChangeListener {
    private final String viewName = "update chat channel";
    private final UpdateChatChannelViewModel updateChatChannelViewModel;
    private UpdateChatChannelController updateChatChannelController = null;
    private SendMessageController sendMessageController = null;
    private MessageViewModel messageViewModel;

    private final JLabel chatName;
    private final JPanel messageContainer;
    private final JScrollPane scrollPane;
    private final JTextField content = new JTextField(15);
    private final JButton send;

//    private final String chatURL;
//    private final Integer senderID;
//    private final Integer recieverID;
    public ChatChannelView(UpdateChatChannelViewModel updateChatChannelViewModel) {
        this.updateChatChannelViewModel = updateChatChannelViewModel;
        this.updateChatChannelViewModel.addPropertyChangeListener(this);
        this.messageViewModel = new MessageViewModel();
        this.messageViewModel.addPropertyChangeListener(this);

//        this.chatURL = updateChatChannelViewModel.getState().getChatURL();
//        this.senderID = updateChatChannelViewModel.getState().getUser1ID();
//        this.recieverID = updateChatChannelViewModel.getState().getUser2ID();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title of screen
        final JLabel title = new JLabel("Chat Display"); // TODO: Get it to show with the name
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);

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
        ChatPreviewPanel chatPreview = new ChatPreviewPanel(chatName, scrollPane, content, send);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chatPreview);

        send.addActionListener(
                evt -> {
                    String message = content.getText();
                    UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
                    try {
                        updateChatChannelController.execute(updateChatChannelState.getChatURL());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    MessageState messageState = new MessageState();
                    System.out.println("messages length:" + updateChatChannelState.getMessages().size());
                    messageState.setSenderName(updateChatChannelState.getUser1Name());
                    messageState.setSenderID(updateChatChannelState.getUser1ID());
                    messageState.setReceiverID(updateChatChannelState.getUser2ID());
                    messageState.setChannelURL(updateChatChannelState.getChatURL());
                    messageState.setContent(message);
                    messageViewModel.setState(messageState);
                    sendMessageController.execute(message, messageState.getChannelURL(), messageState.getSenderID(), messageState.getReceiverID());
                    content.setText("");
                    System.out.println("message sent");
                    System.out.println("messages length:" + updateChatChannelState.getMessages().size());
                    try {
                        updateChatChannelController.execute(updateChatChannelState.getChatURL());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final UpdateChatChannelState state = (UpdateChatChannelState) evt.getNewValue();
            chatName.setText(state.getChatChannelName());
            updateMessage(state.getMessages());
        }
    }

    private void updateMessage(List<MessageViewModel> messages) {
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

        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });

//        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
//        scrollPane.revalidate();
//        scrollPane.repaint();
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
