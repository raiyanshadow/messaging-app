package interface_adapter.friend_request;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

public class FriendRequestViewModel extends ViewModel<FriendRequestState> {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private FriendRequestState state = new FriendRequestState();

    public static final String TITLE_LABEL = "friend Request";
    public FriendRequestViewModel() {
        super("friend request");
    }

    public FriendRequestState getState() {
        return state;
    }

    public void setState(FriendRequestState state) {
        this.state = state;
    }

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public String getViewName() {
        return TITLE_LABEL;
    }
}
