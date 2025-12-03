package interface_adapter.search_contact;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

/**
 * View model of the search contact use case.
 */
public class SearchContactViewModel extends ViewModel {

    public static final String TITLE_LABEL = "Search Contact";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String ADD_BUTTON_LABEL = "Add";
    public static final String BACK_BUTTON_LABEL = "Back";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private SearchContactState state = new SearchContactState();

    public SearchContactViewModel() {
        super("search contact");
    }

    public void setState(SearchContactState state) {
        this.state = state;
    }

    public SearchContactState getState() {
        return state;
    }

    /**
     * Fires on property change.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Adds a property change listener.
     * @param listener listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
