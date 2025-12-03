package interface_adapter.chat_channel;

import use_case.reply_message.ReplyMessageInputBoundary;
import use_case.reply_message.ReplyMessageInputData;

/**
 * Controller for the reply message use case.
 */
public class ReplyMessageController {
    private final ReplyMessageInputBoundary replyMessageUseCaseInteractor;

    public ReplyMessageController(ReplyMessageInputBoundary replyMessageUseCaseInteractor) {
        this.replyMessageUseCaseInteractor = replyMessageUseCaseInteractor;
    }

    /**
     * Calls the interactor for the reply message use case.
     * @param parentMessageId id of the message to reply to.
     * @param message content of the reply.
     * @param channelUrl url of channel to reply in.
     * @param senderID id of the sending user.
     * @param receiverID id of the receiving user.
     */
    public void execute(Long parentMessageId, String message, String channelUrl,
                        Integer senderID, Integer receiverID) {
        final ReplyMessageInputData replyMessageInputData = new ReplyMessageInputData(
                parentMessageId, message, channelUrl, senderID, receiverID);

        replyMessageUseCaseInteractor.execute(replyMessageInputData);
    }
}