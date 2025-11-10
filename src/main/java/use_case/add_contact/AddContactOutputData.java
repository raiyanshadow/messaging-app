package use_case.add_contact;

public class AddContactOutputData {
    private int userID;

    public AddContactOutputData(int userID) {
        this.userID = userID;
    }

    int getUserID() {
        return userID;
    }
}
