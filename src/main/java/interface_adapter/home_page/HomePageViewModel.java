package interface_adapter.home_page;

import interface_adapter.ViewModel;

public class HomePageViewModel extends ViewModel<HomePageState> {

    public HomePageViewModel() {
        super("Home Page");
        setState(new HomePageState());
    }
}
