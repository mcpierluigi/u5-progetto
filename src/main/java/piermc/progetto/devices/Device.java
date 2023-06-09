package piermc.progetto.devices;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import piermc.progetto.users.User;

@Entity
@Table(name="Devices")
@Data
@NoArgsConstructor
public class Device {
	@Id
	@GeneratedValue
	private UUID id;
	private DeviceType deviceType;
	private DeviceStatus deviceStatus;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
	private User user;
	
	public Device (DeviceType deviceType, DeviceStatus deviceStatus) {
		this.deviceType = deviceType;
		this.deviceStatus = deviceStatus;
	}
}
