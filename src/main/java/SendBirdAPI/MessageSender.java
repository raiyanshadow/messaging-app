package SendBirdAPI;

import entity.User;
import org.openapitools.client.model.SendAMessageRequest;
import org.openapitools.client.model.SendbirdMessageResponse;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.MessageApi;

public class MessageSender {
    MessageApi messageApi;

    public MessageSender(String appId) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-" + appId + ".sendbird.com");
        this.messageApi = new MessageApi(defaultClient);
    }

    public String sendMessage(String message, String apiToken, String channelUrl, User sender) {
        SendAMessageRequest request = new SendAMessageRequest();
        request.setMessage(message);
        request.setUserId(Integer.toString(sender.getUserID()));
        String channelType = "group_channels";
        try {
            SendbirdMessageResponse response = messageApi.sendAMessage(channelType, channelUrl)
                    .apiToken(apiToken)
                    .sendAMessageRequest(request)
                    .execute();

        } catch (ApiException e) {
            return "fail";
        }
        return "success";
    }
}
