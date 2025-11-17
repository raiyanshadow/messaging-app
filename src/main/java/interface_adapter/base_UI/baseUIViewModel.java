package interface_adapter.base_UI;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class baseUIViewModel extends ViewModel<baseUIState> {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private baseUIState state = new baseUIState();
    private final String viewName = "baseUIView";

    public baseUIViewModel(String viewName) {
        super(viewName);
    }

    public baseUIState getState() { return state; }

    public void setState(baseUIState state) {
        this.state = state;
    }

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public String getViewName() {
        return viewName;
    }
}

