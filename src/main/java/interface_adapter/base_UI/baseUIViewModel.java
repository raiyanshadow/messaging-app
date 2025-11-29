package interface_adapter.base_UI;

import interface_adapter.ViewModel;

public class baseUIViewModel extends ViewModel<baseUIState> {

    public baseUIViewModel(String viewName) {
        super(viewName);
        setState(new baseUIState());
    }

    @Override
    public void setState(baseUIState state) {
        super.setState(state);
    }
}
