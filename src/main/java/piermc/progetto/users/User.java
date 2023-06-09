package piermc.progetto.users;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import piermc.progetto.devices.Device;

@Entity
@Table(name="Users")
@Data
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue
	private UUID id;
	private String username, name, surname, email, password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany
	@JoinColumn(name = "device_id", referencedColumnName = "id", nullable = true)
	private List<Device> devices;
	
	public User (String username, String name, String surname, String mail, String password) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = mail;	
		this.password = password;
		this.role = Role.USER;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
