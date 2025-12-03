package interface_adapter.base_UI;

import interface_adapter.ViewModel;

/**
 * View model for the base UI use case.
 */
public class BaseUiViewModel extends ViewModel<BaseUiState> {

    public BaseUiViewModel(String viewName) {
        super(viewName);
        setState(new BaseUiState());
    }

    @Override
    public void setState(BaseUiState state) {
        super.setState(state);
    }
}
