package dao;

import exception.DiscountDaoException;
import model.Discount;
import model.User;
import model.Warehouse;
import org.junit.Before;
import org.junit.Test;

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

public class DiscountDaoImplTest {

    static String DB_URL = "jdbc:postgresql://localhost:5432/training_28_test";
    static String DB_USERNAME = "olgagrts";
    static String DB_PASSWORD = "qwerty007";
    DiscountDao discountDao = new DiscountDaoImpl(DB_URL, DB_USERNAME, DB_PASSWORD);
    UserDao userDao = new UserDaoImpl(DB_URL, DB_USERNAME, DB_PASSWORD);

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
    public void get_all_discounts_test() {
        List<Discount> expected = new ArrayList<>();
        Discount discount1 = new Discount();
        discount1.setName("1_test_name");
        discount1.setPercent((short) 20);
        discountDao.addDiscount(discount1);
        expected.add(discount1);

        Discount discount2 = new Discount();
        discount2.setName("2_test_name");
        discount2.setPercent((short) 70);
        discountDao.addDiscount(discount2);
        expected.add(discount2);

        assertEquals(expected, discountDao.getAllDiscounts());
    }

    @Test
    public void add_discount_test() {
        Discount discount = new Discount();
        discount.setName("test_name");
        discount.setPercent((short) 20);
        assertNotNull(discountDao.addDiscount(discount));
    }

    @Test(expected = DiscountDaoException.class)
    public void add_not_initialized_discount_ref_test() {
        Discount discount = new Discount();
        discountDao.addDiscount(discount);
    }


    @Test
    public void add_discount_and_then_get_by_id() {
        Discount discount = new Discount();
        discount.setName("test_name");
        discount.setPercent((short) 20);
        discountDao.addDiscount(discount);

        assertEquals(discount, discountDao.getDiscount(discount.getDiscountId()));
    }

    @Test
    public void get_users_for_discount_test() {
        Discount discount = new Discount();
        discount.setName("discountName");
        discount.setPercent((short) 20);
        discountDao.addDiscount(discount);

        User user1 = new User();
        user1.setUserName("userName1");
        user1.setEmail("userEmail1");
        user1.setPassword("userPassword");
        userDao.addUser(user1, discount.getDiscountId());

        User user2 = new User();
        user2.setUserName("userName2");
        user2.setEmail("userEmail2");
        user2.setPassword("userPassword");
        userDao.addUser(user2, discount.getDiscountId());

        assertEquals(2, discountDao.getUserForDiscount(discount.getDiscountId()).size());
    }

    @Test
    public void delete_discount_test() {
        Discount discount = new Discount();
        discount.setName("discountName");
        discount.setPercent((short) 20);
        discountDao.addDiscount(discount);
        assertTrue(discountDao.deleteDiscount(discount.getDiscountId()));
     }

     @Test(expected = DiscountDaoException.class)
    public void delete_incorrect_discount_id_test() {
        assertTrue(discountDao.deleteDiscount(0));
    }

    @Test
    public void update_discount_test() {
        String changed_name = "changed_name";
        Discount discount = new Discount();
        discount.setName("discountName");
        discount.setPercent((short) 20);
        discountDao.addDiscount(discount);

        discount.setName(changed_name);

        assertTrue(discountDao.updateDiscount(discount));
        assertEquals(discountDao.getDiscount(discount.getDiscountId()).getName(), changed_name);
    }


}
