package dao;

import model.Warehouse;
import org.hibernate.Session;

import java.util.List;

/**
 WarehouseDao provides CRUD operations for {@link Warehouse}
 */

public class WarehouseDAO extends DAO<Warehouse> {

    public WarehouseDAO() {
        this.setModelClass(Warehouse.class);
    }

    @SuppressWarnings("unchecked")
    public List<Warehouse> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Warehouse> result = session.createQuery("select w from Warehouse w " +
                "left join fetch w.products").list();
        session.getTransaction().commit();
        return result;
    }

}
