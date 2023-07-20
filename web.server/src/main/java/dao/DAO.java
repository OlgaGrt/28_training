package dao;

import model.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import service.utils.HibernateUtil;

import java.util.List;

public abstract class DAO<T extends BaseEntity> {

    protected Class<T> modelClass;
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void setModelClass(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    public final T create(T obj) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        int id = (int) session.save(obj);
        session.getTransaction().commit();
        obj.setId(id);
        return obj;
    }

    public final void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        T obj = session.load(modelClass, id);
        session.delete(obj);
        session.getTransaction().commit();
    }

    public final void update(T obj) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(obj);
        session.getTransaction().commit();
    }

    public T getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        T result = session.get(modelClass, id);
        session.getTransaction().commit();
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<T> result = session.createQuery("from " +
                modelClass.getName()).list();
        session.getTransaction().commit();
        return result;
    }
}
