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

        JLabel title = new JLabel(addContactViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 248, 250));
        buttonPanel.add(addButton);


        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        backPanel.setBackground(new Color(245, 248, 250));
        backPanel.add(backButton);

        // middle panel
        JPanel midPanel = new JPanel(new GridLayout(4, 1, 15, 15));

        JLabel userinput = new JLabel(addContactViewModel.USERNAME_LABEL, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 12));

        midPanel.add(userinput);
        midPanel.setBackground(Color.WHITE);
        midPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));

        midPanel.add(addContactViewModel.USERNAME_LABEL, usernameField);
        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)) {
                            System.out.println("Back button pressed -> change back to contacts view");
                            // switch to contacts view
                            // addContactController.switchToContactsView();
                        }
                    }
                }
        );

        addButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(addButton)) {
                            //final AddContactState currentState = AddContactViewModel.getState();
                            System.out.println("add button pressed -> stay on current page (friend request sent!)");
                            // sends out add contact request
                            //addContactController.execute(currentState.getUser1, currentState.getUser2);
                        }
                    }
                }
        );


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
