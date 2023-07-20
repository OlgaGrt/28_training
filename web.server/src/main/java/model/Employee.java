package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity(name = "Employee")
@Table(name = "employees")
public class Employee extends User {

    @Column(name = "salary")
    private int salary;

    @Column(name = "hiring_date")
    private Date hiringDate;

    public Employee() {
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", hiringDate=" + hiringDate +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
