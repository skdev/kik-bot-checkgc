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
        final Session session = sessionFactory.getCurrentSession();
        if(!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        final Query query = session.createQuery(sql);
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.list();
    }

    public static void save(Object obj) {
        final Session session = sessionFactory.getCurrentSession();
        if(!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        session.saveOrUpdate(obj);
    }

    public static void save(List<?> list) {
        final Session session = sessionFactory.getCurrentSession();
        if(!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        for(Object o : list) {
            session.evict(o);
            session.saveOrUpdate(o);
            session.evict(o);
        }
    }

    public static void delete(Object obj) {
        final Session session = sessionFactory.getCurrentSession();
        if(!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        session.evict(obj);
        session.delete(obj);
        session.evict(obj);
    }

    public static boolean open() {
        boolean success = false;
        try {
            final Session session = sessionFactory.getCurrentSession();
            if(null != session) {
                if(!session.getTransaction().isActive()) {
                    session.beginTransaction();
                }
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
