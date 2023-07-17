package dao;

import exception.ProductDaoException;
import exception.UserDaoException;
import model.Product;
import model.User;
import model.Warehouse;
import org.postgresql.geometric.PGpoint;
import utils.Properties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ModelsMappingUtils.mapProduct;
import static dao.ModelsMappingUtils.mapWarehouse;

public class UserDaoImpl implements UserDao {

    private static final String SQL_INSERT_USER = "INSERT INTO USERS (USERNAME, EMAIL, PASSWORD, USER_DISCOUNT) VALUES (?,?,?,?)";

    static void initDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final String dbURL;
    private final String username;
    private final String password;

    public UserDaoImpl() {
        initDriver();
        dbURL = Properties.dbURL;
        username = Properties.username;
        password = Properties.password;
    }

    public UserDaoImpl(String dbURL, String username, String password) {
        initDriver();
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
    }


    @Override
    public List<User> getAllUsers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getUser(int userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User addUser(User user, int discountId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, discountId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new UserDaoException(e.getMessage());
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteUser(int userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addDiscountToUser(int discountId, int userId) {
        throw new UnsupportedOperationException();
    }
}
