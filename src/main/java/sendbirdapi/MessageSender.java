package sendbirdapi;

import org.openapitools.client.model.SendAMessageRequest;
import org.openapitools.client.model.SendbirdMessageResponse;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.MessageApi;

import use_case.send_message.SendMessageApiAccessInterface;

/**
 * Facade for sending a message using SendBird.
 */
public class MessageSender implements SendMessageApiAccessInterface {
    private final MessageApi messageApi;

    public MessageSender(ApiClient defaultClient) {
        this.messageApi = new MessageApi(defaultClient);
    }

    /**
     * Sends a message using the SendBird API.
     * @param message text message to send
     * @param apiToken required for API authentication
     * @param channelUrl tells the api which channel to send the message in
     * @param senderId tells the api from whom the message is being sent by
     * @return the automatically generated message id from SendBird.
     */
    public Long sendMessage(String message, String apiToken, String channelUrl, Integer senderId) {
        final SendAMessageRequest request = new SendAMessageRequest();
        Long ret = null;
        request.setMessage(message);
        request.setUserId(Integer.toString(senderId));
        request.setMessageType(SendAMessageRequest.MessageTypeEnum.MESG);
        final String channelType = "group_channels";
        try {
            final SendbirdMessageResponse response = messageApi.sendAMessage(channelType, channelUrl)
                    .apiToken(apiToken)
                    .sendAMessageRequest(request)
                    .execute();
            ret = response.getMessageId();
        }
        catch (ApiException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
