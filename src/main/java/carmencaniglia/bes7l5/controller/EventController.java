package carmencaniglia.bes7l5.controller;

import carmencaniglia.bes7l5.entities.Event;
import carmencaniglia.bes7l5.exceptions.BadRequestException;
import carmencaniglia.bes7l5.payload.events.EventDTO;
import carmencaniglia.bes7l5.payload.events.EventResDTO;
import carmencaniglia.bes7l5.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return eventService.getEvents(page, size, orderBy);
    }

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable long eventId) {
        return eventService.findById(eventId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResDTO createEvent(@RequestBody @Validated EventDTO newEventPayload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Payload's error!");
        } else {
            Event newEvent = eventService.save(newEventPayload);
            return new EventResDTO(newEvent.getId());
        }
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable long eventId, @RequestBody Event updateEventPayload) {
        return eventService.findByIdAndUpdate(eventId, updateEventPayload);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAndDeleteEvent(@PathVariable long eventId) {
        eventService.deleteById(eventId);
    }
}
