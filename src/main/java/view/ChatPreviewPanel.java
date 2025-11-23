package view;

import javax.swing.*;
import java.awt.*;

public class ChatPreviewPanel extends JPanel {
    ChatPreviewPanel(JLabel chatName, JScrollPane messages, JTextField content, JButton send, JButton back) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        chatName.setHorizontalAlignment(JLabel.CENTER);
        back.setHorizontalAlignment(JLabel.RIGHT);
        titlePanel.add(chatName, BorderLayout.CENTER);
        titlePanel.add(back, BorderLayout.EAST);
        this.add(titlePanel);

        // Messages
        this.add(messages);

        // Send panel
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BoxLayout(sendPanel, BoxLayout.X_AXIS));
        sendPanel.add(content);
        sendPanel.add(send);
        this.add(sendPanel);
    }
}
