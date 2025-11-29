package view;

import entity.User;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.search_contact.SearchContactController;
import interface_adapter.search_contact.SearchContactState;
import interface_adapter.search_contact.SearchContactViewModel;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

public class ProfileEditView extends JPanel implements PropertyChangeListener {

    public final String viewName = "search contact";
    private final SearchContactViewModel searchContactViewModel;
    private final SearchContactController searchContactController;
    private final AddContactController addContactController;
    private final baseUIController baseUIController;
    private final Session session;

    private final JTextField searchInputField = new JTextField(20);
    private final JButton searchButton = new JButton(SearchContactViewModel.SEARCH_BUTTON_LABEL);
    private final JButton backButton = new JButton(SearchContactViewModel.BACK_BUTTON_LABEL);
    private final JPanel resultsPanel = new JPanel();

    public ProfileEditView(SearchContactViewModel searchContactViewModel,
                           SearchContactController searchContactController,
                           AddContactController addContactController,
                           baseUIController baseUIController,
                           Session session) {
        this.searchContactViewModel = searchContactViewModel;
        this.searchContactController = searchContactController;
        this.addContactController = addContactController;
        this.baseUIController = baseUIController;
        this.session = session;

        this.searchContactViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(SearchContactViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Username:"));
        searchPanel.add(searchInputField);
        searchPanel.add(searchButton);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JPanel buttons = new JPanel();
        buttons.add(backButton);

        searchButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(searchButton)) {
                            try {
                                searchContactController.execute(searchInputField.getText());
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(ProfileEditView.this,
                                        "Database Error: " + e.getMessage());
                            }
                        }
                    }
                });

        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)) {
                            try {
                                baseUIController.displayUI();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(searchPanel);
        this.add(scrollPane);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            SearchContactState state = (SearchContactState) evt
                    .getNewValue();
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, state.getError());
            } else {
                updateResults(state.getResults());
            }
        }
    }

    private void updateResults(List<User> users) {
        resultsPanel.removeAll();
        if (users != null) {
            for (User user : users) {
                JPanel userPanel = new JPanel();
                userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                userPanel.add(new JLabel(user.getUsername()));

                JButton addButton = new JButton(SearchContactViewModel.ADD_BUTTON_LABEL);
                addButton.addActionListener(e -> {
                    try {
                        addContactController.execute(user.getUsername());
                        JOptionPane.showMessageDialog(this, "Friend request sent to " + user.getUsername());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error sending request: " + ex.getMessage());
                    }
                });
                userPanel.add(addButton);
                resultsPanel.add(userPanel);
            }
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}

