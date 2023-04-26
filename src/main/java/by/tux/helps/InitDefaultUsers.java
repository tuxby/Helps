package by.tux.helps;

import by.tux.helps.configuration.User;
import by.tux.helps.constants.Roles;
import by.tux.helps.repository.UserRepository;
import by.tux.helps.services.UserService;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;


@Service
public class InitDefaultUsers {
    private final UserService userService;

    public InitDefaultUsers(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void createDefaultsUsers() {
        System.out.println("Create demo users");
        if (!userService.userIsExist("admin")){
            userService.createUser("admin","Admin123","Администратор","admin@helps.com",Roles.ADMIN);
            System.out.println("admin is exist");
        }
        else if (!userService.userIsExist("user")){
            userService.createUser("user","User123","Пользователь","user@hels.com",Roles.USER);
            System.out.println("admin is exist");
        }

    }


}
