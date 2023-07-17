package service;

import model.Discount;

import java.util.List;

/**
 DiscountService implements business logic for {@link Discount}
 */
public interface DiscountService {
    List<Discount> getAllDiscounts();
    Discount getDiscount(int discountId);
    void addDiscount(Discount discount);
    boolean updateDiscount(Discount discount);
    boolean deleteDiscount(int discountId);
}
