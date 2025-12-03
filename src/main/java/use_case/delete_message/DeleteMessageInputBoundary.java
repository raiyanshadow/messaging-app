package use_case.delete_message;

/**
 * The interface for the delete message use case's interactor.
 */
public interface DeleteMessageInputBoundary {
    /**
     * Executes the interactor which attempts to delete the message via the SendBird API and also remove it from the
     *      database.
     * @param deleteMessageInputData the input data needed to delete the message.
     */
    void execute(DeleteMessageInputData deleteMessageInputData);
}
