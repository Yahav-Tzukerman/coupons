package com.yahav.coupons.logics;

import com.yahav.coupons.beans.Company;
import com.yahav.coupons.beans.User;
import com.yahav.coupons.dal.UsersDal;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.enums.Status;
import com.yahav.coupons.enums.UserType;
import com.yahav.coupons.exceptions.ServerException;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLogic {
    private static final UsersDal usersDal = new UsersDal();
    private static final CustomerLogic customerLogic = new CustomerLogic();
    private static final CompanyLogic companyLogic = new CompanyLogic();

    public UserLogic() {
    }

    public int updateUser(User user) throws ServerException {
        try {
            user.setId(usersDal.getId(user.getUsername()));
            validateUser(user);
            return usersDal.updateUser(user);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.UPDATE_ERROR, "Failed to update user: " + user.getUsername());
        }
    }

    public User getUser(String username) throws ServerException {
        int userId = usersDal.getId(username);
        return getUser(userId);
    }

    public User getUser(int userId) throws ServerException {
        try {
            return usersDal.getUser(userId);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get user");
        }
    }

    public List<User> getUsersByType(UserType userType) throws ServerException {
        try {
            return usersDal.getUsersByType(userType);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get users by type " + userType);
        }
    }

    public List<User> getUsers() throws ServerException {
        try {
            return usersDal.getUsers();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all users");
        }
    }

    public void clearContent() throws ServerException {
        try {
            usersDal.clearContent();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Failed to clear users content");
        }
    }

    public void deleteCompanyUsers(String companyName) throws ServerException {
        try {
            Company company = companyLogic.getCompany(companyName);
            if (usersDal.isCompanyUsersExists(company.getId())) {
                usersDal.deleteCompanyUsers(company.getId());
                System.out.println(companyName + " users was deleted successfully");
            }
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete users for company : " + companyName);
        }
    }

    public int deleteUser(String username) throws ServerException {
        int userId = usersDal.getId(username);
        if (userId == -1) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST, username);
        }
        return deleteUser(userId);
    }

    public int deleteUser(int userId) throws ServerException {
        String username = "";
        try {
            User user = usersDal.getUser(userId);
            if (user == null) {
                throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
            }
            username = user.getUsername();
            if (user.getUserType() == UserType.CUSTOMER) {
                if (customerLogic.getCustomer(username) != null) {
                    customerLogic.deleteCustomer(username);
                    return 1;
                }
            }
            return usersDal.deleteUser(userId);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete user: " + username);
        }
    }

    public int addUser(User user) throws ServerException {
        try {
            if (usersDal.isUserExists(user.getUsername())) {
                throw new ServerException(ErrorType.USERNAME_ALREADY_EXIST, "User: " + user.getUsername());
            }
            validateUser(user);
            user.setJoinDate(Date.valueOf(LocalDate.now()));
            user.setAmountOfFailedLogins(0);
            user.setStatus(Status.ACTIVE);
            user.setSuspensionTimeStamp(null);
            return usersDal.addUser(user);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.CREATION_ERROR, "Failed to add " + user.getUsername());
        }
    }

    public boolean login(String username, String password) throws ServerException {
        boolean login;
        User user = getUser(username);
        if (user == null) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
        }
        checkSuspension(user);

        if (user.getAmountOfFailedLogins() < 5) {
            login = usersDal.login(username, password);
            if (!login) {
                user.setAmountOfFailedLogins(user.getAmountOfFailedLogins() + 1);
                usersDal.updateUser(user);
                throw new ServerException(ErrorType.LOGIN_FAILURE, "User: " + username);
            } else {
                user.setAmountOfFailedLogins(0);
                usersDal.updateUser(user);
            }
        } else {
            user.setStatus(Status.SUSPENDED);
            user.setSuspensionTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
            usersDal.updateUser(user);
            throw new ServerException(ErrorType.USER_IS_SUSPENDED, user.getUsername() + " Is suspended");
        }
        return true;
    }

    private void checkSuspension(User user) throws ServerException {
        if (user.getStatus() == Status.SUSPENDED) {
            Timestamp suspensionOver = new Timestamp(user.getSuspensionTimeStamp().getTime() + 300_000); // = 5 minutes --> 1000*60*5
            Timestamp currentTimeStamp = Timestamp.valueOf(LocalDateTime.now());
            if (currentTimeStamp.after(suspensionOver)) {
                user.setAmountOfFailedLogins(0);
                user.setStatus(Status.ACTIVE);
                user.setSuspensionTimeStamp(null);
                usersDal.updateUser(user);
            } else {
                throw new ServerException(ErrorType.USER_IS_SUSPENDED, user.getUsername() + " Is suspended");
            }
        }
    }

    private void validateUser(User user) throws ServerException {
        if (!emailValidation(user.getUsername())) {
            throw new ServerException(ErrorType.INVALID_USERNAME);
        }
        if (!passwordValidation(user.getPassword())) {
            throw new ServerException(ErrorType.INVALID_PASSWORD);
        }
        if (user.getFirstName() != null && !nameValidation(user.getFirstName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Invalid first name");
        }
        if (user.getLastName() != null && !nameValidation(user.getLastName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Invalid last name");
        }
        if (user.getPhone() != null && !phoneValidation(user.getPhone())) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER);
        }
        if (user.getUserType() == UserType.COMPANY && user.getCompanyId() == null) {
            throw new ServerException(ErrorType.INVALID_USER, "User of type " + user.getUserType() + " must posses company id");
        }
        if (user.getUserType() != UserType.COMPANY && user.getCompanyId() != null) {
            throw new ServerException(ErrorType.INVALID_USER, "User of type " + user.getUserType() + " cannot posses company id");
        }
    }

    private boolean passwordValidation(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean emailValidation(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean nameValidation(String name) {
        String regex = "^[A-Za-z]{2,30}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean phoneValidation(String phone) {
        String regex = "^[\\+]?[(]?[0-9]{2,3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
