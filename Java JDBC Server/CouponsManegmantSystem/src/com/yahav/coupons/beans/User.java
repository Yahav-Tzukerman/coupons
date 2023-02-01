package com.yahav.coupons.beans;

import com.yahav.coupons.enums.Status;
import com.yahav.coupons.enums.UserType;

import java.sql.Date;
import java.sql.Timestamp;

public class User {

    private int id;
    private String username;
    private String password;
    private UserType userType;
    private Integer companyId;
    private String firstName;
    private String lastName;
    private String phone;
    private Date joinDate;
    private int amountOfFailedLogins;
    private Status status;
    private Timestamp suspensionTimeStamp;

    public User() {
    }

    public User(int id, String username, String password, UserType userType, Integer companyId,
                String firstName, String lastName, String phone, Date joinDate,
                int amountOfFailedLogins, Status status, Timestamp suspensionTimeStamp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.companyId = companyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.joinDate = joinDate;
        this.amountOfFailedLogins = amountOfFailedLogins;
        this.status = status;
        this.suspensionTimeStamp = suspensionTimeStamp;
    }

    public User(String username, String password, String firstName, String lastName, UserType userType, String phone, Integer companyId) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.phone = phone;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public int getAmountOfFailedLogins() {
        return amountOfFailedLogins;
    }

    public void setAmountOfFailedLogins(int amountOfFailedLogins) {
        this.amountOfFailedLogins = amountOfFailedLogins;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getSuspensionTimeStamp() {
        return suspensionTimeStamp;
    }

    public void setSuspensionTimeStamp(Timestamp suspensionTimeStamp) {
        this.suspensionTimeStamp = suspensionTimeStamp;
    }

    public String getFullName() {
        return this.lastName + " " + this.firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", companyId=" + companyId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", joinDate=" + joinDate +
                ", amountOfFailedLogins=" + amountOfFailedLogins +
                ", status=" + status +
                ", suspensionTimeStamp=" + suspensionTimeStamp +
                '}';
    }
}
