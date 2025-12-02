package use_case.update_chat_channel;

public interface UpdateChatChannelOutputBoundary {
    /**
     * Prepares the view showing that the interactor successfully executed.
     * @param outputData data containing information about the chat URL, messages, sender, and receiver
     */
    void prepareSuccessView(UpdateChatChannelOutputData outputData);

    /**
     * Prepares the view showing that the interactor did not successfully execute.
     * @param errorMessage a String that explains the error in the code
     */
    void prepareFailView(String errorMessage);
}
