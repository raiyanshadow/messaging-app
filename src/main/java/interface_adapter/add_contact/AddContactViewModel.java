package interface_adapter.add_contact;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AddContactViewModel extends ViewModel<AddContactState> {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private AddContactState state = new AddContactState();
    private final String viewName = "add contact";

    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String USERNAME_LABEL = "Search for users: ";
    public static final String ADD_CONTACT_BUTTON_LABEL = "Add new contact (Send Request)";
    public static final String TITLE_LABEL = "Add Contact";

    public AddContactViewModel() {
        super("add contact");
        setState(new AddContactState());
    }
    public AddContactState getState() { return state; }

    public void setState(AddContactState state) {
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
