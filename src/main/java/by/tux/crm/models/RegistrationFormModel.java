package by.tux.crm.models;

import by.tux.crm.constants.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;
import by.tux.crm.configuration.User;
@Data
public class RegistrationFormModel {
    private String login;
    private String password;
    private String name;
    private String email;
    private Roles role;
    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setRole(Roles.USER);
        return user;
    }
}
