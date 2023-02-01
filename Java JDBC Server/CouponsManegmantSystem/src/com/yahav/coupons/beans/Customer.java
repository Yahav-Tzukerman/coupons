package com.yahav.coupons.beans;

import com.yahav.coupons.enums.Gender;
import com.yahav.coupons.enums.MaritalStatus;

import java.sql.Date;

public class Customer {
    private User user;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer amountOfKids;
    private Date birthDate;
    private String country;
    private String city;
    private String address;

    public Customer() {
    }

    public Customer(User user, Gender gender, MaritalStatus maritalStatus, Integer amountOfKids, Date birthDate, String country, String city, String address) {
        this.user = user;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.amountOfKids = amountOfKids;
        this.birthDate = birthDate;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(Integer amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "user=" + user +
                ", gender=" + gender +
                ", maritalStatus=" + maritalStatus +
                ", amountOfKids=" + amountOfKids +
                ", birthDate=" + birthDate +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
