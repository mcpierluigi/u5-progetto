package piermc.progetto.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import piermc.progetto.exceptions.UnauthorizedException;
import piermc.progetto.users.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Validated UserPayload body) {
		User createdUser = userService.createUser(body);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/login") 
	public ResponseEntity<SuccessfullAuthenticationPayload> login(@RequestBody UserLoginPayload body) {
		
		//verificare che l'email sia presente nel db
		User user = userService.findUserByEmail(body.getEmail());
		
		//verificare che la password corrisponde
		if(!body.getPassword().matches(user.getPassword()))
			throw new UnauthorizedException("Credentials not valid");
		
		//se tutto ok genero il token tramite JWT
		String token = JWTTools.createToken(null);
		
		//altrimenti errore
		return new ResponseEntity<>(new SuccessfullAuthenticationPayload(token), HttpStatus.OK);
		}
}
