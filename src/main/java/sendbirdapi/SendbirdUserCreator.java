package sendbirdapi;

import org.openapitools.client.model.CreateAUserRequest;
import org.openapitools.client.model.SendbirdUser;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.UserApi;

/**
 * Facade for creating a user using SendBird.
 */
public class SendbirdUserCreator {

    private final UserApi userApi;

    public SendbirdUserCreator(String appId) {
        final ApiClient client = Configuration.getDefaultApiClient();
        client.setBasePath("https://api-" + appId + ".sendbird.com");
        this.userApi = new UserApi(client);
    }

    /**
     * Creates a user using the SendBird API.
     * @param apiToken the token to authenticate with.
     * @param userId the id of the user to set to.
     * @param nickname the username.
     * @return the SendbirdUser object provided by the SendBird API Java SDK.
     * @throws RuntimeException if the user could not be added.
     */
    public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
        final CreateAUserRequest req = new CreateAUserRequest();
        SendbirdUser ret = null;
        req.setUserId(Integer.toString(userId));
        req.setNickname(nickname);

        try {
            ret = userApi.createAUser()
                    .apiToken(apiToken)
                    .createAUserRequest(req)
                    .execute();
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to create Sendbird user", ex);
        }
        return ret;
    }
}
