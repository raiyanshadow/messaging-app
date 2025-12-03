package use_case.delete_message;

/**
 * Interface for delete message use case's presenter.
 */
public interface DeleteMessageOutputBoundary {
    /**
     * On success, prepares the delete message success view.
     * @param outputData output data needed to update the chat channel.
     */
    void prepareDeleteMessageSuccessView(DeleteMessageOutputData outputData);

    /**
     * On failure, prepares teh delete message fail view.
     * @param error error message.
     */
    void prepareDeleteMessageFailView(String error);
}
