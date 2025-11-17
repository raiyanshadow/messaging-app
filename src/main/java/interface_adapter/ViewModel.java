package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewModel<A> {
    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private A state;
