package in.ketkikule.webtools.ParkIt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ketkikule.webtools.ParkIt.dao.BookingDAO;
import in.ketkikule.webtools.ParkIt.dao.ParkingSpaceDAO;
import in.ketkikule.webtools.ParkIt.model.Booking;
import in.ketkikule.webtools.ParkIt.model.ParkingSpace;
import in.ketkikule.webtools.ParkIt.model.User;
@Service
public class BookingService {
	@Autowired
	private BookingDAO bookingDAO;
	
	public Booking createNewBooking(Booking booking) {
		return this.bookingDAO.createBooking(booking);
	}

	public List<Booking> getBookingsByUserId(String id) {
		return this.bookingDAO.getBookingsByUserId(Integer.parseInt(id));
	}
	
	public Booking getBookingById(int id) {
		return this.bookingDAO.getBookingById(id);
	}
	
	public Booking updateBooking(Booking booking) {
		return this.bookingDAO.updateBooking(booking);
	}
	
	public void deleteBooking(int bookingId) {
		this.bookingDAO.removeBookingById(bookingId);
	}
}
