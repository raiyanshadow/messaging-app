package use_case.delete_message;

public class DeleteMessageOutputData {
    private final Long messageId;

    public DeleteMessageOutputData(Long messageId) {
        this.messageId = messageId;
    }

    public long getMessageId() { return messageId; }
}
