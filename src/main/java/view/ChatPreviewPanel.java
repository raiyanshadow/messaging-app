package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Chat Preview Panel based in the Base UI view.
 */
public class ChatPreviewPanel extends JPanel {
    ChatPreviewPanel(JLabel chatName, JScrollPane messages, JTextField content, JButton send, JButton back) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title Panel
        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        chatName.setHorizontalAlignment(JLabel.CENTER);
        final int chatNameFontSize = 18;
        chatName.setFont(new Font("Arial", Font.BOLD, chatNameFontSize));
        back.setHorizontalAlignment(JLabel.RIGHT);
        titlePanel.add(chatName, BorderLayout.CENTER);
        titlePanel.add(back, BorderLayout.EAST);
        titlePanel.setSize(new Dimension());
        final Color titlePanelBackgroundColor = new Color(224, 224, 224);
        titlePanel.setBackground(titlePanelBackgroundColor);
        this.add(titlePanel);

        // Messages
        final Color messageBackgroundColor = new Color(255, 255, 255);
        messages.setBackground(messageBackgroundColor);
        this.add(messages);

        // Send panel
        final JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BoxLayout(sendPanel, BoxLayout.X_AXIS));
        sendPanel.add(content);
        sendPanel.add(send);
        final Color sendPanelBackgroundColor = new Color(224, 224, 224);
        sendPanel.setBackground(sendPanelBackgroundColor);
        this.add(sendPanel);
    }
}
