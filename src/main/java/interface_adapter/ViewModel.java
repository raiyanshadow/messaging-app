package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * View model.
 * @param <A> The generic view model type the base view model adheres to.
 */
public class ViewModel<A> {
    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private A state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    public A getState() {
        return this.state;
    }

    public void setState(A state) {
        this.state = state;
    }

    /**
     * Fires a property change.
     */
    public void firePropertyChange() {
        this.support.firePropertyChange("state", null, this.state);
    }

    /**
     * Fires a property change given the name of the property.
     * @param propertyName name of the property.
     */
    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    /**
     * Adds a property change listener.
     * @param listener listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     * @param listener listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
