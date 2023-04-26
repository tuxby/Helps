package by.tux.helps.services;

import by.tux.helps.configuration.User;
import by.tux.helps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public List<User> getAllUsers(){
        List<User> userList = (List<User>) userRepository.findAll();
        if (userList.isEmpty()) {
            return null;
        }
        return userList;
    }

    public boolean userIsExist(String login){
        User user = userRepository.findUserByLogin(login);
        if (user!=null)
            return true;
        return false;
    }

    public void createUser(String login, String password, String name, String email, String role){
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        userRepository.save(user);
    }

}
