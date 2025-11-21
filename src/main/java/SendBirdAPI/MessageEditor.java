package SendBirdAPI;

import org.openapitools.client.model.UpdateAMessageRequest;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.MessageApi;

public class MessageEditor {
    private MessageApi messageApi;

    public MessageEditor(String appId) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-" + appId + ".sendbird.com");
        this.messageApi = new MessageApi(defaultClient);
    }

    public String editMessage(String apiToken, String newMessage, String channelUrl, Long messageId) {
        UpdateAMessageRequest request = new UpdateAMessageRequest();
        request.setMessage(newMessage);
        request.setUrl(channelUrl);
        request.setMessageType(UpdateAMessageRequest.MessageTypeEnum.MESG);
        try {
            messageApi.updateAMessage("group_channels",
                    channelUrl, messageId)
                    .apiToken(apiToken)
                    .updateAMessageRequest(request)
                    .execute();
            return "success";
        } catch (ApiException e) {
            return "fail";
        }
    }
}
