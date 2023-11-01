package in.ketkikule.webtools.ParkIt.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import in.ketkikule.webtools.ParkIt.configuration.HibernateConfiguration;
import in.ketkikule.webtools.ParkIt.model.Credentials;
import in.ketkikule.webtools.ParkIt.model.User;
@Service
public class UserDAO {
	public User saveUser(User user) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			System.out.println("session : " + session);
			transaction = session.beginTransaction();
			session.merge(user);
			transaction.commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean doesUserExists(String username) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("userid", username));
			return cr.list().size() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public User getUserByUsername(String username) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("userid", username));
			return (User) cr.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User areCredentialsValid(Credentials credentials) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("userid", credentials.getUserid()));
			User user = (User) cr.list().get(0);
			if(user.getPassword().equals(credentials.getPassword())) {
				return user;
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User getUserById(int id) {
		Session session = null;
		try {
			session = HibernateConfiguration.getInstance().getSession();
			User user = (User) session.get(User.class, id);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
