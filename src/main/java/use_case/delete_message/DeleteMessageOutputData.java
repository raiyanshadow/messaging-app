package use_case.delete_message;

public class DeleteMessageOutputData {
    private Long messageId;

    public DeleteMessageOutputData(Long messageId) {
        this.messageId = messageId;
    }

    public long getMessageId() { return messageId; }
}
