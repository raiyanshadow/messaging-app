package use_case.search_contact;

// to search contacts a user needs to input a username which is a string
public class SearchContactInputData {

    private final String username;

    public SearchContactInputData(String username){
        this.username = username;
    }

    String getUsername() {
        return username;
    }
}
