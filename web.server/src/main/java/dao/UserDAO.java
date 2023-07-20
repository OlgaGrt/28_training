package dao;

import model.User;
import org.hibernate.Session;

import java.util.List;

/**
 WarehouseDao provides CRUD operations for {@link User}
 */

public class UserDAO extends DAO<User> {

    public UserDAO() {
        this.setModelClass(User.class);
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> result = session.createQuery("select u from User u join fetch u.discount d").list();
        session.getTransaction().commit();
        return result;
    }
}
