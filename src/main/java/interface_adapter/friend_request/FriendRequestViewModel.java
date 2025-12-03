package interface_adapter.friend_request;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

/**
 * View model of the friend request use case.
 */
public class FriendRequestViewModel extends ViewModel<FriendRequestState> {
    public static final String TITLE_LABEL = "friend Request";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private FriendRequestState state = new FriendRequestState();

    public FriendRequestViewModel() {
        super("friend request");
    }

    public FriendRequestState getState() {
        return state;
    }

    public void setState(FriendRequestState state) {
        this.state = state;
    }

    /**
     * Fires a property change.
     */
    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Adds a property change listener to this view model.
     * @param listener the listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public String getViewName() {
        return TITLE_LABEL;
    }
}
