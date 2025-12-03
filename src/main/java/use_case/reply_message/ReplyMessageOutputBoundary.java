package use_case.reply_message;

/**
 * Interface for reply message's presenter.
 */
public interface ReplyMessageOutputBoundary {
    /**
     * On success, prepare the reply message success view.
     * @param outputData output data to update the chat channel and show the message reply.
     */
    void prepareReplyMessageSuccessView(ReplyMessageOutputData outputData);

    /**
     * On failure, prepare the reply message fail view.
     * @param errorMessage error message.
     */
    void prepareReplyMessageFailView(String errorMessage);
}
