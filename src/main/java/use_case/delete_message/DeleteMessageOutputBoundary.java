package use_case.delete_message;

public interface DeleteMessageOutputBoundary {
    void prepareDeleteMessageSuccessView(DeleteMessageOutputData outputData);
    void prepareDeleteMessageFailView(String error);
}
