package use_case.edit_message;

/**
 * Interface for the edit message use case's interactor.
 */
public interface EditMessageInputBoundary {
    void execute(EditMessageInputData editMessageInputData);
}
