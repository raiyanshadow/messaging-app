package data_access;

import entity.Contact;
import entity.User;
import java.util.List;

public interface ContactDataAccessObject {
    public void updateUserContacts(User user, List<Contact> contacts);
}
