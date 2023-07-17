package dao;

import exception.ProductDaoException;
import model.Product;
import model.Warehouse;
import org.postgresql.geometric.PGpoint;
import utils.Properties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ModelsMappingUtils.mapProduct;
import static dao.ModelsMappingUtils.mapWarehouse;

public class ProductDaoImpl implements ProductDao {

    static void initDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String SQL_INSERT_PRODUCT = "INSERT INTO PRODUCTS (NAME, DESCRIPTION) VALUES (?,?)";
    private static final String SQL_DELETE_PRODUCT = "DELETE FROM PRODUCTS WHERE PRODUCT_ID=?";
    private static final String SQL_SELECT_All_PRODUCTS = "SELECT * FROM PRODUCTS";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE PRODUCTS SET NAME=?, DESCRIPTION=? WHERE PRODUCT_ID=?";
    private static final String SQL_SELECT_PRODUCT_BY_PRODUCT_ID = "SELECT PRODUCT_ID, NAME, DESCRIPTION FROM PRODUCTS WHERE PRODUCT_ID=?";
    private static final String SQL_SELECT_AVAILABLE_WAREHOUSES_BY_PRODUCT_ID = "SELECT w.warehouse_id, w.name as wName, w.location, p.product_id, p.name as pName, p.description" +
            " FROM warehouses w " +
            "JOIN product_warehouse pw on w.warehouse_id = pw.warehouse_id " +
            "JOIN products p on pw.product_id = p.product_id where p.product_id=?";
    private static final String SQL_ADD_WAREHOUSE_TO_PRODUCT = "INSERT INTO product_warehouse(product_id, warehouse_id) VALUES (?, ?)";

    private final String dbURL;
    private final String username;
    private final String password;

    public ProductDaoImpl() {
        initDriver();
        dbURL = Properties.dbURL;
        username = Properties.username;
        password = Properties.password;
    }

    public ProductDaoImpl(String dbURL, String username, String password) {
        initDriver();
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Product> getAllProduct() {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_All_PRODUCTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> result = new ArrayList<>();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Product product = mapProduct(productId, name, description);
                product.setWarehouses(getAvailableWarehousesForProduct(productId));
                result.add(product);
            }
            return result;
        } catch (Exception e) {
            throw new ProductDaoException(e.getMessage());
        }
    }

    @Override
    public Product getProduct(final int productId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password); PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_PRODUCT_BY_PRODUCT_ID)) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = new Product();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                product = mapProduct(productId, name, description);
                product.setWarehouses(getAvailableWarehousesForProduct(productId));
            }
            return product;
        } catch (Exception e) {
            throw new ProductDaoException(e.getMessage());
        }
    }

    @Override
    public Product addProduct(final Product product) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new ProductDaoException(e.getMessage());
        }
        return product;
    }

    @Override
    public boolean updateProduct(final Product product) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getProductId());
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
            return true;
        } catch (Exception e) {
            throw new ProductDaoException(e.getMessage());
        }
    }

    @Override
    public boolean deleteProduct(final int productId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE_PRODUCT)) {
            preparedStatement.setInt(1, productId);
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new SQLException("Deleting product failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            throw new ProductDaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Warehouse> getAvailableWarehousesForProduct(int productId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password); PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_AVAILABLE_WAREHOUSES_BY_PRODUCT_ID)) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Warehouse> warehouses = new ArrayList<>();
            while (resultSet.next()) {
                int warehouseId = resultSet.getInt("warehouse_id");
                String name = resultSet.getString("wName");
                PGpoint geom = (PGpoint) resultSet.getObject("location");

                String productName = resultSet.getString("pName");
                String description = resultSet.getString("description");
                Warehouse warehouse = mapWarehouse(warehouseId, name, geom);
                List<Product> products = warehouse.getProducts();
                Product product = mapProduct(productId, productName, description);
                products.add(product);

                warehouse.setProducts(products);
                warehouses.add(warehouse);
            }
            return warehouses;
        } catch (SQLException e) {
            throw new ProductDaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addWarehouseToProduct(int productId, int warehouseId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_ADD_WAREHOUSE_TO_PRODUCT)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, warehouseId);
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new SQLException("Add warehouse to product failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            throw new ProductDaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
