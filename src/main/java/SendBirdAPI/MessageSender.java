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
        System.out.println("sender: " + sender.getUsername());
        System.out.println("senderID: " + sender.getUserID());
        System.out.println("channelUrl: " + channelUrl);
        System.out.println("apiToken: " + apiToken);
        SendAMessageRequest request = new SendAMessageRequest();
        request.setMessage(message);
        request.setUserId(Integer.toString(sender.getUserID()));
        request.setMessageType(SendAMessageRequest.MessageTypeEnum.MESG);
        String channelType = "group_channels";
        try {
            SendbirdMessageResponse response = messageApi.sendAMessage(channelType, channelUrl)
                    .apiToken(apiToken)
                    .sendAMessageRequest(request)
                    .execute();
        } catch (ApiException e) {
            System.out.println("Status code: " + e.getCode());
            System.out.println("Body: " + e.getResponseBody());
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }
}
