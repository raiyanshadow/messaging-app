package view;

import javax.swing.*;
import java.awt.*;

public class RoundedMessageBubble extends JPanel {
    private Color bubbleColor;

    public RoundedMessageBubble(Color bubbleColor) {
        this.bubbleColor = bubbleColor;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // padding inside bubble
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(bubbleColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);
    }
}
