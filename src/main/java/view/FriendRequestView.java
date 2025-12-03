package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import interface_adapter.base_UI.BaseUiController;
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestState;
import interface_adapter.friend_request.FriendRequestViewModel;
import session.Session;

/**
 * Friend Request View.
 */
public class FriendRequestView extends JPanel implements PropertyChangeListener {

    private FriendRequestController friendRequestController;
    private final Session sessionManager;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public FriendRequestView(FriendRequestViewModel friendRequestViewModel, Session sessionManager,
                             BaseUiController baseUiController) {
        this.sessionManager = sessionManager;

        friendRequestViewModel.addPropertyChangeListener(this);
        final FriendRequestState state = friendRequestViewModel.getState();

        // initialize back button
        final JButton backButton = new JButton("Back");
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        final Color backButtonBackgroundColour = new Color(96, 179, 120);
        final int backButtonThickness = 1;
        final boolean backButtonIsRounded = true;
        backButton.setBackground(backButtonBackgroundColour);
        backButton.setForeground(Color.WHITE);
        final Dimension backButtonDimension = new Dimension(140, 42);
        backButton.setPreferredSize(backButtonDimension);
        backButton.setBorder(BorderFactory.createLineBorder(backButtonBackgroundColour, backButtonThickness,
                backButtonIsRounded));

        // create title panel
        final JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        final String labelName = "Friend Requests";
        final JLabel title = new JLabel(labelName, SwingConstants.CENTER);
        final int titleFontSize = 20;
        title.setFont(new Font("SansSerif", Font.BOLD, titleFontSize));
        titlePanel.add(title, BorderLayout.CENTER);

        // create back button panel
        final int backPanelHgap = 20;
        final int backPanelVgap = 10;
        final JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, backPanelHgap, backPanelVgap));
        backPanel.add(backButton);
        backPanel.setBackground(Color.WHITE);

        // create top panel (add in title panel and back button panel)
        final JPanel topPanel = new JPanel(new BorderLayout());
        final Dimension titlePanelDimension = new Dimension(300, 40);
        titlePanel.setPreferredSize(titlePanelDimension);
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(backPanel, BorderLayout.SOUTH);

        // create mid panel
        final JPanel midPanel = new JPanel(new GridLayout(1, 3, 20, 20));

        final Color midPanelBackgroundColour = new Color(245, 248, 250);
        midPanel.setBackground(midPanelBackgroundColour);
        final Color midPanelColour = new Color(220, 220, 220);
        final int midPanelTop = 30;
        final int midPanelLeft = 40;
        final int midPanelBottom = 30;
        final int midPanelRight = 40;
        midPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(midPanelColour),
                BorderFactory.createEmptyBorder(midPanelTop, midPanelLeft, midPanelBottom, midPanelRight)
        ));

        final JButton acceptButton = new JButton("Accept");
        final JButton declineButton = new JButton("Decline");
        acceptButton.setFont(buttonFont);
        final Color acceptButtonBackgroundColour = new Color(70, 130, 180);
        final Dimension acceptButtonDimension = new Dimension(140, 42);
        final int acceptButtonThickness = 1;
        final boolean acceptButtonIsRounded = true;
        acceptButton.setBackground(acceptButtonBackgroundColour);
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setPreferredSize(acceptButtonDimension);
        acceptButton.setBorder(BorderFactory.createLineBorder(acceptButtonBackgroundColour, acceptButtonThickness,
                acceptButtonIsRounded));

        final Color declineButtonColour = new Color(255, 165, 0);
        final Dimension declineButtonDimension = new Dimension(140, 42);
        final int declineButtonThickness = 1;
        final boolean declineButtonIsRounded = true;
        declineButton.setFont(buttonFont);
        declineButton.setBackground(declineButtonColour);
        declineButton.setForeground(Color.WHITE);
        declineButton.setPreferredSize(declineButtonDimension);
        declineButton.setBorder(BorderFactory.createLineBorder(declineButtonColour, declineButtonThickness,
                declineButtonIsRounded));

        // create accept or decline panel
        final Color acceptOrDeclinePanelBackgroundColour = new Color(245, 248, 250);
        final JPanel acceptOrDeclinePanel = new JPanel(new GridLayout(4, 1, 20, 20));
        acceptOrDeclinePanel.setBackground(acceptOrDeclinePanelBackgroundColour);
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
        final int scrollPaneTop = 30;
        final int scrollPaneLeft = 30;
        final int scrollPaneBottom = 30;
        final int scrollPaneRight = 30;
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(scrollPaneTop, scrollPaneLeft, scrollPaneBottom, scrollPaneRight),
                BorderFactory.createLineBorder(Color.BLACK)
        ));

        final Dimension scrollPaneDimension = new Dimension(150, 100);
        scrollPane.setPreferredSize(scrollPaneDimension);

        midPanel.add(new JLabel());
        midPanel.add(scrollPane);
        midPanel.add(acceptOrDeclinePanel);

        // add all the panels
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);

        // back button action listener
        backButton.addActionListener(evt -> {
            System.out.println("Back button pressed");
            try {
                baseUiController.displayUi();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        acceptButton.addActionListener(evt -> {
            try {
                final String selectedName = state.getAcceptedUsername();

                friendRequestController.execute(
                        state.getAcceptedUsername(),
                        true
                );
                listModel.removeElement(selectedName);
                sessionManager.getMainUser().getFriendRequests().remove(selectedName);
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        declineButton.addActionListener(evt -> {
            try {
                final String selectedName = state.getAcceptedUsername();

                friendRequestController.execute(
                        state.getAcceptedUsername(),
                        false
                );
                listModel.removeElement(selectedName);
                sessionManager.getMainUser().getFriendRequests().remove(selectedName);
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        scrollableList.addListSelectionListener(evt -> {
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
