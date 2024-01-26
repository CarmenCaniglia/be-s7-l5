package carmencaniglia.bes7l5.controller;

import carmencaniglia.bes7l5.entities.User;
import carmencaniglia.bes7l5.exceptions.BadRequestException;
import carmencaniglia.bes7l5.payload.users.UserDTO;
import carmencaniglia.bes7l5.payload.users.UserLoginDTO;
import carmencaniglia.bes7l5.payload.users.UserLoginResDTO;
import carmencaniglia.bes7l5.payload.users.UserResDTO;
import carmencaniglia.bes7l5.services.AuthService;
import carmencaniglia.bes7l5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public UserLoginResDTO login(@RequestBody UserLoginDTO body){
    String accessToken = authService.authenticateUser(body);
    return new UserLoginResDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResDTO createUser(@RequestBody @Validated UserDTO newUserPayload, BindingResult validation) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the payload!");
        } else {
            User newUser = authService.save(newUserPayload);

            return new UserResDTO(newUser.getId());
        }
    }

}
