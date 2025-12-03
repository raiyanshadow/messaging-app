package interface_adapter.chat_channel;

import use_case.delete_message.DeleteMessageOutputBoundary;
import use_case.delete_message.DeleteMessageOutputData;
import use_case.edit_message.EditMessageOutputBoundary;
import use_case.edit_message.EditMessageOutputData;
import use_case.reply_message.ReplyMessageOutputBoundary;
import use_case.reply_message.ReplyMessageOutputData;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

/**
 * Presenter for the associated use cases of chat channel.
 */
public class ChatChannelPresenter implements SendMessageOutputBoundary, EditMessageOutputBoundary,
                                             ReplyMessageOutputBoundary, DeleteMessageOutputBoundary {

    private final MessageViewModel messageViewModel;

    public ChatChannelPresenter(MessageViewModel messageViewModel) {
        this.messageViewModel = messageViewModel;
    }

    @Override
    public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setChannelUrl(outputData.getChannelUrl());
        messageState.setContent(outputData.getContent());
        messageState.setSenderID(outputData.getSenderID());
        messageState.setReceiverID(outputData.getReceiverID());
        messageState.setTimestamp(outputData.getTimestamp());
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareSendMessageFailView(String error) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareEditMessageSuccessView(EditMessageOutputData outputData) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setContent(outputData.getNewContent());
        messageState.setChannelUrl(outputData.getChannelUrl());
        messageState.setSenderID(outputData.getSenderId());
        messageState.setReceiverID(outputData.getReceiverId());
        messageState.setTimestamp(outputData.getOldTimestamp());
        messageState.setEditedTimestamp(outputData.getNewTimestamp());
        messageState.setEdited(true);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareEditMessageFailView(String error) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareReplyMessageSuccessView(ReplyMessageOutputData outputData) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setContent(outputData.getContent());
        messageState.setChannelUrl(outputData.getChannelUrl());
        messageState.setSenderID(outputData.getSenderId());
        messageState.setReceiverID(outputData.getReceiverId());
        messageState.setTimestamp(outputData.getTimestamp());
        messageState.setReplied(true);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareReplyMessageFailView(String error) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareDeleteMessageSuccessView(DeleteMessageOutputData outputData) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setContent(null);
        messageState.setChannelUrl(null);
        messageState.setReceiverID(null);
        messageState.setSenderID(null);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareDeleteMessageFailView(String error) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }
}
