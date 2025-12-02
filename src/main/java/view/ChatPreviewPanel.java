package view;

import javax.swing.*;
import java.awt.*;

public class ChatPreviewPanel extends JPanel {
    ChatPreviewPanel(JLabel chatName, JScrollPane messages, JTextField content,
                     JButton sendButton, JButton backButton) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title Panel
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(new Color(224, 224, 224));
        chatName.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(chatName);

        // Back button panel
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(96, 179, 120));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(140, 42));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(96, 179, 120),
                1, true));
        backButtonPanel.setBackground(new Color(224, 224, 224));
        backButtonPanel.add(backButton);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(backButtonPanel, BorderLayout.EAST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        this.add(headerPanel);

        // Messages
        messages.setBackground(new Color(255, 255, 255));
        this.add(messages);

        // Send panel
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BoxLayout(sendPanel, BoxLayout.X_AXIS));
        sendPanel.add(content);
        sendPanel.add(sendButton);
        sendPanel.setBackground(new Color(224, 224, 224));
        this.add(sendPanel);
    }
}
