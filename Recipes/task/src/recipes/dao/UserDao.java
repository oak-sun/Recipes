package recipes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.models.User;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
