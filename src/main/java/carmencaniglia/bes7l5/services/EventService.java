package carmencaniglia.bes7l5.services;

import carmencaniglia.bes7l5.entities.Event;
import carmencaniglia.bes7l5.entities.User;
import carmencaniglia.bes7l5.exceptions.NotFoundException;
import carmencaniglia.bes7l5.repositories.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EventService {
    @Autowired
    private EventDAO eventDAO;

    public Event save(Event body) {
        Event event = new Event();
        event.setTitle(body.getTitle());
        event.setDescription(body.getDescription());
        String dateStr = body.getDate().toString();
        try {
            event.setDate(LocalDate.parse(dateStr));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }
        event.setLocation(body.getLocation());
        event.setMaxPartecipants(body.getMaxPartecipants());
        return eventDAO.save(event);
    }

    public Event findById(long id) {
        return eventDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Event> getEvents(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventDAO.findAll(pageable);
    }

    public void deleteById(long id) {
        Event found = this.findById(id);
        eventDAO.delete(found);
    }

    public Event findByIdAndUpdate(Long id, Event body) {
        Event found = this.findById(id);
        found.setTitle(body.getTitle());
        found.setDescription(body.getDescription());
        found.setDate(body.getDate());
        found.setLocation(body.getLocation());
        found.setMaxPartecipants(body.getMaxPartecipants());
        return eventDAO.save(found);
    }
}
