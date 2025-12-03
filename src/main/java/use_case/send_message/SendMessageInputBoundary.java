package use_case.send_message;

/**
 * The interface for the send message use case's interactor.
 */
public interface SendMessageInputBoundary {
    /**
     * Uses the Sendbird API to send a message to another user in a chat channel.
     * @param sendMessageInputData input data needed to send message.
     */
    void execute(SendMessageInputData sendMessageInputData);
}
