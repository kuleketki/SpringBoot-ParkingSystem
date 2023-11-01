package in.ketkikule.webtools.ParkIt.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.ketkikule.webtools.ParkIt.exception.BookingException;
import in.ketkikule.webtools.ParkIt.exception.ParkingSpaceException;
import in.ketkikule.webtools.ParkIt.model.Booking;
import in.ketkikule.webtools.ParkIt.model.User;
import in.ketkikule.webtools.ParkIt.service.BookingService;
import in.ketkikule.webtools.ParkIt.service.UserService;

@RestController
@RequestMapping("/")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private UserService userService;

	@PostMapping("/bookings")
	@ResponseBody
	public ResponseEntity<Object> createBooking(@RequestBody Booking booking) throws BookingException {
		Booking newBooking = bookingService.createNewBooking(booking);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Successfull");
		map.put("status", HttpStatus.OK);
		map.put("data", newBooking);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@GetMapping("/bookings/{userid}")
	public ResponseEntity<Object> getBookingsOfUser(@PathVariable("userid") String userid) throws BookingException {
		User user = userService.getUserById(Integer.parseInt(userid));
		List<Booking> bookings = bookingService.getBookingsByUserId(userid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Successfull");
		map.put("status", HttpStatus.OK);
		map.put("data", bookings);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@PutMapping("/bookings")
	@ResponseBody
	public ResponseEntity<Object> updateBooking(@RequestBody Booking booking) throws BookingException{
		Booking oldBooking = bookingService.getBookingById(booking.getId());
		oldBooking.setFromDate(booking.getFromDate());
		oldBooking.setToDate(booking.getToDate());

		Booking newBooking = bookingService.updateBooking(booking);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Successfull");
		map.put("status", HttpStatus.OK);
		map.put("data", newBooking);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@DeleteMapping("/bookings/{bookingid}")
	public ResponseEntity<Object> deleteBookingById(@PathVariable("bookingid") String bookingId) throws BookingException{
		bookingService.deleteBooking(Integer.parseInt(bookingId));
		Booking bookings = bookingService.getBookingById(Integer.parseInt(bookingId));
		if (bookings == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "Successfull");
			map.put("status", HttpStatus.OK);
			map.put("data", bookings);
			return new ResponseEntity<Object>(map, HttpStatus.OK);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "Failure");
			map.put("status", HttpStatus.NOT_MODIFIED);
			return new ResponseEntity<Object>(map, HttpStatus.NOT_MODIFIED);
		}
	}
}
