package core.hibernate.dao;

import core.hibernate.util.HibernateSession;
import entity.dto.Event;
import org.hibernate.Session;

import java.util.List;

public class EventDaoImpl implements IEventDao {

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
        try(Session session = HibernateSession.getSessionWithTransaction()) {
            session.save(entity);
            HibernateSession.commit();
        }
    }

    public void save(List<Event> entity) {
        try(Session session = HibernateSession.getSessionWithTransaction()) {
            entity.forEach(session::save);
            HibernateSession.commit();
        }
    }

    public void update(Event entity) {
        try(Session session = HibernateSession.getSessionWithTransaction()) {
            session.update(entity);
            HibernateSession.commit();
        }
    }

    public void delete(Event entity) {
        try(Session session = HibernateSession.getSessionWithTransaction()) {
            session.delete(entity);
            HibernateSession.commit();
        }
    }
}
