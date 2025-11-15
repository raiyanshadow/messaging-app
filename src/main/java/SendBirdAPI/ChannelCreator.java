package SendBirdAPI;

import entity.User;
import org.openapitools.client.model.SendbirdGroupChannelDetail;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.openapitools.client.model.CreateAGroupChannelRequest;
import org.sendbird.client.api.GroupChannelApi;
import java.util.Arrays;

public class ChannelCreator {

    GroupChannelApi groupChannelApi;
    public String SendbirdChannelCreator(String apiToken, String channelName, User user1, User user2) {
        CreateAGroupChannelRequest createChannelRequest = new CreateAGroupChannelRequest(); // The exact class name might be different
        createChannelRequest.setName(channelName);
        createChannelRequest.setUserIds(Arrays.asList(Integer.toString(user1.getUserID()), Integer.toString(user2.getUserID())));

        try {
            SendbirdGroupChannelDetail channel = groupChannelApi.createAGroupChannel().apiToken(apiToken)
                    .createAGroupChannelRequest(createChannelRequest)
                    .execute();
            return channel.getChannelUrl();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public ChannelCreator(String appId) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-" + appId + ".sendbird.com");
        this.groupChannelApi = new GroupChannelApi(defaultClient);
    }
}