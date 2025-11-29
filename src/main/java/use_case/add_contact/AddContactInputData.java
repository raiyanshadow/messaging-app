package use_case.add_contact;


public class AddContactInputData {
    // the username that the user inputs -> the user who will receive the friend request
    private final String receiverUsername;

    public AddContactInputData(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    String getReceiverUsername() {
        return receiverUsername;
    }

}
