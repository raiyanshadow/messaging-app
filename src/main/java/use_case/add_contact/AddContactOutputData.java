package use_case.add_contact;

import entity.User;


public class AddContactOutputData {
    // sender is the user who sends out add contact request
    private final User sender;
    // user with the username receiver_username is the user who receives add contact request
    private final String receiver_username;

    public AddContactOutputData(User sender, String receiver_username) {
        this.sender = sender;
        this.receiver_username = receiver_username;
    }

    public String getReceiverUsername() {
        return receiver_username;
    }

}
