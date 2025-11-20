package interface_adapter.chat_channel;

import use_case.delete_message.DeleteMessageInputBoundary;
import use_case.delete_message.DeleteMessageInputData;

public class DeleteMessageController {
    private final DeleteMessageInputBoundary deleteMessageUseCaseInteractor;

    public DeleteMessageController(DeleteMessageInputBoundary deleteMessageUseCaseInteractor) {
        this.deleteMessageUseCaseInteractor = deleteMessageUseCaseInteractor;
    }

    public void execute(Long messageId, String channelUrl) {
        final DeleteMessageInputData deleteMessageInputData = new DeleteMessageInputData(messageId, channelUrl);

        deleteMessageUseCaseInteractor.execute(deleteMessageInputData);
    }
}
