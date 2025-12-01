package sendbirdapi;

import org.openapitools.client.model.CreateAUserRequest;
import org.openapitools.client.model.SendbirdUser;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.UserApi;

public class SendbirdUserCreator {

    private final UserApi userApi;

    public SendbirdUserCreator(String appId) {
        ApiClient client = Configuration.getDefaultApiClient();
        client.setBasePath("https://api-" + appId + ".sendbird.com");
        this.userApi = new UserApi(client);
    }

    public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
        CreateAUserRequest req = new CreateAUserRequest();
        req.setUserId(Integer.toString(userId));
        req.setNickname(nickname);

        try {
            return userApi.createAUser()
                    .apiToken(apiToken)
                    .createAUserRequest(req)
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Sendbird user", e);
        }
    }
}
