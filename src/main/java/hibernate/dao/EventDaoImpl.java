package hibernate.dao;

import entities.Event;
import hibernate.util.HibernateSession;
import org.hibernate.Session;

import java.util.List;

public class EventDaoImpl implements IDao<Event> {

    public List<Event> findAll() {
        try(Session session = HibernateSession.getSession()) {
            return session.createQuery("From Event", Event.class).list();
        }
    }

    public Event findById(int id) {
        try(Session session = HibernateSession.getSession()) {
            return session.find(Event.class, id);
        }
    }

    public void save(Event entity) {
        try(Session session = HibernateSession.getSession()) {
            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    public void update(Event entity) {
        try(Session session = HibernateSession.getSession()) {
            session.getTransaction().begin();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    public void delete(Event entity) {
        try(Session session = HibernateSession.getSession()) {
            session.getTransaction().begin();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }
}
