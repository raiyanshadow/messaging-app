package entity;

import java.util.Objects;

/**
 * Relationship between two User entities is identified as a Contact entity.
 */
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
        boolean ret = false;

        final Contact con;
        if (this == o) {
            ret = true;
        }
        else if (o instanceof Contact) {
            con = (Contact) o;
            ret = user.getUserID() == con.user.getUserID()
                    && contact.getUserID() == con.contact.getUserID();
        }

        return ret;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getUserID(), contact.getUserID());
    }
}
