package carmencaniglia.bes7l5.services;

import carmencaniglia.bes7l5.entities.Event;
import carmencaniglia.bes7l5.entities.User;
import carmencaniglia.bes7l5.exceptions.NotFoundException;
import carmencaniglia.bes7l5.exceptions.PartecipateException;
import carmencaniglia.bes7l5.payload.events.EventDTO;
import carmencaniglia.bes7l5.repositories.EventDAO;
import carmencaniglia.bes7l5.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class EventService {
    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private UserDAO userDAO;

    public Event save(EventDTO body) {
        Event event = new Event();
        event.setTitle(body.title());
        event.setDescription(body.description());
        String dateStr = body.date().toString();
        try {
            event.setDate(LocalDate.parse(dateStr));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }
        event.setLocation(body.location());
        event.setMaxPartecipants(body.maxParticipants());
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

    public Event bookEvent(long eventId, long userId) {
        Event event = eventDAO.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));

        if (event.getUsers().contains(user)) {
            throw new PartecipateException(event, user);
        } else {
            if (event.getUsers().size() < event.getMaxPartecipants()) {
                event.getUsers().add(user);
                return eventDAO.save(event);
            } else {
                throw new PartecipateException("SOLD OUT!");
            }
        }
    }
}
