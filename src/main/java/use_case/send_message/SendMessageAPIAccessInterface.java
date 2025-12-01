package use_case.send_message;

public interface SendMessageAPIAccessInterface {

    /**
     * Sends a message using the Sendbird API
     * @param message text message to send
     * @param apiToken required for API authentication
     * @param channelUrl tells the api which channel to send the message in
     * @param senderId tells the api from whom the message is being sent by
     * @return the generated ID of the message from the API
     */
    Long sendMessage(String message, String apiToken, String channelUrl, Integer senderId);
}
