package data.access;

import java.util.ArrayList;
import java.util.List;

import entity.Contact;
import entity.User;
import use_case.add_contact.AddContactContactDataAccessInterface;
import use_case.baseUI.BaseUiContactDataAccessInterface;
import use_case.friend_request.FriendRequestContactDataAccessInterface;

/**
 * An in memory data access object to represent the contact table in our database.
 */
public class InMemoryContactDao implements ContactDataAccessObject, FriendRequestContactDataAccessInterface,
        AddContactContactDataAccessInterface, BaseUiContactDataAccessInterface {

    // to simulate the contact table in the database
    // can't just use the Contact entity because it does not contain the attribute isFriendRequest
    // which is needed for the updateFriendRequests method

    private final List<DummyContact> contacts = new ArrayList<>();

    /**
     * Add a mock contact to our mock database.
     * @param dummyContact The mock contact to add,
     */
    public void addDummyContact(DummyContact dummyContact) {
        contacts.add(dummyContact);
    }

    public List<DummyContact> getAllContacts() {
        return contacts;
    }

    @Override
    public void updateUserContacts(User user, List<Contact> userContacts) {
        for (DummyContact contact : contacts) {
            // DummyContact has the format (user, contact, isFriendRequest)
            // so if there is a contact in the DummyContacts list that has current user in either
            // the user col or contact col, and it is not a friend request
            // add the contact to the user's list of contacts
            if (contact.getContact().getUserID() == user.getUserID() && !contact.getIsFriendRequest()) {
                final Contact tempContact = new Contact(user, contact.getUser());
                userContacts.add(tempContact);
            }
            else if (contact.getUser().getUserID() == user.getUserID() && !contact.getIsFriendRequest()) {
                final Contact tempContact = new Contact(user, contact.getContact());
                userContacts.add(tempContact);
            }
        }
    }

    @Override
    public void updateUserFriendRequests(User user, List<String> friendRequests) {
        for (DummyContact contact : contacts) {
            // DummyContact has the format (user, contact, isFriendRequest)
            // so the user only has friend requests if the user is in the contact col
            if (contact.getContact().getUserID() == user.getUserID() && contact.getIsFriendRequest()) {
                friendRequests.add(contact.getUser().getUsername());
            }
        }
    }

    @Override
    public void acceptRequest(User accepter, String acceptedUsername) {
        // format of DummyContact is (user, contact, isFriendRequest)
        for (DummyContact contact : contacts) {
            if (contact.getUser().getUsername().equals(acceptedUsername) && contact.getContact().getUserID()
                    == accepter.getUserID() && contact.getIsFriendRequest()) {
                contact.setIsFriendRequest(false);
            }
        }
    }

    @Override
    public void deleteRequest(User decliner, String acceptedUsername) {
        contacts.removeIf(contact -> {
            return contact.getUser().getUsername().equals(acceptedUsername)
                    && contact.getContact().getUserID() == decliner.getUserID() && contact.getIsFriendRequest();
        });
    }

    @Override
    public List<Contact> getContacts(User user) {
        updateUserContacts(user, user.getContacts());
        return user.getContacts();
    }

    /**
     * Mock contact data access object.
     */
    public static class DummyContact {
        private final User user;
        private final User contact;
        private Boolean isFriendRequest;

        public DummyContact(User user, User contact, Boolean isFriendRequest) {
            this.user = user;
            this.contact = contact;
            this.isFriendRequest = isFriendRequest;
        }

        public User getUser() {
            return user;
        }

        public User getContact() {
            return contact;
        }

        public Boolean getIsFriendRequest() {
            return isFriendRequest;
        }

        public void setIsFriendRequest(Boolean isFriendRequest) {
            this.isFriendRequest = isFriendRequest;
        }

    }
}
