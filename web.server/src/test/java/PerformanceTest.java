import dao.ProductDAO;
import model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import service.utils.HibernateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PerformanceTest {

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


    /*
    with index = 402
    without index = 529
     */
    @Test
    public void task1() {
        Random rand = new Random();
        int usersAmount = 1_000_000;
        int upperbound = 1_000_000;

        List<Product> products = new ArrayList<>(usersAmount);

        for (int i = 0; i < usersAmount; i++) {
            Product product = new Product();
            product.setName("name" + i);
            product.setDescription("descp");
            product.setBarcode(rand.nextInt(upperbound));
            products.add(product);
        }

        productDAO.addAll(products);

        LocalDateTime start = LocalDateTime.now();
        productDAO.getWithBarcodeLessThan(500);
        LocalDateTime end = LocalDateTime.now();
        long seconds = ChronoUnit.MILLIS.between(start, end);
        System.out.println(seconds);
    }

    /*
    before caching 416
    after 1
     */
    @Test
    public void task2() {
        LocalDateTime start = LocalDateTime.now();
        productDAO.getWithBarcodeLessThan(500);
        LocalDateTime end = LocalDateTime.now();
        long seconds = ChronoUnit.MILLIS.between(start, end);
        System.out.println(seconds);

        LocalDateTime start1 = LocalDateTime.now();
        productDAO.getWithBarcodeLessThan(500);
        LocalDateTime end1 = LocalDateTime.now();
        long seconds2 = ChronoUnit.MILLIS.between(start1, end1);
        System.out.println(seconds2);



    }

}
