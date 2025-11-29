package view;

import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactState;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.ViewManagerModel;
import session.Session;

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
    private final Session sessionmanager;
    private final baseUIController baseUIController;


    public AddContactView(AddContactViewModel addContactViewModel, ViewManagerModel viewManagerModel, Session sessionmanager, baseUIController baseUIController) {
        this.addContactViewModel = addContactViewModel;
        this.viewManagerModel = viewManagerModel;
        this.sessionmanager = sessionmanager;
        this.baseUIController = baseUIController;

        addContactViewModel.addPropertyChangeListener(this);


        // initialize back button and add button
        JButton backButton = new JButton(AddContactViewModel.BACK_BUTTON_LABEL);
        JButton addButton = new JButton(AddContactViewModel.ADD_CONTACT_BUTTON_LABEL);
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(96, 179, 120));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(140, 42));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(96, 179, 120), 1, true));

        addButton.setFont(buttonFont);
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(300, 42));
        addButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true));


        // create title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel title = new JLabel(labelName, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        titlePanel.add(title, BorderLayout.CENTER);

        // create back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        backPanel.add(backButton);
        backPanel.setBackground(Color.WHITE);


        // create top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(300, 40));
        // topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(backPanel, BorderLayout.SOUTH);

        // create add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(addButton);
        buttonPanel.setBackground(Color.WHITE);



        // create middle panel
        JPanel midPanel = new JPanel();
        midPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel userinputLabel = new JLabel(AddContactViewModel.USERNAME_LABEL, SwingConstants.CENTER);
        userinputLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        userinputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        midPanel.add(Box.createVerticalStrut(100));
        midPanel.add(userinputLabel);
        midPanel.add(Box.createVerticalStrut(30));
        midPanel.setBackground(Color.WHITE);
        usernameField.setPreferredSize(new Dimension(100, 30));
        midPanel.add(usernameField);
        midPanel.add(Box.createVerticalStrut(100));


        // back button action listener
        backButton.addActionListener(e -> {
            // heading back to baseUI view
            System.out.println("Back button pressed");
            try {
                baseUIController.displayUI();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // add button action listener

        addButton.addActionListener(evt -> {
            AddContactState state = addContactViewModel.getState();
            state.setSender(sessionmanager.getMainUser());
            try {
                addContactController.execute(
                        state.getSender(),
                        state.getUsernameInput()
                );
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            state =  new AddContactState();
            addContactViewModel.setState(state);
        });

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        // this.add(backPanel, BorderLayout.EAST);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.addUsernameListener();

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
        if (!(evt.getNewValue() instanceof AddContactState)) {
            return;
        }

        AddContactState state = (AddContactState) evt.getNewValue();
        if (state.getAddContactError() != null) {
            JOptionPane.showMessageDialog(this, state.getAddContactError());
        }
        if (state.getSuccess_message() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccess_message());
        }
    }



    public void setAddContactController(AddContactController addContactController) {
        this.addContactController = addContactController;
    }

}