package view;

import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactState;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.search_contact.SearchContactController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class AddContactView extends JPanel implements PropertyChangeListener {

    private final AddContactViewModel addContactViewModel;
    private final JTextField usernameField =  new JTextField(20);
    private final CustomEditor customEditor = new CustomEditor();
    private AddContactController addContactController = null;
    private SearchContactController searchContactController = null;



    public AddContactView(AddContactViewModel addContactViewModel, baseUIController baseUIController) {
        this.addContactViewModel = addContactViewModel;

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
        String labelName = "Add Contact";
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

        JLabel userInputLabel = new JLabel(AddContactViewModel.USERNAME_LABEL, SwingConstants.CENTER);
        userInputLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        userInputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        midPanel.add(Box.createVerticalStrut(100));
        midPanel.add(userInputLabel);
        midPanel.add(Box.createVerticalStrut(30));
        midPanel.setBackground(Color.WHITE);
        usernameField.setPreferredSize(new Dimension(100, 30));
        // original user input field
        // midPanel.add(usernameField);

        // try making new JCombo box
        JComboBox<String> inputBox = new JComboBox<>();
        inputBox.setEditable(true);
        // CustomEditor customEditor = new CustomEditor();
        inputBox.setEditor(customEditor);


        midPanel.add(inputBox);
        midPanel.add(Box.createVerticalStrut(100));


        // action listener for the inputBox
        inputBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

                String userInput = customEditor.getItem().toString();
                // System.out.println(userInput);
                if (userInput != null) {
                    try {

                        searchContactController.execute(userInput);

                        AddContactState state = addContactViewModel.getState();
                        inputBox.removeAllItems();
                        for (String matchingUsername: state.getMatchingUsernames()) {
                            inputBox.addItem(matchingUsername);
                        }
                        customEditor.setItem(userInput);

                    }
                    catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                String userInput = customEditor.getItem().toString();
                customEditor.setItem(userInput);


            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                String userInput = customEditor.getItem().toString();
                customEditor.setItem(userInput);


            }
        });

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
            // state.setSender(sessionManager.getMainUser());
            try {
                addContactController.execute(state.getUsernameInput());
                customEditor.setItem("");
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

    private void addUsernameListener() {
        JTextField editorField = (JTextField) customEditor.getEditorComponent();
        editorField.getDocument().addDocumentListener(new DocumentListener() {
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
                //System.out.println(editorField.getText() + "this is what is in the input field");
                if (editorField.getText().isEmpty()) {
                    state.setUsername(null);
                }
                else { state.setUsername(editorField.getText());}
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
        if (state.getSuccessMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage());
        }
    }



    public void setAddContactController(AddContactController addContactController) {
        this.addContactController = addContactController;
    }

    public void setSearchContactController(SearchContactController searchContactController) {
        this.searchContactController = searchContactController;
    }
}