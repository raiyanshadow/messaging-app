package use_case.edit_message;

/**
 * Interface for edit message use case's presenter.
 */
public interface EditMessageOutputBoundary {
    /**
     * On success, prepare the view for edit message's success view.
     * @param outputData output data to update the chat channel on success.
     */
    void prepareEditMessageSuccessView(EditMessageOutputData outputData);

    /**
     * On failure, prepare the view for edit message's fail view.
     * @param error error message.
     */
    void prepareEditMessageFailView(String error);
}
