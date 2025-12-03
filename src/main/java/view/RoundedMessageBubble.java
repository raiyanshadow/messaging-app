package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * RoundedMesssageBubble helper class for message panel.
 */
public class RoundedMessageBubble extends JPanel {
    private Color bubbleColor;

    public RoundedMessageBubble(Color bubbleColor) {
        this.bubbleColor = bubbleColor;
        setOpaque(false);
        setLayout(new BorderLayout());
        final int borderTop = 10;
        final int borderLeft = 15;
        final int borderRight = 10;
        final int borderBottom = 15;
        setBorder(BorderFactory.createEmptyBorder(borderTop, borderLeft, borderBottom, borderRight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(bubbleColor);
        final int g2X = 0;
        final int g2Y = 0;
        final int arcWidth = 20;
        final int arcHeight = 20;
        g2.fillRoundRect(g2X, g2Y, getWidth(), getHeight(), arcWidth, arcHeight);

        super.paintComponent(g);
    }
}
