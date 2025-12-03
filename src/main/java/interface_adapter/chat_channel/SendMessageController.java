package interface_adapter.chat_channel;

import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;

/**
 * Controller for the send message use case.
 */
public class SendMessageController {
    private final SendMessageInputBoundary sendMessageUseCaseInteractor;

    public SendMessageController(SendMessageInputBoundary sendMessageUseCaseInteractor) {
        this.sendMessageUseCaseInteractor = sendMessageUseCaseInteractor;
    }

    /**
     * Calls the interactor for the send message use case.
     * @param message content of the message to send.
     * @param channelUrl url to send the message in.
     * @param receiverID the id of the receiving user.
     */
    public void execute(String message, String channelUrl, Integer receiverID) {
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(
                message, channelUrl, receiverID);

        sendMessageUseCaseInteractor.execute(sendMessageInputData);
    }
}
