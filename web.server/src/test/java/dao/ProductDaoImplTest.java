package dao;

import model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import service.utils.HibernateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/* tests not by convention, just for practising and learning diff aspects of hibernate */
public class ProductDaoImplTest {

    static ProductDAO productDAO = new ProductDAO();

    @Before
    public void before() throws IOException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String schemaFilePath = "src/main/resources/initDB.sql";
        String schemaSQL = Files.readString(Paths.get(schemaFilePath));

        session.createNativeQuery(schemaSQL).executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void test1() {

        Product product = new Product();
        product.setName("name");
        product.setDescription("descp");
        product.setBarcode(123);
        productDAO.getAll();

    }

}
