package use_case.update_chat_channel;

/**
 * Interface for the update chat channel use case's presenter.
 */
public interface UpdateChatChannelOutputBoundary {
    /**
     * Upon success, present the update chat channel success view.
     * @param outputData the output data needed to update the chat channel.
     */
    void prepareSuccessView(UpdateChatChannelOutputData outputData);

    /**
     * Upon failure, present the update chat channel fail view.
     * @param errorMessage error message.
     */
    void prepareFailView(String errorMessage);
}
