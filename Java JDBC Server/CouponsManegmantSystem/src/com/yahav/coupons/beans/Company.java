package com.yahav.coupons.beans;

import java.sql.Date;

public class Company {
    private int id;
    private String name;
    private String phone;
    private Date foundationDate;
    private String country;
    private String city;
    private String address;

    public Company() {
    }

    public Company(int id, String name, String phone, Date foundationDate, String country, String city, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.foundationDate = foundationDate;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public Company(String name, String phone, Date foundationDate, String country, String city, String address) {
        this.name = name;
        this.phone = phone;
        this.foundationDate = foundationDate;
        this.country = country;
        this.city = city;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", foundationDate=" + foundationDate +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
