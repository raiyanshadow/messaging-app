package sendbirdapi;

import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.MessageApi;

public class MessageDeleter {
    private MessageApi messageApi = new MessageApi();

    public MessageDeleter(String appId) {
        ApiClient defaultClient = new ApiClient();
        defaultClient.setBasePath("https://api-" + appId + ".sendbird.com");
        this.messageApi = new MessageApi(defaultClient);
    }

    public String deleteMessage(String apiToken, Long messageId, String channelUrl) {
        try {
            Object result = messageApi.deleteAMessage("group_channels", channelUrl, messageId)
                    .apiToken(apiToken)
                    .execute();
            return "success";
        } catch (ApiException e) {
            return "fail";
        }
    }
}
