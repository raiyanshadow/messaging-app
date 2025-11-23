package interface_adapter.chat_channel;

import use_case.reply_message.ReplyMessageInputData;
import use_case.reply_message.ReplyMessageInputBoundary;

public class ReplyMessageController {
    private final ReplyMessageInputBoundary replyMessageUseCaseInteractor;

    public ReplyMessageController(ReplyMessageInputBoundary replyMessageUseCaseInteractor) {
        this.replyMessageUseCaseInteractor = replyMessageUseCaseInteractor;
    }

    public void execute(Long parentMessageId, String message, String channelUrl,
                        Integer senderID, Integer receiverID) {
        final ReplyMessageInputData replyMessageInputData = new ReplyMessageInputData(
                parentMessageId, message, channelUrl, senderID, receiverID);

        replyMessageUseCaseInteractor.execute(replyMessageInputData);
    }
}