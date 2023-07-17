package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Discount {
    int discountId;
    String name;
    short percent;
    List<User> users;

    public Discount() {
        users = new ArrayList<>();
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getPercent() {
        return percent;
    }

    public void setPercent(short percent) {
        this.percent = percent;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return discountId == discount.discountId && percent == discount.percent && Objects.equals(name, discount.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountId, name, percent);
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountId=" + discountId +
                ", name='" + name + '\'' +
                ", percent=" + percent +
                '}';
    }
}
