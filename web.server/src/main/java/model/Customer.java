package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity(name = "Customer")
@Table(name = "customers")
public class Customer extends User{

    @Column(name = "registration_date")
    private Date registrationDate;

}
