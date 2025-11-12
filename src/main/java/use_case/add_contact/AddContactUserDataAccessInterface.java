package use_case.add_contact;

import entity.User;

public interface AddContactUserDataAccessInterface {

    /**
     *
     * @param user the user who we want to add to our contacts
     * @return true if the given user exists; meaning it is possible to add to contacts
     */
    boolean exists(User user);

    /**
     * user1 send a contact request to user2
     * @param user1 the user who wants to add a contact (sends out add contact request)
     * @param user2 the user to be added (receives add contact request)
     */
    void addContact(User user1, User user2);

}
