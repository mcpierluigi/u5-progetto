package piermc.progetto.devices;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Device saveUser(@RequestBody @Validated Device body) {
		return deviceService.createDevice(body);
	}
	
	@GetMapping("")
	public Page<Device> Devices(@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "10") int size, 
				@RequestParam(defaultValue ="id") String sortBy) {
		return deviceService.findAllDevices(page, size, sortBy);
	}
	
	@GetMapping("/{deviceId")
	public Device getDevice(@PathVariable UUID deviceId) throws Exception {
		return deviceService.findDeviceById(deviceId);
	}
	
	@PutMapping("/{deviceId}")
	public Device updateUser(@PathVariable UUID deviceId, @RequestBody Device body) throws Exception {
		return deviceService.findDeviceByIdAndUpdate(deviceId, body);
	}
	
	@DeleteMapping("/{deviceId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		deviceService.findDeviceByIdAndDelete(userId);
	}
}
