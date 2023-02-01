package com.yahav.coupons.dal;

import com.yahav.coupons.beans.User;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.enums.Status;
import com.yahav.coupons.enums.UserType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDal {

    public UsersDal() {
    }

    public boolean isCompanyUsersExists(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT username FROM users WHERE company_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, companyId);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Something went wrong while trying to check if company user exist");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public boolean isUserExists(String username) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT username FROM users WHERE username = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Something went wrong while trying to check if user exist");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public int updateUser(User user) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "UPDATE users " +
                    "SET username = ?, password = ?, " +
                    "user_type = (SELECT type_id FROM user_type WHERE permission = ? ), " +
                    "company_id = ?, first_name = ?, last_name = ?, phone = ? , join_date = ? , " +
                    "amount_of_failed_logins = ?, status = ?, suspension_timestamp = ?  " +
                    "WHERE user_id = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, String.valueOf(user.getUserType()));
            if (user.getCompanyId() != null) {
                preparedStatement.setInt(4, user.getCompanyId());
            } else {
                preparedStatement.setNull(4, Types.NULL);
            }
            if (user.getFirstName() != null) {
                preparedStatement.setString(5, user.getFirstName());
            } else {
                preparedStatement.setNull(5, Types.NULL);
            }
            if (user.getLastName() != null) {
                preparedStatement.setString(6, user.getLastName());
            } else {
                preparedStatement.setNull(6, Types.NULL);
            }
            if (user.getPhone() != null) {
                preparedStatement.setString(7, user.getPhone());
            } else {
                preparedStatement.setNull(7, Types.NULL);
            }
            preparedStatement.setDate(8, user.getJoinDate());
            preparedStatement.setInt(9, user.getAmountOfFailedLogins());
            preparedStatement.setString(10, String.valueOf(user.getStatus()));
            if (user.getSuspensionTimeStamp() != null) {
                preparedStatement.setTimestamp(11, user.getSuspensionTimeStamp());
            } else {
                preparedStatement.setNull(11, Types.NULL);
            }
            preparedStatement.setInt(12, user.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to update user on database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int getId(String username) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT user_id FROM users WHERE username = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to get user id for:  " + username);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public User getUser(int userId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT u.user_id, u.username, u.password, ut.permission, u.company_id, " +
                    "u.first_name, u.last_name, u.phone, u.join_date, u.amount_of_failed_logins, u.status, u.suspension_timestamp " +
                    "FROM users AS u " +
                    "JOIN user_type AS ut " +
                    "ON (user_type = type_id) " +
                    "WHERE u.user_id = ?;";

            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return setUser(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<User> getUsersByType(UserType userType) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT u.user_id, u.username, u.password, ut.permission, u.company_id, " +
                    "u.first_name, u.last_name, u.phone, u.join_date, " +
                    "u.amount_of_failed_logins, u.status, u.suspension_timestamp " +
                    "FROM users AS u " +
                    "JOIN user_type AS ut " +
                    "ON (user_type = type_id) " +
                    "WHERE ut.permission = ?;";

            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, String.valueOf(userType));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = setUser(resultSet);
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<User> getUsers() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT u.user_id, u.username, u.password, ut.permission, u.company_id, " +
                    "u.first_name, u.last_name, u.phone, u.join_date, u.amount_of_failed_logins, u.status, u.suspension_timestamp " +
                    "FROM users AS u " +
                    "JOIN user_type AS ut " +
                    "ON (user_type = type_id);";
            preparedStatement = connection.prepareStatement(sqlStatement);

            resultSet = preparedStatement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                User user = setUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public int clearContent() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM users";
            preparedStatement = connection.prepareStatement(sqlStatement);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deleteCompanyUsers(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM users WHERE company_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, companyId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to delete company users from the database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int deleteUser(int userId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM users WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to delete user from database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int addUser(User user) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();

            String sqlStatement = "INSERT INTO users (username, password, user_type, company_id, first_name, last_name, phone, join_date, amount_of_failed_logins, status, suspension_timestamp) " +
                    "VALUES(?,?,(SELECT type_id FROM user_type WHERE permission = ?),?,?,?,?,?,?,?,?);";

            preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, String.valueOf(user.getUserType()));

            if (user.getCompanyId() != null) {
                preparedStatement.setInt(4, user.getCompanyId());
            } else {
                preparedStatement.setNull(4, Types.NULL);
            }
            if (user.getFirstName() != null) {
                preparedStatement.setString(5, user.getFirstName());
            } else {
                preparedStatement.setNull(5, Types.NULL);
            }
            if (user.getLastName() != null) {
                preparedStatement.setString(6, user.getLastName());
            } else {
                preparedStatement.setNull(6, Types.NULL);
            }
            if (user.getPhone() != null) {
                preparedStatement.setString(7, user.getPhone());
            } else {
                preparedStatement.setNull(7, Types.NULL);
            }
            preparedStatement.setDate(8, user.getJoinDate());
            preparedStatement.setInt(9, user.getAmountOfFailedLogins());
            preparedStatement.setString(10, String.valueOf(user.getStatus()));
            if (user.getSuspensionTimeStamp() != null) {
                preparedStatement.setTimestamp(11, user.getSuspensionTimeStamp());
            } else {
                preparedStatement.setNull(11, Types.NULL);
            }

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new ServerException(ErrorType.FAILED_TO_GENERATE_ID);
            }
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Unable to insert new user into database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public boolean login(String username, String password) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM users WHERE username = ? AND password = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Something went wrong while trying to login into the server");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
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