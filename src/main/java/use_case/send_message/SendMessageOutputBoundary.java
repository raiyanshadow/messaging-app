package use_case.send_message;

/**
 * Interface for the send message use case's presenter.
 */
public interface SendMessageOutputBoundary {
    /**
     * On success, prepare the send message success view.
     * @param outputData output data needed to update the chat channel.
     */
    void prepareSendMessageSuccessView(SendMessageOutputData outputData);

    /**
     * On failure, prepare the send message fail view.
     * @param error error message.
     */
    void prepareSendMessageFailView(String error);
}
