package SendBirdAPI;

import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.OpenChannelApi;
import org.sendbird.client.model.OcCreateChannelData;
import org.sendbird.client.model.OpenChannel;

public class ChannelCreator {

    private OpenChannelApi openChannelApi;

    public void SendbirdChannelCreator(String appId, String apiToken) {
        ApiClient client = Configuration.getDefaultApiClient();
        client.setBasePath("https://api-" + appId + ".sendbird.com/v3");
        client.addDefaultHeader("Api-Token", apiToken);
        this.openChannelApi = new OpenChannelApi(client);
    }

    public ChannelCreator(OpenChannelApi openChannelApi) {
        this.openChannelApi = openChannelApi;
    }

    public String createChannel(String channelName, String channelUrl) throws ApiException {
        OcCreateChannelData data = new OcCreateChannelData();
        data.setName(channelName);
        data.setChannelUrl(channelUrl);

        OpenChannel channel = openChannelApi.ocCreateChannel(data);
        return channel.getChannelUrl();
    }
}