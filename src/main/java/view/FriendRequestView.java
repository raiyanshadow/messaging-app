package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import javax.swing.*;

import interface_adapter.base_UI.baseUIController;
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestState;
import interface_adapter.friend_request.FriendRequestViewModel;
import session.Session;

public class FriendRequestView extends JPanel implements PropertyChangeListener {

    private FriendRequestController friendRequestController = null;
    private final Session sessionManager;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public FriendRequestView(FriendRequestViewModel friendRequestViewModel, Session sessionManager, baseUIController baseUIController) {
        this.sessionManager = sessionManager;

        friendRequestViewModel.addPropertyChangeListener(this);
        final FriendRequestState state = friendRequestViewModel.getState();

        // initialize back button
        final JButton backButton = new JButton("Back");
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(96, 179, 120));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(140, 42));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(96, 179, 120), 1, true));

        // create title panel
        final JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        final String labelName = "Friend Requests";
        final JLabel title = new JLabel(labelName, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        titlePanel.add(title, BorderLayout.CENTER);

        // create back button panel
        final JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        backPanel.add(backButton);
        backPanel.setBackground(Color.WHITE);

        // create top panel (add in title panel and back button panel)
        final JPanel topPanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(300, 40));
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(backPanel, BorderLayout.SOUTH);

        // create mid panel
        final JPanel midPanel = new JPanel(new GridLayout(1, 3, 20, 20));

        midPanel.setBackground(new Color(245, 248, 250));
        midPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        // midPanel.setBackground(Color.WHITE);
        // create accept and decline buttons
        final JButton acceptButton = new JButton("Accept");
        final JButton declineButton = new JButton("Decline");
        acceptButton.setFont(buttonFont);
        acceptButton.setBackground(new Color(70, 130, 180));
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setPreferredSize(new Dimension(140, 42));
        acceptButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true));

        declineButton.setFont(buttonFont);
        declineButton.setBackground(new Color(255, 165, 0));
        declineButton.setForeground(Color.WHITE);
        declineButton.setPreferredSize(new Dimension(140, 42));
        declineButton.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 1, true));

        // create accept or decline panel
        final JPanel acceptOrDeclinePanel = new JPanel(new GridLayout(4, 1, 20, 20));
        acceptOrDeclinePanel.setBackground(new Color(245, 248, 250));
        acceptOrDeclinePanel.add(new JLabel());
        acceptOrDeclinePanel.add(acceptButton);
        acceptOrDeclinePanel.add(declineButton);
        acceptOrDeclinePanel.add(new JLabel());

        for (String friendRequest : sessionManager.getMainUser().getFriendRequests()) {
            listModel.addElement(friendRequest);
            System.out.println(friendRequest);
        }

        // Create the JList using the model
        final JList<String> scrollableList = new JList<>(listModel);
        scrollableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane scrollPane = new JScrollPane(scrollableList);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(

                BorderFactory.createEmptyBorder(30, 30, 30, 30),
                BorderFactory.createLineBorder(Color.BLACK)
        ));

        scrollPane.setPreferredSize(new Dimension(150, 100));

        midPanel.add(new JLabel());
        midPanel.add(scrollPane);
        midPanel.add(acceptOrDeclinePanel);

        // add all the panels
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);

        // back button action listener
        backButton.addActionListener(e -> {
            System.out.println("Back button pressed");
            try {
                baseUIController.displayUI();
                // triggers presenter → viewManager switching
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        acceptButton.addActionListener(evt -> {

            // state.setAcceptee(sessionManager.getMainUser());
            try {
                final String selectedName = state.getAcceptedUsername();

                friendRequestController.execute(
                        state.getAcceptedUsername(),
                        true
                );
                listModel.removeElement(selectedName);
                sessionManager.getMainUser().getFriendRequests().remove(selectedName);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        declineButton.addActionListener(evt -> {

            // state.setAcceptee(sessionManager.getMainUser());
            try {
                final String selectedName = state.getAcceptedUsername();

                friendRequestController.execute(
                        state.getAcceptedUsername(),
                        false
                );
                listModel.removeElement(selectedName);
                sessionManager.getMainUser().getFriendRequests().remove(selectedName);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        scrollableList.addListSelectionListener(e -> {
            // System.out.println(scrollableList.getSelectedValue());
            state.setAcceptedUsername(scrollableList.getSelectedValue());
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        listModel.clear();
        for (String fr : sessionManager.getMainUser().getFriendRequests()) {
            listModel.addElement(fr);
        }
        final FriendRequestState state = (FriendRequestState) evt.getNewValue();
        if (state.getFriendRequestError() != null) {
            JOptionPane.showMessageDialog(this, state.getFriendRequestError());
            state.setFriendRequestError(null);
        }
        if (state.getSuccessMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage());
            state.setSuccessMessage(null);
        }
    }

    public void setFriendRequestController(FriendRequestController friendRequestController) {
        this.friendRequestController = friendRequestController;
    }
}
