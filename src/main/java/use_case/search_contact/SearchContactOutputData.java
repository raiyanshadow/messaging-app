package use_case.search_contact;

import entity.User;

import java.util.List;

// output data needed is list of users that contain a substring of the input data
public class SearchContactOutputData {

    private final List<User> users;

    public SearchContactOutputData(List<User> users){
        this.users = users;
    }

    List<User> getUsers(){
        return users;
    }

}
