package in.ketkikule.webtools.ParkIt.configuration;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {
	private static HibernateConfiguration me;
    private Configuration cfg;
    private SessionFactory sessionFactory;

    private HibernateConfiguration() throws HibernateException {
    	cfg = new Configuration();
        // build the config
        sessionFactory = cfg.configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public static synchronized HibernateConfiguration getInstance() throws HibernateException {
        if (me == null) {
            me = new HibernateConfiguration();
        }

        return me;
    }

    public Session getSession() throws HibernateException {
        Session session = sessionFactory.openSession();
        if (!session.isConnected()) {
            this.reconnect();
        }
        return session;
    }

    private void reconnect() throws HibernateException {
        this.sessionFactory = cfg.buildSessionFactory();
    }
}
