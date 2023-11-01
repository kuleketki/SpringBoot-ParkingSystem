package in.ketkikule.webtools.ParkIt.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.ketkikule.webtools.ParkIt.exception.ParkingSpaceException;
import in.ketkikule.webtools.ParkIt.model.ParkingSpace;
import in.ketkikule.webtools.ParkIt.model.ParkingSpaceWrapper;
import in.ketkikule.webtools.ParkIt.model.User;
import in.ketkikule.webtools.ParkIt.service.ParkingSpaceService;
import in.ketkikule.webtools.ParkIt.service.UserService;

@RestController
@RequestMapping("/")
public class ParkingSpaceController {
	@Autowired
	private ParkingSpaceService parkingSpaceService;
	@Autowired
	private UserService userService;

	@PostMapping("/parkingspaces")
	@ResponseBody
	public ResponseEntity<Object> createParkingSpace(@ModelAttribute ParkingSpaceWrapper model)
			throws ParkingSpaceException, IOException, MessagingException {
		User user = this.userService.getUserById(Integer.parseInt(model.getUserid()));
		ParkingSpace details = new ParkingSpace();
		details.setUser(this.userService.getUserById(Integer.parseInt(model.getUserid())));
		details.setAddress(model.getAddress());
		details.setCity(model.getCity());
		details.setLatitude(model.getLatitude());
		details.setLongitude(model.getLongitude());
		details.setName(model.getName());
		details.setParkingEntrance(model.getParkingEntrance());
		details.setPhone(model.getPhone());
		details.setRate(model.getRate());
		details.setImageBytes(model.getImage().getBytes());
		details.setFileName(model.getImage().getOriginalFilename());

		user.getParkingspace().add(details);
		User updatedUser = userService.registerNewUser(user);
		// ParkingSpace newParkingSpace =
		// parkingSpaceService.registerParkingSpace(details);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Successfull");
		map.put("status", HttpStatus.OK);
		map.put("data", updatedUser.getParkingspace());
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@GetMapping("/parkingspaces/{userid}")
	public ResponseEntity<Object> getParkingSpaces(@PathVariable("userid") String userid) throws ParkingSpaceException {
		// List<ParkingSpace> parkingSpaces =
		// parkingSpaceService.getParkingSpaces(userid);
		User user = userService.getUserById(Integer.parseInt(userid));
		// List<ParkingSpace> parkingSpaces = user.getParkingspace();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Successfull");
		map.put("status", HttpStatus.OK);
		map.put("data", user);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@GetMapping("/search/{city}")
	public ResponseEntity<Object> searchParkingSpace(@PathVariable("city") String city) throws ParkingSpaceException {
		List<ParkingSpace> parkingSpaces = parkingSpaceService.getParkingSpaceByCity(city);

		// List<ParkingSpace> parkingSpaces = user.getParkingspace();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Successfull");
		map.put("status", HttpStatus.OK);
		map.put("data", parkingSpaces);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@GetMapping("/parkingsearch/{id}")
	public ResponseEntity<Object> searchParkingSpaceID(@PathVariable("id") String id) throws ParkingSpaceException {
		ParkingSpace parkingSpace = parkingSpaceService.getParkingSpaceById(Integer.parseInt(id));

		// List<ParkingSpace> parkingSpaces = user.getParkingspace();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Successfull");
		map.put("status", HttpStatus.OK);
		map.put("data", parkingSpace);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
}
