package piermc.progetto.users;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import piermc.progetto.exceptions.*;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	public User createUser(UserPayload u) {
		userRepo.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email" + user.getEmail() + "already in use!");
		});
		User newUser = new User(u.getNickname(), u.getName(), u.getSurname(), u.getEmail(), u.getPassword());
		return userRepo.save(newUser);
	}
	
	public Page<User> findAllUsers(int page, int size, String sortBy) {
		if (size < 0) 
			size = 10;
		if(size > 100)
			size = 20;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return userRepo.findAll(pageable);
	}
	
	public User finUserById(UUID id) throws NotFoundException {
		return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found!")); 
	}
	
	public User findUserByIdAndUpdate(UUID id, User u) throws NotFoundException {
			User foundUser = this.finUserById(id);
			foundUser.setId(id);
			foundUser.setUsername(u.getUsername());
			foundUser.setName(u.getName());
			foundUser.setSurname(u.getSurname());
			foundUser.setEmail(u.getEmail());
			return userRepo.save(foundUser);
	}
	
	public void findUserByIdAndDelete(UUID id) throws NotFoundException {
		User foundUser = this.finUserById(id);
		userRepo.delete(foundUser);
	}
	
	public User findUserByEmail(String email) throws NotFoundException {
	return userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found!")); 
	}
}
