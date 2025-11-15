package interface_adapter.base_UI;

import interface_adapter.ViewModel;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import view.BaseUIView;

import java.util.ArrayList;
import java.util.List;

public class baseUIViewModel extends ViewModel<baseUIState> {
    private String message;
    public final List<baseUIViewModel.ViewModelListener> listeners = new ArrayList<>();

    public baseUIViewModel(String viewName) {
        super(viewName);
    }

    public interface ViewModelListener {
        void onViewModelChange(baseUIViewModel viewModel);
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
        NotifyListeners();
    }


    public void NotifyListeners(){
        for(baseUIViewModel.ViewModelListener listener : listeners){
            listener.onViewModelChange(this);
        }
    }
}
