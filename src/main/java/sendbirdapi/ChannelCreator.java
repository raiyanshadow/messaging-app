package sendbirdapi;

import java.util.Arrays;

import org.openapitools.client.model.CreateAGroupChannelRequest;
import org.openapitools.client.model.SendbirdGroupChannelDetail;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.GroupChannelApi;

/**
 * Facade for a creating a channel using SendBird.
 */
public class ChannelCreator {

    private GroupChannelApi groupChannelApi;

    public ChannelCreator(ApiClient defaultClient) {
        this.groupChannelApi = new GroupChannelApi(defaultClient);
    }

    /**
     * Creates a SendBird channel.
     * @param apiToken token to authenticate with the SendBird token.
     * @param channelName name of the chat channel.
     * @param senderId id of the sending user.
     * @param receiverId id of the receiving user.
     * @return returns the url of the chat channel.
     */
    public String sendbirdChannelCreator(String apiToken, String channelName, Integer senderId, Integer receiverId) {
        final CreateAGroupChannelRequest createChannelRequest = new CreateAGroupChannelRequest();
        createChannelRequest.setName(channelName);
        createChannelRequest.setUserIds(Arrays.asList(Integer.toString(senderId), Integer.toString(receiverId)));
        String ret = "";
        try {
            final SendbirdGroupChannelDetail channel = groupChannelApi.createAGroupChannel().apiToken(apiToken)
                    .createAGroupChannelRequest(createChannelRequest)
                    .execute();
            ret = channel.getChannelUrl();
        }
        catch (ApiException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
