package interface_adapter.add_contact;

import interface_adapter.ViewModel;

public class AddContactViewModel extends ViewModel<AddContactState> {
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String USERNAME_LABEL = "Search for users: ";
    public static final String ADD_CONTACT_BUTTON_LABEL = "Add new contact (Send Request)";
    public static final String TITLE_LABEL = "Add Contact";

    public AddContactViewModel() {
        super("add contact");
        setState(new AddContactState());
    }
}
