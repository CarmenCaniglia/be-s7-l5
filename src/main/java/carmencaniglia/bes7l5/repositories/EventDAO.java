package carmencaniglia.bes7l5.repositories;

import carmencaniglia.bes7l5.entities.Event;
import carmencaniglia.bes7l5.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventDAO extends JpaRepository<Event, Long> {
    public Optional<Event> findById(long id);
    public Page<Event> findByUsers(User user, Pageable pageable);
}
