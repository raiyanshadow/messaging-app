package interface_adapter.chat_channel;

import use_case.delete_message.DeleteMessageInputBoundary;
import use_case.delete_message.DeleteMessageInputData;

/**
 * Controller for the delete message use case.
 */
public class DeleteMessageController {
    private final DeleteMessageInputBoundary deleteMessageUseCaseInteractor;

    public DeleteMessageController(DeleteMessageInputBoundary deleteMessageUseCaseInteractor) {
        this.deleteMessageUseCaseInteractor = deleteMessageUseCaseInteractor;
    }

    /**
     * Calls the interactor for the delete message use case.
     * @param messageId messageId to delete.
     * @param channelUrl channelUrl to delete from.
     */
    public void execute(Long messageId, String channelUrl) {
        final DeleteMessageInputData deleteMessageInputData = new DeleteMessageInputData(messageId, channelUrl);

        deleteMessageUseCaseInteractor.execute(deleteMessageInputData);
    }
}
