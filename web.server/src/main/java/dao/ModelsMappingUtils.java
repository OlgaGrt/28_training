package dao;

import model.Discount;
import model.Product;
import model.User;
import model.Warehouse;
import org.postgresql.geometric.PGpoint;

public class ModelsMappingUtils {

    static Warehouse mapWarehouse(int warehouseId, String name, PGpoint geom) {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId(warehouseId);
        warehouse.setName(name);
        warehouse.setPoint(geom);
        return warehouse;
    }

    static Product mapProduct(int productId, String name, String description) {
        Product product = new Product();
        product.setProductId(productId);
        product.setName(name);
        product.setDescription(description);
        return product;
    }

    static Discount mapDiscount(int discountId, String name, short percent) {
        Discount discount = new Discount();
        discount.setDiscountId(discountId);
        discount.setName(name);
        discount.setPercent(percent);
        return discount;
    }

    static User mapUser(int userId, String userName, String email) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setEmail(email);
        return user;
    }
}
