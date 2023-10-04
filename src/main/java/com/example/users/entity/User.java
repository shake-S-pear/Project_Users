package com.example.users.entity;

import com.example.users.validator.ValidEmail;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "{NotEmpty.firstName}")
    @Size(min=2, max=30, message = "{Size.firstName}")
    private String firstname;

    @NotEmpty(message = "{NotEmpty.lastName}")
    @Size(min=2, max=30, message = "{Size.lastName}")
    private String lastname;

    @Past(message = "{Past.birthdate}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "{NotNull.birthdate}")
    private Date birthdate;

    @NotEmpty(message = "{NotEmpty.email}")
    @ValidEmail(message="{ValidEmail.email}")
    private String email;

    @Nullable
    private Address address;

    @Nullable
    private String telephone;

    public User() {
    }

    public User(int id, String firstname, String lastname, Date birthdate, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.email = email;
    }

    public User(int id, String firstname, String lastname, Date birthdate, String email, Address address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.email = email;
        this.address = address;
    }

    public User(int id, String firstname, String lastname, Date birthdate, String email, String telephone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.email = email;
        this.telephone = telephone;
    }

    public User(int id, String firstname, String lastname, Date birthdate, String email, Address address, String telephone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
