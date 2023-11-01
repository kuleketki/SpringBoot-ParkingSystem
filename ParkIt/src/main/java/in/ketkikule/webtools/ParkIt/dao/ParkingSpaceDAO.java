package in.ketkikule.webtools.ParkIt.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import in.ketkikule.webtools.ParkIt.configuration.HibernateConfiguration;
import in.ketkikule.webtools.ParkIt.model.ParkingSpace;
import in.ketkikule.webtools.ParkIt.model.User;


@Service
public class ParkingSpaceDAO {
	public ParkingSpace saveParkingSpace(ParkingSpace parkingspace) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			System.out.println("session : " + session);
			transaction = session.beginTransaction();
			session.save(parkingspace);
			transaction.commit();
			return parkingspace;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ParkingSpace> getParkingSpacesByUserId(int userid) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			//Query query = session.createQuery("FROM parkingspace WHERE userid=:userid ");
			Query query = session.createQuery("FROM Parkingspace WHERE user_id=:userid");
			query.setInteger("userid", userid);
			List<ParkingSpace> schedules = (List<ParkingSpace>) query.list();
			return schedules;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ParkingSpace> getParkingSpaceByCity(String city) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			Criteria cr = session.createCriteria(ParkingSpace.class);
			cr.add(Restrictions.eq("city", city));
			List<ParkingSpace> schedules = (List<ParkingSpace>) cr.list();
			return schedules;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ParkingSpace getParkingSpaceById(int id) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			ParkingSpace parkingSpace = (ParkingSpace) session.get(ParkingSpace.class, id);
			return parkingSpace;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
