package by.tux.crm.controller;

import by.tux.crm.configuration.User;
import by.tux.crm.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model){
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "/admin/users";
    }

}