package view;

import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddChatView extends JFrame implements AddChatChannelViewModel.ViewModelListener {

    private final JTextField chatNameField = new JTextField(15);
    private final JButton createButton = new JButton("Create Chat");
    private final JLabel messageLabel = new JLabel();

    public void CreateChatView(AddChatChannelViewModel viewModel, AddChatChannelController controller) {
        viewModel.listeners.add(this);

        setLayout(new FlowLayout());
        add(new JLabel("Chat name:"));
        add(chatNameField);
        add(createButton);
        add(messageLabel);



        createButton.addActionListener(e ->
                controller.CreateChannel(chatNameField.getText()));

        setTitle("Create Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setVisible(true);
    }

    @Override
    public void onViewModelChange(AddChatChannelViewModel viewModel) {
        SwingUtilities.invokeLater(() -> {
            messageLabel.setForeground(viewModel.getHasError() ? Color.RED : Color.GREEN);
            messageLabel.setText(viewModel.getMessage());
        });

    }
}
