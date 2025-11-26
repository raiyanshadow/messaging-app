package interface_adapter.base_UI;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class baseUIViewModel extends ViewModel<baseUIState> {

    public baseUIViewModel(String viewName) {
        super(viewName);
        setState(new baseUIState());
    }

}

