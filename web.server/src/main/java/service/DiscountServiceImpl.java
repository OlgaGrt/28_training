package service;

import dao.DiscountDao;
import dao.DiscountDaoImpl;
import model.Discount;

import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    private final DiscountDao discountDao = new DiscountDaoImpl();

    @Override
    public List<Discount> getAllDiscounts() {
        return discountDao.getAllDiscounts();
    }

    @Override
    public Discount getDiscount(final int discountId) {
        return discountDao.getDiscount(discountId);
    }

    @Override
    public void addDiscount(final Discount discount) {
        discountDao.addDiscount(discount);
    }

    @Override
    public boolean updateDiscount(final Discount discount) {
        return discountDao.updateDiscount(discount);
    }

    @Override
    public boolean deleteDiscount(int discountId) {
        return discountDao.deleteDiscount(discountId);
    }
}
