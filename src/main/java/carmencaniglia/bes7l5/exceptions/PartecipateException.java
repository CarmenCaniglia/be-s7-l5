package carmencaniglia.bes7l5.exceptions;

import carmencaniglia.bes7l5.entities.Event;
import carmencaniglia.bes7l5.entities.User;

public class PartecipateException extends RuntimeException{
    public PartecipateException(String message) {
        super(message);
    }

    public PartecipateException(Event event, User user) {
        super("User " + user.getName() + " is already participating in event " + event.getTitle());
    }
}
