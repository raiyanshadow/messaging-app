package use_case.baseUI;

import entity.DirectChatChannel;

import java.util.List;

public interface BaseUIOutputBoundary {
    void DisplayUI(BaseUIOutputData response);
    void DisplayAddChat(BaseUIOutputData response);
}
