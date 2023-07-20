package dao;

import model.Point;
import model.Product;
import model.Warehouse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import service.utils.HibernateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/* tests not by convention, just for practising and learning diff aspects of hibernate */
public class WarehouseDaoImplTest {

    WarehouseDAO warehouseDao = new WarehouseDAO();
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
        List<Warehouse> expected = new ArrayList<>();
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setName("test_name1");
        Point point = new Point(0.0f, 0.0f);
        warehouse1.setLocation(point);
        warehouseDao.create(warehouse1);
        expected.add(warehouse1);

        Warehouse warehouse2 = new Warehouse();
        warehouse2.setName("test_name2");
        warehouse2.setLocation(point);
        warehouseDao.create(warehouse2);
        expected.add(warehouse2);

        Product product = new Product();
        product.setName("name");
        product.setDescription("descp");
        product.setBarcode(123);
        productDAO.create(product);

        warehouse1.addProduct(product);
        warehouse2.addProduct(product);

        warehouseDao.update(warehouse1);
        warehouseDao.update(warehouse2);

        assertEquals(expected, warehouseDao.getAll());
    }

}
