package use_case.reply_message;

/**
 * Interface for the reply message use case's interactor.
 */
public interface ReplyMessageInputBoundary {
    /**
     * Replies to a parent message using child message strategy.
     * @param inputData input data needed to reply to message.
     */
    void execute(ReplyMessageInputData inputData);
}
