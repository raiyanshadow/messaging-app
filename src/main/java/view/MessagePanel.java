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

        JLabel senderLabel = new JLabel(sender);
        JLabel contentLabel = new JLabel("<html>" + content + "</html>");
        JLabel timeLabel = new JLabel(time);

        senderLabel.setFont(new Font("Arial", Font.BOLD, 11));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        timeLabel.setForeground(Color.GRAY);

        bubble.add(senderLabel);
        bubble.add(contentLabel);
        bubble.add(timeLabel);

        bubble.setAlignmentX(isSelf ? Component.LEFT_ALIGNMENT : Component.RIGHT_ALIGNMENT);

        return bubble;
    }
}
