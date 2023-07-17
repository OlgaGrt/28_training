package dao;

import model.Product;
import model.User;
import model.Warehouse;

import java.util.List;

/**
UserDao provides CRUD operations for {@link User}
 */

public interface UserDao {
    List<User> getAllUsers();
    User getUser(int userId);
    User addUser(User product, int discountId);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
    boolean addDiscountToUser(int discountId, int userId);
}
