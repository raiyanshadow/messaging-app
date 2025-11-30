package SendBirdAPI;

import org.openapitools.client.model.SendAMessageRequest;
import org.openapitools.client.model.SendbirdMessageResponse;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.MessageApi;
import use_case.send_message.SendMessageAPIAccessInterface;

public class MessageSender implements SendMessageAPIAccessInterface {
    private final MessageApi messageApi;

    public MessageSender(ApiClient defaultClient) {
        this.messageApi = new MessageApi(defaultClient);
    }

    public Long sendMessage(String message, String apiToken, String channelUrl, Integer senderId) {
        SendAMessageRequest request = new SendAMessageRequest();
        request.setMessage(message);
        request.setUserId(Integer.toString(senderId));
        request.setMessageType(SendAMessageRequest.MessageTypeEnum.MESG);
        String channelType = "group_channels";
        try {
            SendbirdMessageResponse response = messageApi.sendAMessage(channelType, channelUrl)
                    .apiToken(apiToken)
                    .sendAMessageRequest(request)
                    .execute();
            return response.getMessageId();
        } catch (ApiException e) {
            return null;
        }
    }
}
