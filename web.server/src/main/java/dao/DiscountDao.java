package dao;

import model.Discount;
import model.User;

import java.util.List;

/**
 DiscountDao provides CRUD operation for {@link Discount}
 */

public interface DiscountDao {
    List<Discount> getAllDiscounts();
    Discount getDiscount(int discountId);
    Discount addDiscount(Discount discount);
    boolean updateDiscount(Discount discount);
    boolean deleteDiscount(int discountId);
    List<User> getUserForDiscount(int discountId);
}
