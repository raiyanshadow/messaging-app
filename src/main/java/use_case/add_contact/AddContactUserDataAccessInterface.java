package use_case.add_contact;

import entity.User;

public interface AddContactUserDataAccessInterface {

    // @return true if the given user exists so we can add the user into contacts; false if the user does not exist
    boolean exists(User user);

    // @param user the user to add into contacts
    void addContact(User user);

}
