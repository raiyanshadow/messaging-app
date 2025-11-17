package view;

import javax.swing.*;

public class ChatPreviewPanel extends JPanel {
    ChatPreviewPanel(JLabel chatName, JScrollPane messages, JTextField content, JButton send) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(chatName);
        this.add(messages);
        this.add(content);
        this.add(send);

    }
}
