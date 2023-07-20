package dao;

import model.Product;
import org.hibernate.Session;

import java.util.List;

/**
 * ProductDao provides CRUD operations for {@link Product}
 */

public class ProductDAO extends DAO<Product> {

    public ProductDAO() {
        this.setModelClass(Product.class);
    }

    @SuppressWarnings("unchecked")
    public List<Product> getWithBarcodeLessThan(int number) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> result = session.createQuery("FROM " + Product.class.getName() + " p " +
                "WHERE p.barcode < :number")
                .setCacheable(true)
                .setParameter("number",number)
                .getResultList();
        session.getTransaction().commit();
        return result;
    }

    public void addAll(final List<Product> products) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.setJdbcBatchSize(100);

        for (int i = 0; i < products.size(); i++) {
            session.save(products.get(i));
            if( i % 10 == 0 ) {
                session.flush();
                session.clear();
            }
        }
        session.getTransaction().commit();
    }
}
