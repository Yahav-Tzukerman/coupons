package com.yahav.coupons.dal;

import com.yahav.coupons.beans.Customer;
import com.yahav.coupons.beans.User;
import com.yahav.coupons.enums.*;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersDal {

    public boolean isCustomerExist(String username) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM users AS u JOIN customers AS c WHERE u.username = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Something went wrong while trying to check if customer exist");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public int updateCustomer(Customer customer) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "UPDATE customers " +
                    "SET gender = ?, martial_status = ?, " +
                    "amount_of_kids = ?, birth_date = ?, " +
                    "country = ?, city = ?, address = ?  " +
                    "WHERE user_id = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);

            if (customer.getGender() != null) {
                preparedStatement.setString(1, String.valueOf(customer.getGender()));
            } else {
                preparedStatement.setNull(1, Types.NULL);
            }
            if (customer.getMaritalStatus() != null) {
                preparedStatement.setString(2, String.valueOf(customer.getMaritalStatus()));
            } else {
                preparedStatement.setNull(2, Types.NULL);
            }
            if (customer.getAmountOfKids() != null) {
                preparedStatement.setInt(3, customer.getAmountOfKids());
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }
            preparedStatement.setDate(4, customer.getBirthDate());
            preparedStatement.setString(5, customer.getCountry());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setString(7, customer.getAddress());
            preparedStatement.setInt(8, customer.getUser().getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to update customer on database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public Customer getCustomer(int userId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT u.user_id, u.username, u.password, ut.permission, u.company_id, u.first_name, u.last_name, " +
                    "u.phone, u.join_date, u.amount_of_failed_logins, u.status, u.suspension_timestamp, " +
                    "c.gender, c.martial_status, c.amount_of_kids, c.birth_date, c.country, c.city, c.address " +
                    "FROM users AS u " +
                    "JOIN user_type AS ut " +
                    "ON (user_type = type_id) " +
                    "JOIN customers AS c USING(user_id) " +
                    "WHERE c.user_id = ?;";

            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return setCustomer(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "something went wrong while trying to get customer from database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Customer> getCustomers() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Customer> customers = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT u.user_id, u.username, u.password, ut.permission, u.company_id, u.first_name, u.last_name, " +
                    "u.phone, u.join_date, u.amount_of_failed_logins, u.status, u.suspension_timestamp, " +
                    "c.gender, c.martial_status, c.amount_of_kids, c.birth_date, c.country, c.city, c.address " +
                    "FROM users AS u " +
                    "JOIN user_type AS ut " +
                    "ON (user_type = type_id) " +
                    "JOIN customers AS c USING(user_id);";
            preparedStatement = connection.prepareStatement(sqlStatement);

            resultSet = preparedStatement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                Customer customer = setCustomer(resultSet);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "something went wrong while trying to get customers from database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public int clearContent() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM customers";
            preparedStatement = connection.prepareStatement(sqlStatement);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int deleteCustomer(int userId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM customers WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to delete user from database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void addCustomer(Customer customer) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "INSERT INTO customers (user_id, gender, martial_status, amount_of_kids, birth_date, country, city, address) " +
                    "VALUES(?,?,?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, customer.getUser().getId());
            if (customer.getGender() != null) {
                preparedStatement.setString(2, String.valueOf(customer.getGender()));
            } else {
                preparedStatement.setNull(2, Types.NULL);
            }
            if (customer.getMaritalStatus() != null) {
                preparedStatement.setString(3, String.valueOf(customer.getMaritalStatus()));
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }
            if (customer.getAmountOfKids() != null) {
                preparedStatement.setInt(4, customer.getAmountOfKids());
            } else {
                preparedStatement.setNull(4, Types.NULL);
            }
            preparedStatement.setDate(5, customer.getBirthDate());
            preparedStatement.setString(6, customer.getCountry());
            preparedStatement.setString(7, customer.getCity());
            preparedStatement.setString(8, customer.getAddress());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Unable to insert new customer into database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    private Customer setCustomer(ResultSet resultSet) throws SQLException {
        User user = setUser(resultSet);
        Gender gender = null;
        if (resultSet.getObject(13) != null) {
            gender = Gender.valueOf(resultSet.getString(13));
        }
        MaritalStatus maritalStatus = null;
        if (resultSet.getObject(14) != null) {
            maritalStatus = MaritalStatus.valueOf(resultSet.getString(14));
        }
        Integer amountOfKids = null;
        if (resultSet.getObject(15) != null) {
            amountOfKids = resultSet.getInt(15);
        }
        Date birthDate = resultSet.getDate(16);
        String country = resultSet.getString(17);
        String city = resultSet.getString(18);
        String address = resultSet.getString(19);

        return new Customer(user, gender, maritalStatus, amountOfKids, birthDate, country, city, address);
    }

    private User setUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String username = resultSet.getString(2);
        String password = resultSet.getString(3);
        UserType type = UserType.valueOf(resultSet.getString(4));
        Integer companyId = null;
        if (resultSet.getObject(5) != null) {
            companyId = resultSet.getInt(5);
        }
        String firstName = null;
        if (resultSet.getObject(6) != null) {
            firstName = resultSet.getString(6);
        }
        String lastName = null;
        if (resultSet.getObject(7) != null) {
            lastName = resultSet.getString(7);
        }
        String phone = null;
        if (resultSet.getObject(8) != null) {
            phone = resultSet.getString(8);
        }
        Date joinDate = resultSet.getDate(9);
        int amountOdFailedLogins = resultSet.getInt(10);
        Status status = Status.valueOf(resultSet.getString(11));
        Timestamp suspensionTimeStamp = null;
        if (resultSet.getObject(12) != null) {
            suspensionTimeStamp = resultSet.getTimestamp(12);
        }

        return new User(id, username, password, type, companyId, firstName, lastName, phone, joinDate, amountOdFailedLogins, status, suspensionTimeStamp);
    }
}
