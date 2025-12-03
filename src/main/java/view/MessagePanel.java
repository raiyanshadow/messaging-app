package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Message panel helper class for chat channel view.
 */
public class MessagePanel extends JPanel {
    MessagePanel(JLabel name, JLabel content, JLabel timeStamp) {
        this.add(name);
        this.add(timeStamp);
        this.add(content);
    }

    JPanel createBubble(String sender, String content, String time, boolean isSelf) {
        final RoundedMessageBubble bubble;
        if (isSelf) {
            final Color bubbleColour = new Color(96, 250, 99);
            bubble = new RoundedMessageBubble(
                    bubbleColour);
        }
        else {
            final Color bubbleColour = new Color(74, 176, 241);
            bubble = new RoundedMessageBubble(
                    bubbleColour);
        }

        final int bubbleTop = 8;
        final int bubbleLeft = 12;
        final int bubbleBottom = 8;
        final int bubbleRight = 12;
        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setBorder(BorderFactory.createEmptyBorder(bubbleTop, bubbleLeft, bubbleBottom, bubbleRight));

        // -------- sender label --------
        final JLabel senderLabel = new JLabel(sender);
        final int senderLabelFontSize = 11;
        senderLabel.setFont(new Font("Arial", Font.BOLD, senderLabelFontSize));
        senderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // -------- content (wrapped text) --------
        final JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setOpaque(false);
        contentArea.setBorder(null);
        final int bubbleWidth = 300;
        final int minBubbleHeight = 40;
        final int offset = 10;
        contentArea.setMinimumSize(new Dimension(bubbleWidth, minBubbleHeight - offset));
        bubble.setMinimumSize(new Dimension(bubbleWidth, minBubbleHeight));
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        // -------- timestamp --------
        final JLabel timeLabel = new JLabel(time);
        final int timeLabelFontSize = 10;
        timeLabel.setFont(new Font("Arial", Font.PLAIN, timeLabelFontSize));
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // add components
        bubble.add(senderLabel);
        bubble.add(contentArea);
        bubble.add(timeLabel);

        // alignment of bubble in container
        final Dimension bubbleDimension = new Dimension(300, Integer.MAX_VALUE);
        bubble.setMaximumSize(bubbleDimension);
        if (isSelf) {
            bubble.setAlignmentX(Component.RIGHT_ALIGNMENT);
        }
        else {
            bubble.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        return bubble;
    }
}
