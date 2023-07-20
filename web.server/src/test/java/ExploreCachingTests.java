import dao.ProductDAO;
import model.Product;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.utils.HibernateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExploreCachingTests {

    ProductDAO productDAO = new ProductDAO();

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
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Product productFromDb = session.find(Product.class, 1);
        System.out.println(productFromDb);
        Cache secondLevelCache = session.getSessionFactory().getCache();

        session.clear();
        session.getTransaction().commit();

        Assert.assertTrue(secondLevelCache.containsEntity(Product.class, 1));
    }
}
