package sendbirdapi;

import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.MessageApi;

/**
 * Facade for deleting a message using SendBird.
 */
public class MessageDeleter {
    private MessageApi messageApi = new MessageApi();

    public MessageDeleter(String appId) {
        final ApiClient defaultClient = new ApiClient();
        defaultClient.setBasePath("https://api-" + appId + ".sendbird.com");
        this.messageApi = new MessageApi(defaultClient);
    }

    /**
     * Deletes a message using the SendBird API.
     * @param apiToken token to authenticate with.
     * @param messageId id of the message to delete.
     * @param channelUrl url of the channel.
     * @return whether the message deletion was a success or fail.
     */
    public String deleteMessage(String apiToken, Long messageId, String channelUrl) {
        String ret = "fail";
        try {
            messageApi.deleteAMessage("group_channels", channelUrl, messageId)
                    .apiToken(apiToken)
                    .execute();
            ret = "success";
        }
        catch (ApiException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
