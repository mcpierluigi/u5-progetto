package piermc.progetto.users;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody @Validated UserPayload body) {
		return userService.createUser(body);
	}
	
	@GetMapping("")
	public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "10") int size, 
				@RequestParam(defaultValue ="id") String sortBy) {
		return userService.findAllUsers(page, size, sortBy);
	}
	
	@GetMapping("/{userId")
	public User getUser(@PathVariable UUID userId) throws Exception {
		return userService.finUserById(userId);
	}
	
	@PutMapping("/{userId}")
	public User updateUser(@PathVariable UUID userId, @RequestBody User body) throws Exception {
		return userService.findUserByIdAndUpdate(userId, body);
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		userService.findUserByIdAndDelete(userId);
	}
	
}
