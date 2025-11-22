package use_case.add_contact;

import entity.User;


public class AddContactOutputData {
    // user1 is the user who sends out add contact request
    private final User sender;
    // user2 is the user who receives add contact request
    private final String receiver_username;

    public AddContactOutputData(User sender, String receiver_username) {
        this.sender = sender;
        this.receiver_username = receiver_username;
    }

    User getSender() {
        return sender;
    }
    String getReceiverUsername() {
        return receiver_username;
    }

}
