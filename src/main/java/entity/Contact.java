package entity;

import java.util.Objects;

public class Contact {
    private User user;
    private User contact;

    public Contact(User user, User contact) {

        if (user == null || contact == null) {
            throw new IllegalArgumentException("User and contact cannot be null");
        }
        if (user.getUserID() == contact.getUserID()) {
            throw new IllegalArgumentException("User cannot add themselves as contact");
        }
        else {
            this.user = user;
            this.contact = contact;
        }
    }

    public User getUser() {

        return user;
    }

    public User getContact() {

        return contact;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }

        final Contact that = (Contact) o;

        return user.getUserID() == that.user.getUserID()
                && contact.getUserID() == that.contact.getUserID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getUserID(), contact.getUserID());
    }
}
