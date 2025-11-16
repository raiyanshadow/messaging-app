package interface_adapter.add_chat_channel;

import interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddChatChannelViewModel extends ViewModel<AddChatChannelState> {
    private String message;
    private String error;
    private Boolean HasError;

    public final List<ViewModelListener> listeners = new ArrayList<>();

    public boolean getHasError() {
        return HasError;
    }

    public interface ViewModelListener {
        void onViewModelChange(AddChatChannelViewModel viewModel);
    }

    public AddChatChannelViewModel(String message, String error) {
        super("add_chat_channel");
        this.message = message;
        this.error = error;
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
