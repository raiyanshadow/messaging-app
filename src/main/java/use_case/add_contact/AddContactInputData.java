package use_case.add_contact;

import entity.User;

public class AddContactInputData {
    // user1 is the user who sends out add contact request
    private final User user1;
    // user2 is the user who receives add contact request
    private final User user2;

    public AddContactInputData(User user1, User user2) {
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
