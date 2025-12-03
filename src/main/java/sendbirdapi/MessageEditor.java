package sendbirdapi;

import org.openapitools.client.model.UpdateAMessageRequest;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.MessageApi;

/**
 * Facade for editing a message using SendBird.
 */
public class MessageEditor {
    private MessageApi messageApi;

    public MessageEditor(String appId) {
        final ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-" + appId + ".sendbird.com");
        this.messageApi = new MessageApi(defaultClient);
    }

    /**
     * Edits a message using the SendBird API.
     * @param apiToken token to authenticate with.
     * @param newMessage the new message to set to.
     * @param channelUrl the channel url.
     * @param messageId the id of the original message.
     * @return whether the message editing was a success or fail.
     */
    public String editMessage(String apiToken, String newMessage, String channelUrl, Long messageId) {
        final UpdateAMessageRequest request = new UpdateAMessageRequest();
        String ret = "fail";
        request.setMessage(newMessage);
        request.setUrl(channelUrl);
        request.setMessageType(UpdateAMessageRequest.MessageTypeEnum.MESG);
        try {
            messageApi.updateAMessage("group_channels",
                    channelUrl, messageId)
                    .apiToken(apiToken)
                    .updateAMessageRequest(request)
                    .execute();
            ret = "success";
        }
        catch (ApiException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
