package com.example.users.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class Address {

    @Size(min=5, max=5, message = "{Size.zipcode}")
    private String zipcode;

    @Size(min=2, max=30, message = "{Size.city}")
    private String city;

    @Size(min=2, max=30, message = "{Size.street}")
    private String street;

    @Size(min=1, max=4, message = "{Size.building}")
    private String building;

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

}
