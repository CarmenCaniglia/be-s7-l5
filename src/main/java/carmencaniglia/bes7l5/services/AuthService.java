package carmencaniglia.bes7l5.services;

import carmencaniglia.bes7l5.entities.Role;
import carmencaniglia.bes7l5.entities.User;
import carmencaniglia.bes7l5.exceptions.BadRequestException;
import carmencaniglia.bes7l5.exceptions.UnauthorizedException;
import carmencaniglia.bes7l5.payload.users.UserDTO;
import carmencaniglia.bes7l5.payload.users.UserLoginDTO;
import carmencaniglia.bes7l5.repositories.UserDAO;
import carmencaniglia.bes7l5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body){
        User user = userService.findByEmail(body.email());
        if(body.password().equals(user.getPassword())){
        return jwtTools.createToken(user);
        }else{
            throw new UnauthorizedException("Invalid credentials!");
        }
    }

    public User save(UserDTO body) {

        userDAO.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("The email " + user.getEmail() + " is already used!");
        });

        User newUser = new User();
        newUser.setSurname(body.surname());
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setRole(Role.USER);
        return userDAO.save(newUser);
    }
}
