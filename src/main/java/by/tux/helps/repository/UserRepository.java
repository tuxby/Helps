package by.tux.helps.repository;

import org.springframework.data.repository.CrudRepository;
import by.tux.helps.configuration.User;
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByLogin(String login);

}
