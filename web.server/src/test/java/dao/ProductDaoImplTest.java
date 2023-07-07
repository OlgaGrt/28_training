package dao;

import exception.ProductDaoException;
import model.Product;
import model.Warehouse;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.geometric.PGpoint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDaoImplTest {

    static String DB_URL = "jdbc:postgresql://localhost:5432/training_28_test";
    static String DB_USERNAME = "olgagrts";
    static String DB_PASSWORD = "qwerty007";
    ProductDao productDao = new ProductDaoImpl(DB_URL, DB_USERNAME, DB_PASSWORD);
    WarehouseDao warehouseDao = new WarehouseDaoImpl(DB_URL, DB_USERNAME, DB_PASSWORD);


    @Before
    public void setup() throws IOException {

        String schemaFilePath = "src/main/resources/initDB.sql";
        String schemaSQL = Files.readString(Paths.get(schemaFilePath));

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(schemaSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get_all_products_test() {
        List<Product> expected = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("1_test_name");
        product1.setDescription("1_test_description");
        productDao.addProduct(product1);
        expected.add(product1);

        Product product2 = new Product();
        product2.setName("2_test_name");
        product2.setDescription("2_test_description");
        productDao.addProduct(product2);
        expected.add(product2);

        assertEquals(expected, productDao.getAllProduct());
    }

    @Test
    public void add_product_test() {
        Product product = new Product();
        product.setName("test_name");
        product.setDescription("test_description");
        assertNotNull(productDao.addProduct(product));
    }

    @Test(expected = ProductDaoException.class)
    public void add_not_initialized_product_ref_test() {
        Product product = new Product();
        productDao.addProduct(product);
    }

    @Test
    public void add_product_and_then_delete_it_test() {
        Product product = new Product();
        product.setName("test_name");
        product.setDescription("test_description");
        product = productDao.addProduct(product);
        assertTrue(productDao.deleteProduct(product.getProductId()));
    }

    @Test(expected = ProductDaoException.class)
    public void delete_product_with_not_existing_id_test() {
        assertTrue(productDao.deleteProduct(0));
    }

    @Test
    public void update_product_test() {
        String changed_description = "changed_description";
        Product product = new Product();
        product.setName("test_name");
        product.setDescription("test_description");
        product = productDao.addProduct(product);

        product.setDescription(changed_description);
        assertTrue(productDao.updateProduct(product));
        assertEquals(productDao.getProduct(product.getProductId()).getDescription(), changed_description);
    }

    @Test
    public void get_available_warehouses_by_product_id_test() {
        Product product = new Product();
        product.setName("test_name");
        product.setDescription("test_description");
        product = productDao.addProduct(product);

        Warehouse warehouse = new Warehouse();
        warehouse.setName("Luban");
        warehouse.setPoint(new PGpoint(-52.47, 28.00));
        warehouse = warehouseDao.addWarehouse(warehouse);

        boolean expected = productDao.addWarehouseToProduct(product.getProductId(), warehouse.getWarehouseId());
        assertTrue(expected);
    }


}
