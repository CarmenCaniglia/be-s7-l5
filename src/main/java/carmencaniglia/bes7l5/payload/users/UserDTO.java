package carmencaniglia.bes7l5.payload.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max = 30, message = "Name must be between 3 e 30 chars")
        String name,
        @NotEmpty(message = "Surname cannot be empty")
        @Size(min = 3, max = 30, message = "Surname must be between 3 e 30 chars")
        String surname,
        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "The email address entered is invalid")
        String email,
        @NotEmpty(message = "La password è un campo obbligatorio!")
        @Size(min = 4, message = "La password deve avere minimo 4 caratteri!")
        String password
) {
}
