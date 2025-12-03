package interface_adapter.chat_channel;

import use_case.edit_message.EditMessageInputBoundary;
import use_case.edit_message.EditMessageInputData;

/**
 * Controller for the edit message use case.
 */
public class EditMessageController {
    private final EditMessageInputBoundary editMessageUseCaseInteractor;

    public EditMessageController(EditMessageInputBoundary editMessageUseCaseInteractor) {
        this.editMessageUseCaseInteractor = editMessageUseCaseInteractor;
    }

    /**
     * Calls the interactor for the edit message use case.
     * @param newMessage new message to set to.
     * @param channelUrl channel url to edit in.
     * @param messageId message id to edit to.
     */
    public void execute(String newMessage, String channelUrl, Long messageId) {
        final EditMessageInputData editMessageInputData = new EditMessageInputData(
                newMessage, channelUrl, messageId);

        editMessageUseCaseInteractor.execute(editMessageInputData);
    }
}
