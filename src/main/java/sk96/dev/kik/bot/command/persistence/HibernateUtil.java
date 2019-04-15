package sk96.dev.kik.bot.command.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sk96.dev.kik.bot.utils.logging.Logger;

import java.util.List;
import java.util.Map;

public class HibernateUtil {
    private static final Logger L = Logger.getTextFileLogger("HibernateUtil");
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static List<?> queryDatabase(String sql, Map<String, Object> params) {
        final Session session = sessionFactory.isClosed() ? sessionFactory.openSession() : sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            final Query query = session.createQuery(sql);
            if (params != null) {
                params.forEach(query::setParameter);
            }
            return query.list();
        } finally {
            session.getTransaction().commit();
        }
    }

    public void rawQuery(String query) {
        final Session session = sessionFactory.isClosed() ? sessionFactory.openSession() : sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery(query);
        q.executeUpdate();
    }

    public static void save(Object obj) {
        final Session session = sessionFactory.isClosed() ? sessionFactory.openSession() : sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.save(obj);
        } finally {
            session.getTransaction().commit();
        }
    }

    public static void delete(Object obj) {
        final Session session = sessionFactory.isClosed() ? sessionFactory.openSession() : sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.delete(obj);
        } finally {
            session.getTransaction().commit();
        }
    }

    public static boolean open() {
        boolean success = false;
        try {
            final Session session = sessionFactory.isClosed() ? sessionFactory.openSession() : sessionFactory.getCurrentSession();
            if(null != session) {
                session.beginTransaction();
                if(session.isOpen()) {
                    success = true;
                }
            }
        } catch (Exception e) {
            L.error("Failed to open connection to database: " + e.getMessage());
        }
        return success;
    }
}
