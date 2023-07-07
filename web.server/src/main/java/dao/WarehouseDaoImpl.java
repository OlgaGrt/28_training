package dao;

import exception.ProductDaoException;
import exception.WarehouseDaoException;
import model.Product;
import model.Warehouse;
import org.postgresql.geometric.PGpoint;
import utils.Properties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ModelsMappingUtils.mapProduct;
import static dao.ModelsMappingUtils.mapWarehouse;


public class WarehouseDaoImpl implements WarehouseDao {

    static void initDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String SQL_UPDATE_WAREHOUSE = "UPDATE WAREHOUSES SET NAME=?, LOCATION=? WHERE WAREHOUSE_ID=?";
    private static final String SQL_INSERT_WAREHOUSE = "INSERT INTO WAREHOUSES (NAME, LOCATION) VALUES (?,?)";
    private static final String SQL_DELETE_WAREHOUSE = "DELETE FROM WAREHOUSES WHERE WAREHOUSE_ID=?";
    private static final String SQL_SELECT_WAREHOUSE_BY_WAREHOUSE_ID = "SELECT WAREHOUSE_ID, NAME, LOCATION FROM WAREHOUSES WHERE WAREHOUSE_ID=?";
    private static final String SQL_SELECT_All_WAREHOUSES = "SELECT * FROM WAREHOUSES";
    private static final String SQL_SELECT_PRODUCTS_BY_WAREHOUSE_ID = "SELECT p.product_id, p.name, description FROM products p " +
            "JOIN product_warehouse pw on p.product_id = pw.warehouse_id " +
            "JOIN warehouses w on pw.product_id = w.warehouse_id where w.warehouse_id=?";

    private String dbURL;
    private String username;
    private String password;

    public WarehouseDaoImpl() {
        initDriver();
        dbURL = Properties.dbURL;
        username = Properties.username;
        password = Properties.password;
    }

    public WarehouseDaoImpl(String dbURL, String username, String password) {
        initDriver();
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_All_WAREHOUSES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Warehouse> result = new ArrayList<>();
            while (resultSet.next()) {
                int warehouseId = resultSet.getInt("warehouse_id");
                String name = resultSet.getString("name");
                PGpoint geom = (PGpoint) resultSet.getObject("location");
                Warehouse warehouse = mapWarehouse(warehouseId, name, geom);
                List<Product> products = findProductsForWarehouse(warehouseId);
                warehouse.setProducts(products);
                result.add(warehouse);
            }
            return result;
        } catch (Exception e) {
            throw new ProductDaoException(e.getMessage());
        }
    }

    @Override
    public Warehouse getWarehouse(int warehouseId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password); PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_WAREHOUSE_BY_WAREHOUSE_ID)) {
            preparedStatement.setInt(1, warehouseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Warehouse warehouse = new Warehouse();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                PGpoint geom = (PGpoint) resultSet.getObject("location");
                warehouse = mapWarehouse(warehouseId, name, geom);
            }
            return warehouse;
        } catch (Exception e) {
            throw new WarehouseDaoException(e.getMessage());
        }
    }

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_WAREHOUSE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, warehouse.getName());
            preparedStatement.setObject(2, warehouse.getPoint());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating warehouse failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    warehouse.setWarehouseId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating warehouse failed, no ID obtained.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new WarehouseDaoException(e.getMessage());
        }
        return warehouse;
    }

    @Override
    public boolean updateWarehouse(Warehouse warehouse) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE_WAREHOUSE)) {
            preparedStatement.setString(1, warehouse.getName());
            preparedStatement.setObject(2, warehouse.getPoint());
            preparedStatement.setInt(3, warehouse.getWarehouseId());
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new SQLException("Updating warehouse failed, no rows affected.");
            }
            return true;
        } catch (Exception e) {
            throw new WarehouseDaoException(e.getMessage());
        }
    }

    @Override
    public boolean deleteWarehouse(int warehouseId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE_WAREHOUSE)) {
            preparedStatement.setInt(1, warehouseId);
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new SQLException("Deleting warehouse failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            throw new WarehouseDaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> findProductsForWarehouse(int warehouseId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password); PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_PRODUCTS_BY_WAREHOUSE_ID)) {
            preparedStatement.setInt(1, warehouseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("name");
                String description = resultSet.getString("description");

                Product product = mapProduct(productId, productName, description);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new ProductDaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
