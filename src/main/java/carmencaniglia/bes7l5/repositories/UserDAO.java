package carmencaniglia.bes7l5.repositories;

import carmencaniglia.bes7l5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(long id);
}
