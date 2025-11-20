package view;

import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactState;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.ViewManagerModel;

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
    private final ViewManagerModel viewManagerModel;


    public AddContactView(AddContactViewModel addContactViewModel, baseUIViewModel baseUIViewModel, ViewManagerModel viewManagerModel) {
        this.addContactViewModel = addContactViewModel;
        this.viewManagerModel = viewManagerModel;
        addContactViewModel.addPropertyChangeListener(this);
        viewManagerModel.addPropertyChangeListener(this);

        // initialize back button and add button
        JButton backButton = new JButton(AddContactViewModel.BACK_BUTTON_LABEL);
        JButton addButton = new JButton(AddContactViewModel.ADD_CONTACT_BUTTON_LABEL);
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        addButton.setFont(buttonFont);


        // create title panel
        JLabel title = new JLabel(labelName, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBackground(Color.WHITE);

        // create back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        backPanel.add(backButton);
        backPanel.setBackground(Color.WHITE);


        // create top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.CENTER);
        topPanel.add(backPanel, BorderLayout.EAST);

        // create add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(addButton);
        buttonPanel.setBackground(Color.WHITE);



        // create middle panel
        JPanel midPanel = new JPanel();
        JLabel userinputLabel = new JLabel(AddContactViewModel.USERNAME_LABEL, SwingConstants.CENTER);
        userinputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        midPanel.add(userinputLabel);
        midPanel.setBackground(Color.WHITE);
        midPanel.add(Box.createVerticalStrut(20));
        midPanel.add(AddContactViewModel.USERNAME_LABEL, usernameField);
        midPanel.add(Box.createVerticalStrut(50));


        // back button action listener
        backButton.addActionListener(e -> {
            // heading back to baseUI view
            System.out.println("Back button pressed -> head back to base UI view");
            baseUIViewModel.setState(new baseUIState());
            viewManagerModel.setState(baseUIViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        });

        // add button action listener
        addButton.addActionListener(evt -> {
            AddContactState state = addContactViewModel.getState();
            try {
                addContactController.execute(
                        state.getSender(),
                        state.getReceiver_username()
                );
                System.out.println(usernameField.getText() + " is the username we want to add");
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        // this.add(backPanel, BorderLayout.EAST);
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
