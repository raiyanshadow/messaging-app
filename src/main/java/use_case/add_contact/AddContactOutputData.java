package use_case.add_contact;

/**
 * Output data of the add contact use case.
 */
public class AddContactOutputData {
    // user with the username receiverUsername is the user who receives add contact request
    private final String receiverUsername;

    public AddContactOutputData(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

}
