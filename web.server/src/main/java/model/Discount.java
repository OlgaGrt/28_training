package model;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Discount")
@Table(name = "discounts")
public class Discount extends BaseEntity {

    @NaturalId
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "percent")
    private short percent;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, orphanRemoval = true)
    List<User> users = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public void addUser(final User user) {
        users.add(user);
        user.setDiscount(this);
    }

    public void removeUser(final User user) {
        users.remove(user);
        user.setDiscount(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return percent == discount.percent && Objects.equals(code, discount.code) && Objects.equals(name, discount.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, percent);
    }
}
