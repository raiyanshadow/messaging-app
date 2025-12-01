package interface_adapter.chat_channel;

import use_case.send_message.SendMessageOutputBoundary;
import use_case.edit_message.EditMessageOutputBoundary;
import use_case.reply_message.ReplyMessageOutputBoundary;
import use_case.delete_message.DeleteMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;
import use_case.edit_message.EditMessageOutputData;
import use_case.reply_message.ReplyMessageOutputData;
import use_case.delete_message.DeleteMessageOutputData;

public class ChatChannelPresenter implements SendMessageOutputBoundary, EditMessageOutputBoundary,
                                             ReplyMessageOutputBoundary, DeleteMessageOutputBoundary {

    private final MessageViewModel messageViewModel;

    public ChatChannelPresenter(MessageViewModel messageViewModel) {
        this.messageViewModel = messageViewModel;
    }

    @Override
    public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
        MessageState messageState = messageViewModel.getState();
        messageState.setChannelURL(outputData.getChannelUrl());
        messageState.setContent(outputData.getContent());
        messageState.setSenderID(outputData.getSenderID());
        messageState.setReceiverID(outputData.getReceiverID());
        messageState.setTimestamp(outputData.getTimestamp());
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareSendMessageFailView(String error) {
        MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareEditMessageSuccessView(EditMessageOutputData outputData) {
        MessageState messageState = messageViewModel.getState();
        messageState.setContent(outputData.getNewContent());
        messageState.setChannelURL(outputData.getChannelUrl());
        messageState.setSenderID(outputData.getSenderId());
        messageState.setReceiverID(outputData.getReceiverId());
        messageState.setTimestamp(outputData.getOldTimestamp());
        messageState.setEditedTimestamp(outputData.getNewTimestamp());
        messageState.setEdited(true);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareEditMessageFailView(String error) {
        MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareReplyMessageSuccessView(ReplyMessageOutputData outputData) {
        MessageState messageState = messageViewModel.getState();
        messageState.setContent(outputData.getContent());
        messageState.setChannelURL(outputData.getChannelUrl());
        messageState.setSenderID(outputData.getSenderId());
        messageState.setReceiverID(outputData.getReceiverId());
        messageState.setTimestamp(outputData.getTimestamp());
        messageState.setReplied(true);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareReplyMessageFailView(String error) {
        MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareDeleteMessageSuccessView(DeleteMessageOutputData outputData) {
        MessageState messageState = messageViewModel.getState();
        messageState.setContent(null);
        messageState.setChannelURL(null);
        messageState.setReceiverID(null);
        messageState.setSenderID(null);
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareDeleteMessageFailView(String error) {
        MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }

    public void prepareReturnToHome() {
        MessageState messageState = messageViewModel.getState();
        messageViewModel.firePropertyChange();
    }
}
