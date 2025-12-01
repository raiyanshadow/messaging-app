package use_case.delete_message;

import sendbirdapi.MessageDeleter;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.SQLException;

public class DeleteMessageInteractor implements DeleteMessageInputBoundary {
    private final DeleteMessageOutputBoundary presenter;
    private final DeleteMessageDataAccessInterface messageDataAccessObject;
    private final MessageDeleter messageDeleter;
    private final Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    public DeleteMessageInteractor(DeleteMessageOutputBoundary presenter,
                                   DeleteMessageDataAccessInterface messageDataAccessObject,
                                   MessageDeleter messageDeleter) {
        this.presenter = presenter;
        this.messageDataAccessObject = messageDataAccessObject;
        this.messageDeleter = messageDeleter;
    }

    @Override
    public void execute(DeleteMessageInputData deleteMessageInputData) {
        String status = messageDeleter.deleteMessage(dotenv.get("MSG_TOKEN"), deleteMessageInputData.getMessageId(),
                deleteMessageInputData.getChannelUrl());
        if (status.equals("fail")) {
            presenter.prepareDeleteMessageFailView("Sendbird write fail");
            return;
        }

        try {
            messageDataAccessObject.deleteMessage(deleteMessageInputData.getMessageId(),
                    deleteMessageInputData.getChannelUrl());
        } catch (SQLException e) {
            presenter.prepareDeleteMessageFailView("DB write fail");
            return;
        }

        DeleteMessageOutputData response = new DeleteMessageOutputData(
                deleteMessageInputData.getMessageId()
        );

        presenter.prepareDeleteMessageSuccessView(response);
    }
}
