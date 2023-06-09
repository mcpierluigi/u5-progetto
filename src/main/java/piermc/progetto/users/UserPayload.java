package piermc.progetto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserPayload {
	@NotNull(message = "You must insert a name")
	@Size(min = 8, max = 15, message = "Name with min 78characters and max 15")
	String nickname;
	@NotNull(message = "You must insert a name")
	@Size(min = 3, max = 30, message = "Name with min 3 characters and max 30")
	String name;
	@NotNull(message = "You must insert a surname")
	String surname;
	@Email(message = "Your mail is not valid")
	String email;
	@NotNull(message = "Insert a password")
	String password;
}
