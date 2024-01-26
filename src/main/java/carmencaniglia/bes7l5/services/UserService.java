package carmencaniglia.bes7l5.services;

import carmencaniglia.bes7l5.entities.User;
import carmencaniglia.bes7l5.exceptions.BadRequestException;
import carmencaniglia.bes7l5.exceptions.NotFoundException;
import carmencaniglia.bes7l5.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



public class UserService {
    @Autowired
    private UserDAO userDAO;

    public Page<User> getUsers(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User findById(long id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User save(User body) {
        userDAO.findByEmail(body.getEmail()).ifPresent(user -> {
            throw new BadRequestException("The email " + user.getEmail() + " is already used!");
        });

        User newUser = new User();
        newUser.setSurname(body.getSurname());
        newUser.setName(body.getName());
        newUser.setEmail(body.getEmail());
        newUser.setPassword(body.getPassword());
        return userDAO.save(newUser);
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
}
