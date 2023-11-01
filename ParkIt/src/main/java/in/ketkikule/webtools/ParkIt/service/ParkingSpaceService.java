package in.ketkikule.webtools.ParkIt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.ketkikule.webtools.ParkIt.dao.ParkingSpaceDAO;
import in.ketkikule.webtools.ParkIt.model.ParkingSpace;
import in.ketkikule.webtools.ParkIt.model.User;

@Service
public class ParkingSpaceService {
	@Autowired
	private ParkingSpaceDAO parkingDAO;

	public ParkingSpace registerParkingSpace(ParkingSpace parkingSpace) {
		return this.parkingDAO.saveParkingSpace(parkingSpace);
	}

	public List<ParkingSpace> getParkingSpaces(String id) {
		return this.parkingDAO.getParkingSpacesByUserId(Integer.parseInt(id));
	}
	
	public List<ParkingSpace> getParkingSpaceByCity(String city) {
		return this.parkingDAO.getParkingSpaceByCity(city);
	}
	
	public ParkingSpace getParkingSpaceById(int id) {
		return this.parkingDAO.getParkingSpaceById(id);
	}
}
