package use_case.edit_message;

public interface EditMessageOutputBoundary {
    public void prepareEditMessageSuccessView(EditMessageOutputData outputData);
    public void prepareEditMessageFailView(String error);
}
