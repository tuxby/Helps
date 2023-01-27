package by.tux.helps.services;

import by.tux.helps.configuration.User;
import by.tux.helps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public List<User> getAllUsers(){
        List<User> userList = (List<User>) userRepository.findAll();
        if (userList.isEmpty()) {
            return null;
        }
        return userList;
    }
}
