package view;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {
    MessagePanel(JLabel name, JLabel content, JLabel timeStamp) {
        this.add(name);
        this.add(timeStamp);
        this.add(content);
    }

    JPanel createBubble(String sender, String content, String time, boolean isSelf) {
        RoundedMessageBubble bubble = new RoundedMessageBubble(
                isSelf ? new Color(96, 250, 99) : new Color(74, 176, 241));

        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // -------- sender label --------
        JLabel senderLabel = new JLabel(sender);
        senderLabel.setFont(new Font("Arial", Font.BOLD, 11));
        senderLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // <--- FIX

        // -------- content (wrapped text) --------
        JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setOpaque(false);
        contentArea.setBorder(null);
        contentArea.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT); // <--- FIX

        // -------- timestamp --------
        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // <--- FIX

        // add components
        bubble.add(senderLabel);
        bubble.add(contentArea);
        bubble.add(timeLabel);

        // alignment of bubble in container
        bubble.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        bubble.setAlignmentX(isSelf ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);

        return bubble;
    }
}
