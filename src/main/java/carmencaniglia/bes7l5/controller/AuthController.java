package carmencaniglia.bes7l5.controller;

import carmencaniglia.bes7l5.payload.users.UserLoginDTO;
import carmencaniglia.bes7l5.payload.users.UserLoginResDTO;
import carmencaniglia.bes7l5.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public UserLoginResDTO login(@RequestBody UserLoginDTO body){
    String accessToken = authService.authenticateUser(body);
    return new UserLoginResDTO(accessToken);
    }
}
