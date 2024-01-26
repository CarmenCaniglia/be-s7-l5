package carmencaniglia.bes7l5.services;

import carmencaniglia.bes7l5.entities.User;
import carmencaniglia.bes7l5.exceptions.UnauthorizedException;
import carmencaniglia.bes7l5.payload.users.UserLoginDTO;
import carmencaniglia.bes7l5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUser(UserLoginDTO body){
        User user = userService.findByEmail(body.email());
        if(body.password().equals(user.getPassword())){
        return jwtTools.createToken(user);
        }else{
            throw new UnauthorizedException("Invalid credentials!");
        }
    }
}
