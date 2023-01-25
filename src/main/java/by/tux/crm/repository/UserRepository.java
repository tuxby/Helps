package by.tux.crm.repository;

import org.springframework.data.repository.CrudRepository;
import by.tux.crm.configuration.User;
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByLogin(String login);
}
