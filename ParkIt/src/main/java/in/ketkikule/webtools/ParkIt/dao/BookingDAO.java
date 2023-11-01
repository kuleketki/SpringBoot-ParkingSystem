package in.ketkikule.webtools.ParkIt.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import in.ketkikule.webtools.ParkIt.configuration.HibernateConfiguration;
import in.ketkikule.webtools.ParkIt.model.Booking;

@Service
public class BookingDAO {
	
	public Booking createBooking(Booking booking) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			transaction = session.beginTransaction();
			session.save(booking);
			transaction.commit();
			return booking;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Booking> getBookingsByUserId(int userid) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			Criteria cr = session.createCriteria(Booking.class);
			cr.add(Restrictions.eq("userId", userid));
			List<Booking> bookings = (List<Booking>) cr.list();
			return bookings;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Booking getBookingById(int id) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			Booking booking = (Booking) session.get(Booking.class, id);
			return booking;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Booking updateBooking(Booking booking) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(booking);
			transaction.commit();
			return booking;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void removeBookingById(int bookingId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			Booking booking = (Booking) session.get(Booking.class, bookingId);
			transaction = session.beginTransaction();
			session.delete(booking);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
