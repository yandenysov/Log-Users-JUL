package org.example.app.service;

import org.example.app.controller.UserController;
import org.example.app.exceptions.OptionException;
import org.example.app.repository.impl.UserRepository;
import org.example.app.utils.AppStarter;
import org.example.app.utils.Constants;
import org.example.app.view.AppView;
import org.example.app.view.UserView;

public class AppService {

    UserRepository repository = new UserRepository();
    UserService service = new UserService(repository);
    UserView view = new UserView();
    UserController controller = new UserController(service, view);

    public void handleOption(int option) {
        switch (option) {
            case 1 -> controller.createUser();
            case 2 -> controller.readUsers();
            case 3 -> controller.updateUser();
            case 4 -> controller.deleteUser();
            case 5 -> controller.readUserById();
            case 0 -> new AppView().getOutput(Integer.toString(option));
            default -> {
                try {
                    throw new OptionException(Constants.INCORRECT_OPTION_MSG);
                } catch (OptionException e) {
                    new AppView().getOutput(e.getMessage());
                    AppStarter.startApp();
                }
            }
        }
    }
}