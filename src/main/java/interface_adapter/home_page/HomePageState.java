package interface_adapter.home_page;

import entity.User;

public class HomePageState {
    private boolean isLoggedIn;
    private User user;

    public HomePageState() {
        this.isLoggedIn = false;
        this.user = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setisLoggedIn(boolean status) {
        this.isLoggedIn = status;
        if (!status) {
            this.user = null;
        }
    }
}
