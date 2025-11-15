package interface_adapter.chat_channel;

import use_case.send_message.SendMessageInputData;
import use_case.send_message.SendMessageInputBoundary;

public class SendMessageController {
    private final SendMessageInputBoundary sendMessageUseCaseInteractor;

    public SendMessageController(SendMessageInputBoundary sendMessageUseCaseInteractor) {
        this.sendMessageUseCaseInteractor = sendMessageUseCaseInteractor;
    }

    public void execute(String message, String channelUrl, Integer senderID, Integer receiverID) {
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(
                message, channelUrl, senderID, receiverID);

        sendMessageUseCaseInteractor.execute(sendMessageInputData);
    }

}
