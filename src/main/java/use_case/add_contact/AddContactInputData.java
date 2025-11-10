package use_case.add_contact;

public class AddContactInputData {

    private final int userId;

    public AddContactInputData(int userId) {
        this.userId = userId;
    }

    int getUserId() {
        return userId;
    }
}
