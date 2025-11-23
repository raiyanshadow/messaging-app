package app;

import data_access.*;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelPresenter;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactPresenter;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIPresenter;
import interface_adapter.base_UI.baseUIViewModel;

import interface_adapter.chat_channel.ChatChannelViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_contact.*;
import use_case.baseUI.BaseUIInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;
import view.AddContactView;
import view.BaseUIView;
import view.CreateChatView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddContactViewTest {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("add contact testing");

        // DAO
        Connection conn = DBConnectionFactory.createConnection();
        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);
        DBChatChannelDataAccessObject dbChatChannelDataAccessObject = new DBChatChannelDataAccessObject(conn);


        // testing if actually able to send request
        // dummyDAO.sendRequest(dummyDAO.getUserFromName("a"), "Bob");
        User temp = dummyUserDAO.getUserFromID(1);
        dummyContactDAO.updateUserContacts(temp, temp.getContacts());

        System.out.println(temp.getUsername());

        AddContactViewModel addContactViewModel = new AddContactViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        AddContactPresenter presenter = new AddContactPresenter(addContactViewModel, viewManagerModel);
        SessionManager sessionManager = new SessionManager(temp, true);

        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("chatChannelViewModel");
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelViewModel");
        AddChatChannelPresenter addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel, addChatChannelViewModel, viewManagerModel);
        baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel);

        AddChatChannelInteractor addChatChannelInteractor = new AddChatChannelInteractor(addChatChannelPresenter, dbChatChannelDataAccessObject, dummyUserDAO, sessionManager);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dbChatChannelDataAccessObject, dummyUserDAO, sessionManager);

        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor);
        ViewManager viewManager = new ViewManager(viewManagerModel);


        // Create actual base view and register it
        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController);
        CreateChatView addChatChannelView = new CreateChatView(sessionManager, addChatChannelController, baseUIViewModel, baseUIController);
        AddContactView addContactview = new AddContactView(addContactViewModel, viewManagerModel, sessionManager, baseUIController);

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, presenter);
        AddContactController controller = new AddContactController(interactor);
        addContactview.setAddContactController(controller);


        viewManager.addView(addContactview, addChatChannelViewModel.getViewName());
        viewManager.addView(baseUIView, baseUIViewModel.getViewName());
        viewManager.addView(addChatChannelView, addChatChannelViewModel.getViewName());

        frame.add(viewManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
