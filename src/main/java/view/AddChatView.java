import javax.swing.*;
import java.awt.*;

public class CreateChatView extends JFrame {

    private final JButton createChatButton;
    private final JList<String> userList;

    public CreateChatView() {
        setTitle("Create Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // --- Main panel ---
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Label ---
        JLabel label = new JLabel("Select a user to start a chat with:");
        panel.add(label, BorderLayout.NORTH);

        // --- List of users (single selection) ---
        String[] users = {"Alice", "Bob", "Charlie", "Diana"};
        userList = new JList<>(users);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // --- Button ---
        createChatButton = new JButton("Create Chat");
        createChatButton.setEnabled(false); // disabled until selection

        // --- Button click action ---
        createChatButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Creating chat with " + selectedUser,
                        "Chat Created",
                        JOptionPane.INFORMATION_MESSAGE
                );
                // TODO: Hook up interactor or presenter call here
            }
        });

        // --- Enable button only when a user is selected ---
        userList.addListSelectionListener(e -> {
            createChatButton.setEnabled(userList.getSelectedIndex() != -1);
        });

        // --- Button panel ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createChatButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    // Main method showing the window directly
    public static void main(String[] args) {
        CreateChatView view = new CreateChatView();
        view.setVisible(true);
    }
}
