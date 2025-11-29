package interface_adapter.search_contact;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SearchContactViewModel extends ViewModel {

    public static final String TITLE_LABEL = "Search Contact";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String ADD_BUTTON_LABEL = "Add";
    public static final String BACK_BUTTON_LABEL = "Back";

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

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
