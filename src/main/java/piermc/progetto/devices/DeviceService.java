package piermc.progetto.devices;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import piermc.progetto.exceptions.NotFoundException;

public class DeviceService {
	
	@Autowired
	private DeviceRepository deviceRepo;
	
	public Device createDevice(Device d) {
		Device newDevice = new Device(d.getDeviceType(), d.getDeviceStatus());
		return deviceRepo.save(newDevice);
	}
	
	public Page<Device> findAllDevices(int page, int size, String sortBy) {
		if (size < 0) 
			size = 10;
		if(size > 100)
			size = 20;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return deviceRepo.findAll(pageable);
	}
	
	public Device findDeviceById(UUID id) throws NotFoundException {
		return deviceRepo.findById(id).orElseThrow(() -> new NotFoundException("Device not found!")); 
	}
	
	public Device findDeviceByIdAndUpdate(UUID id, Device d) throws NotFoundException {
			Device foundDevice = this.findDeviceById(id);
			foundDevice.setId(id);
			foundDevice.setDeviceStatus(d.getDeviceStatus());
			return deviceRepo.save(foundDevice);
	}
	
	public void findDeviceByIdAndDelete(UUID id) throws NotFoundException {
		Device foundDevice = this.findDeviceById(id);
		deviceRepo.delete(foundDevice);
	}

}
