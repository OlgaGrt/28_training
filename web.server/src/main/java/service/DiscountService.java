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
    void updateDiscount(Discount discount);
    void deleteDiscount(int discountId);
}
