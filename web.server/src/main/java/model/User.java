package model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name="User")
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @ManyToOne
    @JoinColumn(name = "user_discount")
    private Discount discount;

    public User() {
    }

    public void addDiscountToUser(final Discount discount) {
        this.setDiscount(discount);
        discount.addUser(this);
    }

    public void removeDiscountToUser(final Discount discount) {
        this.setDiscount(null);
        discount.removeUser(this);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(discount, user.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, email, discount);
    }

    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
