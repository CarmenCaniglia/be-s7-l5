package carmencaniglia.bes7l5.services;

import carmencaniglia.bes7l5.entities.Event;
import carmencaniglia.bes7l5.entities.Role;
import carmencaniglia.bes7l5.entities.User;
import carmencaniglia.bes7l5.exceptions.NotFoundException;
import carmencaniglia.bes7l5.repositories.EventDAO;
import carmencaniglia.bes7l5.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;


    public Page<User> getUsers(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User findById(long id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    public void deleteById(long id) {
        User found = this.findById(id);
        userDAO.delete(found);
    }

    public User findByIdAndUpdate(Long id, User body) {
        User found = this.findById(id);
        found.setSurname(body.getSurname());
        found.setName(body.getName());
        found.setEmail(body.getEmail());
        found.setPassword(body.getPassword());
        return userDAO.save(found);
    }

    public User findByEmail(String email) throws NotFoundException {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found!"));

    }

    public User setAdmin(long id) {
        User user = userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        user.setRole(Role.ADMIN);
        return userDAO.save(user);
    }

    public User setUser(long id) {
        User user = userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        user.setRole(Role.USER);
        return userDAO.save(user);
    }

    public Page<Event> getEvents(User user, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eventDAO.findByUsers(user, pageable);
    }
}
