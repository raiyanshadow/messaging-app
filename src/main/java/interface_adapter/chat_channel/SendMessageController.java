package interface_adapter.chat_channel;

import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;

public class SendMessageController {
    private final SendMessageInputBoundary sendMessageUseCaseInteractor;

    public SendMessageController(SendMessageInputBoundary sendMessageUseCaseInteractor) {
        this.sendMessageUseCaseInteractor = sendMessageUseCaseInteractor;
    }

    public void execute(String message, String channelUrl, Integer receiverID) {
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(
                message, channelUrl, receiverID);

        sendMessageUseCaseInteractor.execute(sendMessageInputData);
    }
}
