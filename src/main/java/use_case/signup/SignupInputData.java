package use_case.signup;

public class SignupInputData {
    private final String username;
    private final String password;
    private final String repeatPassword;
    private final String preferredLanguage;

    public SignupInputData(String username, String password, String repeatPassword, String preferredLanguage) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.preferredLanguage = preferredLanguage;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRepeatPassword() { return repeatPassword; }
    public String getPreferredLanguage() { return preferredLanguage; }
}
