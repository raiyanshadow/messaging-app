package view;

import entity.Contact;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_contact.AddContactState;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestState;
import interface_adapter.friend_request.FriendRequestViewModel;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class FriendRequestView extends JPanel implements PropertyChangeListener {

    private final String labelName = "Friend Requests";
    private final FriendRequestViewModel friendRequestViewModel;
    private FriendRequestController friendRequestController = null;
    private final ViewManagerModel viewManagerModel;
    private final Session sessionmanager;
    private final baseUIController baseUIController;

    public FriendRequestView(FriendRequestViewModel friendRequestViewModel, ViewManagerModel viewManagerModel, Session sessionmanager, baseUIController baseUIController) {
        this.friendRequestViewModel = friendRequestViewModel;
        this.viewManagerModel = viewManagerModel;
        this.sessionmanager = sessionmanager;
        this.baseUIController = baseUIController;

        friendRequestViewModel.addPropertyChangeListener(this);


        FriendRequestState state = friendRequestViewModel.getState();
        // initialize back button
        JButton backButton = new JButton("Back");
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(96, 179, 120));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(140, 42));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(96, 179, 120), 1, true));

        // create title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel title = new JLabel(labelName, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        titlePanel.add(title, BorderLayout.CENTER);

        // create back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        backPanel.add(backButton);
        backPanel.setBackground(Color.WHITE);


        // create top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(300, 40));
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(backPanel, BorderLayout.SOUTH);



        // create mid panel
        JPanel midPanel = new JPanel(new GridLayout(1, 3, 20, 20));

        midPanel.setBackground(new Color(245, 248, 250));
        midPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        // midPanel.setBackground(Color.WHITE);
        // create accept and decline buttons
        JButton acceptButton = new JButton("Accept");
        JButton declineButton = new JButton("Decline");
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
        JPanel accept_or_declinePanel = new JPanel( new GridLayout(4, 1, 20, 20));
        accept_or_declinePanel.setBackground(new Color(245, 248, 250));
        accept_or_declinePanel.add(new JLabel());
        accept_or_declinePanel.add(acceptButton);
        accept_or_declinePanel.add(declineButton);
        accept_or_declinePanel.add(new JLabel());


        // Create a data model for the list
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (String friendRequest : sessionmanager.getMainUser().getFriendRequests()) {
            listModel.addElement(friendRequest);
        }

        // Create the JList using the model
        JList<String> scrollableList = new JList<>(listModel);
        scrollableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(scrollableList);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(

                BorderFactory.createEmptyBorder(30, 30, 30, 30),
                BorderFactory.createLineBorder(Color.BLACK)
        ));

        scrollPane.setPreferredSize(new Dimension(150, 100));

        midPanel.add(new JLabel());
        midPanel.add(scrollPane);
        midPanel.add(accept_or_declinePanel);


        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);


        // back button action listener
        backButton.addActionListener(e -> {
            System.out.println("Back button pressed");
            try {
                baseUIController.displayUI(); // triggers presenter → viewmanager switching
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        acceptButton.addActionListener(evt -> {

            state.setAcceptee(sessionmanager.getMainUser());
            try {
                System.out.println(state.getAcceptee().getUsername());
                System.out.println(state.getAccepted_username());

                friendRequestController.execute(
                        state.getAcceptee(),
                        state.getAccepted_username(),
                        true
                );
                listModel.removeElement(state.getAccepted_username());

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        declineButton.addActionListener(evt -> {

            state.setAcceptee(sessionmanager.getMainUser());
            try {
                System.out.println(state.getAcceptee().getUsername());
                System.out.println(state.getAccepted_username());

                friendRequestController.execute(
                        state.getAcceptee(),
                        state.getAccepted_username(),
                        false
                );
                listModel.removeElement(state.getAccepted_username());

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        scrollableList.addListSelectionListener(e -> {
            // System.out.println(scrollableList.getSelectedValue());
            state.setAccepted_username(scrollableList.getSelectedValue());
        });
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        FriendRequestState state = (FriendRequestState) evt.getNewValue();
        if (state.getFriendRequestError() != null) {
            JOptionPane.showMessageDialog(this, state.getFriendRequestError());
        }
        if (state.getSuccess_message() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccess_message());
        }
    }

    public void setFriendRequestController(FriendRequestController friendRequestController) {
        this.friendRequestController = friendRequestController;
    }
}
