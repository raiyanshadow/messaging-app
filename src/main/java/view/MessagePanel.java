package view;

import javax.swing.*;

public class MessagePanel extends JPanel {
    MessagePanel(JLabel name, JLabel content, JLabel timeStamp) {
        this.add(name);
        this.add(timeStamp);
        this.add(content);
    }
}
