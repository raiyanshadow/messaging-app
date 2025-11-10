package use_case.update_chat_channel;

public interface UpdateChatChannelOutputBoundary {
    void prepareSuccessView(UpdateChatChannelOutputData outputData);
    void prepareFailView(String errorMessage);
}
