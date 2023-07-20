package service;

import dao.DiscountDAO;
import model.Discount;

import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    private final DiscountDAO discountDAO = new DiscountDAO();

    @Override
    public List<Discount> getAllDiscounts() {
        return discountDAO.getAll();
    }

    @Override
    public Discount getDiscount(final int discountId) {
        return discountDAO.getById(discountId);
    }

    @Override
    public void addDiscount(final Discount discount) {
        discountDAO.create(discount);
    }

    @Override
    public void updateDiscount(final Discount discount) {
        discountDAO.update(discount);
    }

    @Override
    public void deleteDiscount(final int discountId) {
        discountDAO.delete(discountId);
    }
}
