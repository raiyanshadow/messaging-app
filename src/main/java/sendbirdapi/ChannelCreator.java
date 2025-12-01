package sendbirdapi;

import org.openapitools.client.model.SendbirdGroupChannelDetail;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.openapitools.client.model.CreateAGroupChannelRequest;
import org.sendbird.client.api.GroupChannelApi;
import java.util.Arrays;

public class ChannelCreator {

    private GroupChannelApi groupChannelApi;
    public String SendbirdChannelCreator(String apiToken, String channelName, Integer senderId, Integer receiverId) {
        CreateAGroupChannelRequest createChannelRequest = new CreateAGroupChannelRequest(); // The exact class name might be different
        createChannelRequest.setName(channelName);
        createChannelRequest.setUserIds(Arrays.asList(Integer.toString(senderId), Integer.toString(receiverId)));

        try {
            SendbirdGroupChannelDetail channel = groupChannelApi.createAGroupChannel().apiToken(apiToken)
                    .createAGroupChannelRequest(createChannelRequest)
                    .execute();
            return channel.getChannelUrl();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    protected ChannelCreator() {
    }

    public ChannelCreator(ApiClient defaultClient) {
        this.groupChannelApi = new GroupChannelApi(defaultClient);
    }
}