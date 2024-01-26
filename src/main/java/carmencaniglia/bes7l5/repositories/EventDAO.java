package carmencaniglia.bes7l5.repositories;

import carmencaniglia.bes7l5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventDAO extends JpaRepository<Event, Long> {
    public Optional<Event> findById(long id);
}
