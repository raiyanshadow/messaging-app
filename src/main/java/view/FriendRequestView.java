package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FriendRequestView extends JPanel implements PropertyChangeListener {

    private final String labelName = "Friend Request";
    private final FriendRequestViewModel friendRequestViewModel;
    private FriendRequestController friendRequestController = null;
    private final ViewManagerModel viewManagerModel;

    public FriendRequestView(FriendRequestViewModel friendRequestViewModel,
                             baseUIViewModel baseUIViewModel, ViewManagerModel viewManagerModel) {
        this.friendRequestViewModel = friendRequestViewModel;
        this.viewManagerModel = viewManagerModel;


        // initialize back button
        JButton backButton = new JButton("Back");
        final Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        backButton.setFont(buttonFont);

        JLabel title = new JLabel(FriendRequestViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
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



        // create mid panel
        JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // create accept and decline buttons
        JButton acceptButton = new JButton("Accept");
        JButton declineButton = new JButton("Decline");
        acceptButton.setFont(buttonFont);
        declineButton.setFont(buttonFont);

        // create accept or decline panel
        JPanel accept_or_declinePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        accept_or_declinePanel.setLayout(new BoxLayout(accept_or_declinePanel, BoxLayout.Y_AXIS));
        accept_or_declinePanel.add(acceptButton);
        accept_or_declinePanel.add(Box.createVerticalStrut(10));
        accept_or_declinePanel.add(declineButton);

        // Create a data model for the list
        DefaultListModel<String> listModel = new DefaultListModel<>();

        // Add some dummy data
        listModel.addElement("Friend1");
        listModel.addElement("Friend2");
        listModel.addElement("Friend3");
        listModel.addElement("Friend4");
        listModel.addElement("Friend5");
        listModel.addElement("Friend6");
        listModel.addElement("Friend7");
        listModel.addElement("Friend8");
        listModel.addElement("Friend9");
        listModel.addElement("Friend10");
        listModel.addElement("Friend11");
        listModel.addElement("Friend12");
        listModel.addElement("Friend999");

        // Create the JList using the model
        JList<String> scrollableList = new JList<>(listModel);
        scrollableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(scrollableList);
        // Manually set a preferred size for the scroll pane
        scrollPane.setPreferredSize(new Dimension(150, 100));

        midPanel.add(scrollPane, BorderLayout.CENTER);
        midPanel.add(accept_or_declinePanel, BorderLayout.EAST);


        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);

        // scrollable list listener
        scrollableList.addListSelectionListener(e -> {
            System.out.println(scrollableList.getSelectedValue());
        });

        // back button action listener
        backButton.addActionListener(e -> {
            // heading back to baseUI view
            System.out.println("Back button pressed -> head back to base UI view");
            baseUIViewModel.setState(new baseUIState());
            viewManagerModel.setState(baseUIViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public void setFriendRequestController(FriendRequestController friendRequestController) {
        this.friendRequestController = friendRequestController;
    }
}
