package view;

import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactState;
import interface_adapter.add_contact.AddContactViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class AddContactView extends JPanel implements PropertyChangeListener {

    private final String labelName = "Add Contact";
    private final AddContactViewModel addContactViewModel;
    private final JTextField usernameField =  new JTextField(20);
    private AddContactController addContactController = null;

    private final JButton backButton;
    private final JButton addButton;


    public AddContactView(AddContactViewModel addContactViewModel) {
        this.addContactViewModel = addContactViewModel;
        addContactViewModel.addPropertyChangeListener(this);

        // initialize back button and add button
        backButton = new JButton(addContactViewModel.BACK_BUTTON_LABEL);
        addButton = new JButton(addContactViewModel.ADD_CONTACT_BUTTON_LABEL);
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        addButton.setFont(buttonFont);

        // create title panel
        JLabel title = new JLabel(addContactViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBackground(Color.WHITE);

        // create add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(addButton);
        buttonPanel.setBackground(Color.WHITE);

        // create back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        backPanel.add(backButton);
        backPanel.setBackground(Color.WHITE);

        // create middle panel
        JPanel midPanel = new JPanel();
        JLabel userinputLabel = new JLabel(addContactViewModel.USERNAME_LABEL, SwingConstants.CENTER);
        userinputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        midPanel.add(userinputLabel);
        midPanel.setBackground(Color.WHITE);
        midPanel.add(Box.createVerticalStrut(20));
        midPanel.add(addContactViewModel.USERNAME_LABEL, usernameField);
        midPanel.add(Box.createVerticalStrut(50));


        // back button action listener
        backButton.addActionListener(evt -> addContactController.switchToContactsView());


        // add button action listener
        addButton.addActionListener(evt -> {
            AddContactState state = addContactViewModel.getState();
            try {
                addContactController.execute(
                        state.getSender(),
                        state.getReceiver_username()
                );
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(backPanel, BorderLayout.EAST);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);


    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    private void addUsernameListener() {
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateState();
            }

            private void updateState() {
                AddContactState state = addContactViewModel.getState();
                state.setUsername(usernameField.getText());
                addContactViewModel.setState(state);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }


    public void setAddContactController(AddContactController addContactController) {
        this.addContactController = addContactController;
    }

}
