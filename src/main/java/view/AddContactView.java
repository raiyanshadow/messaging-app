package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactState;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.BaseUiController;
import interface_adapter.search_contact.SearchContactController;

/**
 * Add contact view.
 */

public class AddContactView extends JPanel implements PropertyChangeListener {

    private final AddContactViewModel addContactViewModel;
    private final JTextField usernameField;
    private final CustomEditor customEditor;
    private AddContactController addContactController;
    private SearchContactController searchContactController;

    /**
     * Add contact view.
     * @param addContactViewModel view model to update anything on the view according to property changes.
     * @param baseUiController base ui controller to handle logic.
     */
    public AddContactView(AddContactViewModel addContactViewModel, BaseUiController baseUiController) {
        final String font = "SansSerif";
        final int usernameFieldColumns = 20;
        this.addContactViewModel = addContactViewModel;
        customEditor = new CustomEditor();
        usernameField = new JTextField(usernameFieldColumns);

        addContactViewModel.addPropertyChangeListener(this);

        // initialize back button and add button
        final JButton backButton = new JButton(AddContactViewModel.BACK_BUTTON_LABEL);
        final JButton addButton = new JButton(AddContactViewModel.ADD_CONTACT_BUTTON_LABEL);
        final int buttonFontSize = 14;
        final Font buttonFont = new Font(font, Font.BOLD, buttonFontSize);

        backButton.setFont(buttonFont);
        final int backButtonRed = 96;
        final int backButtonGreen = 179;
        final int backButtonBlue = 120;
        backButton.setBackground(new Color(backButtonRed, backButtonGreen, backButtonBlue));
        backButton.setForeground(Color.WHITE);
        final int backButtonWidth = 140;
        final int backButtonHeight = 42;
        final int backButtonThickness = 1;
        final boolean backButtonIsRounded = true;
        backButton.setPreferredSize(new Dimension(backButtonWidth, backButtonHeight));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(backButtonRed, backButtonGreen, backButtonBlue),
                backButtonThickness, backButtonIsRounded));

        addButton.setFont(buttonFont);
        final int addButtonRed = 70;
        final int addButtonGreen = 130;
        final int addButtonBlue = 180;
        addButton.setBackground(new Color(addButtonRed, addButtonGreen, addButtonBlue));
        addButton.setForeground(Color.WHITE);
        final int addButtonWidth = 300;
        final int addButtonHeight = 42;
        final int addButtonThickness = 1;
        final boolean addButtonIsRounded = true;
        addButton.setPreferredSize(new Dimension(addButtonWidth, addButtonHeight));
        addButton.setBorder(BorderFactory.createLineBorder(new Color(addButtonRed, addButtonGreen, addButtonBlue),
                addButtonThickness, addButtonIsRounded));

        // create title panel
        final JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        final String labelName = "Add Contact";
        final JLabel title = new JLabel(labelName, SwingConstants.CENTER);
        final int titleFontSize = 14;
        title.setFont(new Font(font, Font.BOLD, titleFontSize));
        titlePanel.add(title, BorderLayout.CENTER);

        // create back button panel
        final int backPanelHgap = 20;
        final int backPanelVgap = 10;
        final JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, backPanelHgap, backPanelVgap));
        backPanel.add(backButton);
        backPanel.setBackground(Color.WHITE);

        // create top panel
        final JPanel topPanel = new JPanel(new BorderLayout());
        final int titlePanelWidth = 300;
        final int titlePanelHeight = 40;
        titlePanel.setPreferredSize(new Dimension(titlePanelWidth, titlePanelHeight));
        topPanel.add(backPanel, BorderLayout.SOUTH);

        // create add button panel
        final int buttonPanelHgap = 20;
        final int buttonPanelVgap = 10;
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, buttonPanelHgap, buttonPanelVgap));
        buttonPanel.add(addButton);
        buttonPanel.setBackground(Color.WHITE);

        // create middle panel
        final int midPanelRed = 220;
        final int midPanelGreen = 220;
        final int midPanelBlue = 220;
        final int midPanelTop = 30;
        final int midPanelLeft = 40;
        final int midPanelBottom = 40;
        final int midPanelRight = 40;
        final JPanel midPanel = new JPanel();
        midPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(midPanelRed, midPanelGreen, midPanelBlue)),
                BorderFactory.createEmptyBorder(midPanelTop, midPanelLeft, midPanelBottom, midPanelRight)
        ));

        final JLabel userInputLabel = new JLabel(AddContactViewModel.USERNAME_LABEL, SwingConstants.CENTER);
        final int userInputFontSize = 28;
        userInputLabel.setFont(new Font("SansSerif", Font.BOLD, userInputFontSize));
        userInputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        final int midPanelVerticalStructHeight = 100;
        final int midPanelVerticalStructHeight2 = 30;
        midPanel.add(Box.createVerticalStrut(midPanelVerticalStructHeight));
        midPanel.add(userInputLabel);
        midPanel.add(Box.createVerticalStrut(midPanelVerticalStructHeight2));
        midPanel.setBackground(Color.WHITE);
        final int usernameFieldWidth = 100;
        final int usernameFieldHeight = 30;
        usernameField.setPreferredSize(new Dimension(usernameFieldWidth, usernameFieldHeight));
        // original user input field

        // try making new JCombo box
        final JComboBox<String> inputBox = new JComboBox<>();
        inputBox.setEditable(true);
        inputBox.setEditor(customEditor);

        midPanel.add(inputBox);
        final int midPanelVerticalStructHeight3 = 100;
        midPanel.add(Box.createVerticalStrut(midPanelVerticalStructHeight3));

        // action listener for the inputBox
        inputBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

                final String userInput = customEditor.getItem().toString();
                if (userInput != null) {
                    try {

                        searchContactController.execute(userInput);

                        final AddContactState state = addContactViewModel.getState();
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
                final String userInput = customEditor.getItem().toString();
                customEditor.setItem(userInput);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                final String userInput = customEditor.getItem().toString();
                customEditor.setItem(userInput);
            }
        });

        // back button action listener
        backButton.addActionListener(evt -> {
            // heading back to baseUI view
            try {
                baseUiController.displayUi();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // add button action listener

        addButton.addActionListener(evt -> {
            AddContactState state = addContactViewModel.getState();
            try {
                addContactController.execute(state.getUsernameInput());
                customEditor.setItem("");
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            state = new AddContactState();
            addContactViewModel.setState(state);
        });

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.addUsernameListener();
    }

    private void addUsernameListener() {
        final JTextField editorField = (JTextField) customEditor.getEditorComponent();
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
                final AddContactState state = addContactViewModel.getState();
                if (editorField.getText().isEmpty()) {
                    state.setUsername(null);
                }
                else {
                    state.setUsername(editorField.getText());
                }
                addContactViewModel.setState(state);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!(evt.getNewValue() instanceof AddContactState)) {
            return;
        }

        final AddContactState state = (AddContactState) evt.getNewValue();
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
