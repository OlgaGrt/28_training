package dao;

import model.Discount;
import model.Employee;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import service.utils.HibernateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;

import static org.junit.Assert.assertEquals;

/* tests not by convention, just for practising and learning diff aspects of hibernate */
public class UserDaoImplTest {

    private UserDAO userDAO = new UserDAO();
    DiscountDAO discountDAO = new DiscountDAO();

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

        Discount discount = new Discount();
        discount.setCode("122392");
        discount.setName("name");
        discount.setPercent((short) 1);

        discountDAO.create(discount);

        Discount discount2 = new Discount();
        discount2.setCode("122");
        discount2.setName("name2");
        discount2.setPercent((short) 2);

        discountDAO.create(discount2);

        Employee employee = new Employee();
        employee.setSalary(122);
        employee.setHiringDate(new Date(2022, 03, 03));
        employee.setEmail("llal2a");
        employee.setUserName("sd2mc");
        employee.setPassword("pass");
        employee.setDiscount(discount);
        userDAO.create(employee);

        Employee employee2 = new Employee();
        employee2.setSalary(122);
        employee2.setHiringDate(new Date(2022, 03, 03));
        employee2.setEmail("llaкl2кa");
        employee2.setUserName("sd2ккmc");
        employee2.setPassword("paккss");
        employee2.setDiscount(discount2);
        userDAO.create(employee2);

        User user = new User();
        user.setEmail("llalda");
        user.setUserName("sddmc");
        user.setPassword("padss");
        user.addDiscountToUser(discount);

        userDAO.create(user);

        assertEquals(3, userDAO.getAll().size());
    }

    @Test
    public void test2() {

        Discount discount = new Discount();
        discount.setCode("123");
        discount.setName("name");

        discountDAO.create(discount);

        User user = new User();
        user.setEmail("llala");
        user.setUserName("sdmc");
        user.setPassword("pass");
        user.addDiscountToUser(discount);

        userDAO.create(user);

    }

}
