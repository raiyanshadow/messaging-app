package interface_adapter.chat_channel;

import use_case.edit_message.EditMessageInputBoundary;
import use_case.edit_message.EditMessageInputData;

public class EditMessageController {
    private final EditMessageInputBoundary editMessageUseCaseInteractor;

    public EditMessageController(EditMessageInputBoundary editMessageUseCaseInteractor) {
        this.editMessageUseCaseInteractor = editMessageUseCaseInteractor;
    }

    public void execute(String newMessage, String channelUrl, Long messageId) {
        final EditMessageInputData editMessageInputData = new EditMessageInputData(
                newMessage, channelUrl, messageId);

        editMessageUseCaseInteractor.execute(editMessageInputData);
    }
}
