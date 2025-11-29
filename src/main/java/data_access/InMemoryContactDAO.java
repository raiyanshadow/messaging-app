package data_access;

import entity.Contact;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryContactDAO implements ContactDataAccessObject {

    // to simulate the contact table in the database
    // can't just use the Contact entity because it does not contain the attribute isFriendRequest
    // which is needed for the updateFriendRequests method

    public static class DummyContact {
        private final User user;
        private final User contact;
        private final Boolean isFriendRequest;

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

    }


    private final List<DummyContact> contacts = new ArrayList<>();

    public void addDummyContact(DummyContact dummyContact) {
        contacts.add(dummyContact);
    }

    @Override
    public void updateUserContacts(User user, List<Contact> userContacts) {
        for (DummyContact contact : contacts) {
            // DummyContact has the format (user, contact, isFriendRequest)
            // so if there is a contact in the DummyContacts list that has current user in either
            // the user col or contact col, and it is not a friend request
            // add the contact to the user's list of contacts
            if (contact.getContact().getUserID() == user.getUserID() && !contact.getIsFriendRequest()) {
                Contact tempContact = new Contact(user, contact.getUser());
                userContacts.add(tempContact);
            }
            else if (contact.getUser().getUserID() == user.getUserID() && !contact.getIsFriendRequest()) {
                Contact tempContact = new Contact(user, contact.getContact());
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
}
