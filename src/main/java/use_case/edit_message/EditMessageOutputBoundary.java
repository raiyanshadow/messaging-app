package use_case.edit_message;

public interface EditMessageOutputBoundary {
    void prepareEditMessageSuccessView(EditMessageOutputData outputData);
    void prepareEditMessageFailView(String error);
}
