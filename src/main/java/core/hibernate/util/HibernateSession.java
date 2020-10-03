package core.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateSession {
    private static SessionFactory sessionFactory;
    private static Transaction transaction;
    private static Session session;

    static {
        try {
            Configuration configuration = new Configuration().addResource("hibernate.cfg.xml");
            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public static Session getSessionWithTransaction() throws HibernateException {
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        return session;
    }

    public static void commit() {
        transaction.commit();
    }
}