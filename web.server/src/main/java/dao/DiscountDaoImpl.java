package dao;

import exception.DiscountDaoException;
import exception.ProductDaoException;
import model.Discount;
import model.Product;
import model.User;
import model.Warehouse;
import org.postgresql.geometric.PGpoint;
import utils.Properties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ModelsMappingUtils.*;

/**
 DiscountDao provides CRUD operation for {@link Discount}
 */

public class DiscountDaoImpl implements DiscountDao{

    private static final String SQL_SELECT_All_DISCOUNTS = "SELECT * FROM DISCOUNTS";
    private static final String SQL_INSERT_DISCOUNT= "INSERT INTO DISCOUNTS (NAME, PERCENT) VALUES (?,?)";
    private static final String SQL_SELECT_DISCOUNT_BY_DISCOUNT_ID = "SELECT DISCOUNT_ID, NAME, PERCENT FROM DISCOUNTS WHERE DISCOUNT_ID=?";
    private static final String SQL_SELECT_USERS_BY_DISCOUNT_ID = "SELECT * FROM users JOIN discounts on users.user_discount = discounts.discount_id and discount_id=?";
    private static final String SQL_DELETE_DISCOUNT = "DELETE FROM DISCOUNTS WHERE DISCOUNT_ID=?";
    private static final String SQL_UPDATE_DISCOUNT = "UPDATE DISCOUNTS SET NAME=?, PERCENT=? WHERE DISCOUNT_ID=?";

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

    public DiscountDaoImpl() {
        initDriver();
        dbURL = Properties.dbURL;
        username = Properties.username;
        password = Properties.password;
    }

    public DiscountDaoImpl(String dbURL, String username, String password) {
        initDriver();
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Discount> getAllDiscounts() {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_All_DISCOUNTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Discount> result = new ArrayList<>();
            while (resultSet.next()) {
                int discountId = resultSet.getInt("discount_id");
                String name = resultSet.getString("name");
                short percent = resultSet.getShort("percent");
                Discount discount = mapDiscount(discountId, name, percent);
                discount.setUsers(getUserForDiscount(discountId));
                result.add(discount);
            }
            return result;
        } catch (Exception e) {
            throw new ProductDaoException(e.getMessage());
        }
    }

    @Override
    public Discount getDiscount(final int discountId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password); PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_DISCOUNT_BY_DISCOUNT_ID)) {
            preparedStatement.setInt(1, discountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Discount discount = new Discount();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                short percent = resultSet.getShort("percent");
                discount = mapDiscount(discountId, name, percent);
            }
            discount.setUsers(getUserForDiscount(discountId));
            return discount;
        } catch (Exception e) {
            throw new DiscountDaoException(e.getMessage());
        }
    }

    @Override
    public Discount addDiscount(Discount discount) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_DISCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, discount.getName());
            preparedStatement.setShort(2, discount.getPercent());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating discount failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    discount.setDiscountId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating discount failed, no ID obtained.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new DiscountDaoException(e.getMessage());
        }
        return discount;
    }

    @Override
    public boolean updateDiscount(Discount discount) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE_DISCOUNT)) {
            preparedStatement.setString(1, discount.getName());
            preparedStatement.setShort(2, discount.getPercent());
            preparedStatement.setInt(3, discount.getDiscountId());
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new SQLException("Updating discount failed, no rows affected.");
            }
            return true;
        } catch (Exception e) {
            throw new DiscountDaoException(e.getMessage());
        }
    }

    @Override
    public boolean deleteDiscount(final int discountId) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE_DISCOUNT)) {
            preparedStatement.setInt(1, discountId);
            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                throw new SQLException("Deleting discount failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            throw new DiscountDaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getUserForDiscount(int discountId){
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbURL, username, password); PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_USERS_BY_DISCOUNT_ID)) {
            preparedStatement.setInt(1, discountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("username");
                String email = resultSet.getString("email");
                User user = mapUser(userId,  userName, email);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new ProductDaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Discount discount = getDiscount(discountId);
        users.forEach(x->x.setDiscount(discount));
        return users;
    }

}
