package use_case.send_message;

public interface SendMessageOutputBoundary {
    void prepareSendMessageSuccessView(SendMessageOutputData outputData);
    void prepareSendMessageFailView(String error);
}
