package dao;

import exception.WarehouseDaoException;
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

public class WarehouseDaoImplTest {

    static String DB_URL = "jdbc:postgresql://localhost:5432/training_28_test";
    static String DB_USERNAME = "olgagrts";
    static String DB_PASSWORD = "qwerty007";
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

        List<Warehouse> expected = new ArrayList<>();
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setName("test_name1");
        PGpoint pGpoint = new PGpoint(0.0, 0.0);
        warehouse1.setPoint(pGpoint);
        warehouseDao.addWarehouse(warehouse1);
        expected.add(warehouse1);

        Warehouse warehouse2 = new Warehouse();
        warehouse2.setName("test_name2");
        warehouse2.setPoint(pGpoint);
        warehouseDao.addWarehouse(warehouse2);
        expected.add(warehouse2);

        assertEquals(expected, warehouseDao.getAllWarehouses());
    }

    @Test
    public void add_warehouse_test() {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("test_name");
        PGpoint pGpoint = new PGpoint(0.0, 0.0);
        warehouse.setPoint(pGpoint);
        assertNotNull(warehouseDao.addWarehouse(warehouse));
    }

    @Test(expected = WarehouseDaoException.class)
    public void add_not_initialized_warehouse_ref_test() {
        Warehouse warehouse = new Warehouse();
        warehouseDao.addWarehouse(warehouse);
    }


    @Test(expected = WarehouseDaoException.class)
    public void delete_product_with_not_existing_id_test() {
        assertTrue(warehouseDao.deleteWarehouse(0));
    }

    @Test
    public void update_warehouse_test() {
        String changed_name = "changed_name";
        Warehouse warehouse = new Warehouse();
        warehouse.setName("test_name");
        PGpoint pGpoint = new PGpoint(0.0, 0.0);
        warehouse.setPoint(pGpoint);
        warehouse = warehouseDao.addWarehouse(warehouse);
        warehouse.setName(changed_name);
        assertTrue(warehouseDao.updateWarehouse(warehouse));
        assertEquals(warehouseDao.getWarehouse(warehouse.getWarehouseId()).getName(), changed_name);
    }

}
