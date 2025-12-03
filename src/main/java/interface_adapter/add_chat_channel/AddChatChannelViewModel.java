package interface_adapter.add_chat_channel;

import java.util.ArrayList;
import java.util.List;

import interface_adapter.ViewModel;

/**
 * View model for the add chat channel use case.
 */
public class AddChatChannelViewModel extends ViewModel<AddChatChannelState> {
    private String message;
    private String error;
    private final List<ViewModelListener> listeners = new ArrayList<>();

    public AddChatChannelViewModel(String viewName) {
        super(viewName);
        // Initialize the state in the PARENT class
        super.setState(new AddChatChannelState());
    }

    @Override
    public void setState(AddChatChannelState state) {
        super.setState(state);
        firePropertyChange();
    }

    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the current view model and notifies the listener.
     * @param message the new message to set to.
     */
    public void setMessage(String message) {
        this.message = message;
        notifyListeners();
    }

    public String getError() {
        return error;
    }

    /**
     * Sets the error for the view model and notifies its listeners.
     * @param error the error to set the view model to.
     */
    public void setError(String error) {
        this.error = error;
        notifyListeners();
    }

    /**
     * Notifies the listener in this observer design.
     */
    public void notifyListeners() {
        for (ViewModelListener listener : listeners) {
            listener.onViewModelChange(this);
        }
    }

    /**
     * An inner interface for a view model listener for the add chat channel use case.
     */
    public interface ViewModelListener {
        /**
         * Fired when view model changes.
         * @param viewModel the new view model it has changed to.
         */
        void onViewModelChange(AddChatChannelViewModel viewModel);
    }
}
