package use_case.add_contact;

import entity.User;

// after pressing button to add contact show that <user> has been added as a contact
public class AddContactOutputData {
    // user1 is the user who send out add contact request
    private final User user1;
    // user2 is the user who receives add contact request
    private final User user2;

    public AddContactOutputData(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    User getUser1() {
        return user1;
    }
    User getUser2() {
        return user2;
    }

}
