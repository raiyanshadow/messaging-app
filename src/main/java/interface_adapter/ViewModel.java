package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewModel<A> {
    private final String viewName;
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);
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

    public void firePropertyChange() {
        this.support.firePropertyChange("state", null, this.state);
    }

    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

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

    public void firePropertyChange() {
        this.support.firePropertyChange("state", null, this.state);
    }

    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
