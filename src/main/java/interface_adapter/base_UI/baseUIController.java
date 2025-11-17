package interface_adapter.base_UI;

import use_case.baseUI.BaseUIInputBoundary;
import use_case.baseUI.BaseUIInteractor;
import use_case.baseUI.BaseUIInputData;

import java.sql.SQLException;

public class baseUIController {
    private final BaseUIInputBoundary BaseUIInteractor;

    public baseUIController(BaseUIInteractor baseUIInteractor) {
        this.BaseUIInteractor = baseUIInteractor;
    }

    public void displayUI() throws SQLException {

        BaseUIInputData request = new BaseUIInputData();
        BaseUIInteractor.GetUserChats(request);
    }

}
