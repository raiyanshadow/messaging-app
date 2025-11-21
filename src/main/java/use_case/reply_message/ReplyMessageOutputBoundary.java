package use_case.reply_message;

public interface ReplyMessageOutputBoundary {
    void prepareReplyMessageSuccessView(ReplyMessageOutputData outputData);
    void prepareReplyMessageFailView(String errorMessage);
}
