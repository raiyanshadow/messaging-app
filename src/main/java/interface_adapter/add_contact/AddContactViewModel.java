package interface_adapter.add_contact;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

/**
 * View model for the add contact use case.
 */
public class AddContactViewModel extends ViewModel<AddContactState> {
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String USERNAME_LABEL = "Search for users: ";
    public static final String ADD_CONTACT_BUTTON_LABEL = "Add new contact (Send Request)";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private AddContactState state = new AddContactState();
    private final String viewName = "Add Contact";

    public AddContactViewModel() {
        super("add contact");
        setState(new AddContactState());
    }

    public AddContactState getState() {
        return state;
    }

    public void setState(AddContactState state) {
        this.state = state;
    }

    /**
     * Fires a property change once called.
     */
    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Adds a listener for property changing.
     * @param listener the listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public String getViewName() {
        return viewName;
    }
}
