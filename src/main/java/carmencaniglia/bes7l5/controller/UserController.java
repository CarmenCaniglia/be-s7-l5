package carmencaniglia.bes7l5.controller;

import carmencaniglia.bes7l5.entities.Event;
import carmencaniglia.bes7l5.entities.User;
import carmencaniglia.bes7l5.exceptions.BadRequestException;
import carmencaniglia.bes7l5.exceptions.NotFoundException;
import carmencaniglia.bes7l5.payload.users.UserDTO;
import carmencaniglia.bes7l5.payload.users.UserResDTO;
import carmencaniglia.bes7l5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy){
        return userService.getUsers(page, size, orderBy);
    }

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/me/events")
    public Page<Event> getEvents(@AuthenticationPrincipal User user,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sort) {
        return userService.getEvents(user, page, size, sort);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUserById(@PathVariable long userId){
        try {
            return userService.findById(userId);
        }catch (Exception e){
            throw new NotFoundException(userId);
        }
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResDTO createUser(@RequestBody @Validated UserDTO newUserPayload, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException("Payload's error!");
        }else {
            User newUser = userService.save(newUserPayload);
            return new UserResDTO(newUser.getId());
        }
    }*/

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(@PathVariable long userId,@RequestBody User updateUserPayload){
        return userService.findByIdAndUpdate(userId, updateUserPayload);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAndDeleteUser(@PathVariable long userId){
        userService.deleteById(userId);
    }

    @GetMapping("/{id}/set-admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User setAdmin(@PathVariable long id) {
        return userService.setAdmin(id);
    }

    @GetMapping("/{id}/set-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User setUser(@PathVariable long id) {
        return userService.setUser(id);
    }
}
