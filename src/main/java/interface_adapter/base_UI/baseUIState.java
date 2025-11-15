package interface_adapter.base_UI;

import java.util.ArrayList;
import java.util.List;

public class baseUIState {

    private String errorMessage = null;
    public baseUIState() {
    };

    public void setErrorMessage(String s) {
        this.errorMessage = s;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
}