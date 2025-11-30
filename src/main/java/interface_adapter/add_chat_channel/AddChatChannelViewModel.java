package interface_adapter.add_chat_channel;

import interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddChatChannelViewModel extends ViewModel<AddChatChannelState> {
    private String message;
    private String error;
    private Boolean HasError;
    public final List<ViewModelListener> listeners = new ArrayList<>();

    public AddChatChannelViewModel(String viewName) {
        super(viewName);
        // Initialize the state in the PARENT class
        super.setState(new AddChatChannelState());
    }

    @Override
    public void setState(AddChatChannelState state) {
        super.setState(state); // Update the parent's state variable
        firePropertyChange();  // Trigger the event using the parent's state
    }

    public boolean getHasError() {
        return HasError;
    }

    public interface ViewModelListener {
        void onViewModelChange(AddChatChannelViewModel viewModel);
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
        NotifyListeners();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        NotifyListeners();
    }

    public void NotifyListeners(){
        for(ViewModelListener listener : listeners){
            listener.onViewModelChange(this);
        }
    }

}
