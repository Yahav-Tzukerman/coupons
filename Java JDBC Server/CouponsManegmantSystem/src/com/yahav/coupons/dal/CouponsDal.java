package com.yahav.coupons.dal;

import com.yahav.coupons.beans.Coupon;
import com.yahav.coupons.enums.Category;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponsDal {
    public CouponsDal() {
    }

    public boolean isCompanyCouponsExist(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM coupons WHERE company_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Something went wrong while trying to check if company coupons exist");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public boolean isCouponExist(String couponCode) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM coupons WHERE code = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, couponCode);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Something went wrong while trying to check coupon exist");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public int updateCoupon(Coupon coupon) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "UPDATE coupons " +
                    "SET code = ? , title = ? , description = ? ," +
                    "start_date = ? , end_date = ? , " +
                    "category_id = (SELECT category_id FROM categories WHERE name LIKE (?)) ," +
                    "amount = ? , price = ? , image = ? " +
                    "WHERE coupon_id = ?";

            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, coupon.getCode());
            preparedStatement.setString(2, coupon.getTitle());
            preparedStatement.setString(3, coupon.getDescription());
            preparedStatement.setDate(4, coupon.getStartDate());
            preparedStatement.setDate(5, coupon.getEndDate());
            preparedStatement.setString(6, String.valueOf(coupon.getCategory()));
            preparedStatement.setInt(7, coupon.getAmount());
            preparedStatement.setFloat(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());
            preparedStatement.setInt(10, coupon.getId());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to update coupon: " + coupon.getCode());
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int getId(String code) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT coupon_id FROM coupons WHERE code = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, code);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to update coupon on database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public Coupon getCoupon(int couponId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT coupon_id, code,title, description, start_date, end_date, cat.name, " +
                    "amount,price, com.name, image " +
                    "FROM coupons AS cop " +
                    "JOIN categories AS cat USING(category_id) " +
                    "JOIN companies AS com USING(company_id) WHERE coupon_id = ?";

            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, couponId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return setCoupon(resultSet);
            }
            return null;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to get coupon from database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Coupon> getCouponsByCategory(Category category) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Coupon> coupons = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT coupon_id, code,title, description, start_date, end_date, cat.name, " +
                    "amount,price, com.name, image " +
                    "FROM coupons AS cop " +
                    "JOIN categories AS cat USING(category_id) " +
                    "JOIN companies AS com USING(company_id) WHERE cat.name = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, String.valueOf(category));

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = setCoupon(resultSet);
                coupons.add(coupon);
            }
            return coupons;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Coupon> getCoupons() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Coupon> coupons = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT coupon_id, code,title, description, start_date, end_date, cat.name, " +
                    "amount,price, com.name, image " +
                    "FROM coupons AS cop " +
                    "JOIN categories AS cat USING(category_id) " +
                    "JOIN companies AS com USING(company_id);";
            preparedStatement = connection.prepareStatement(sqlStatement);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = setCoupon(resultSet);
                coupons.add(coupon);
            }
            return coupons;

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
            String sqlStatement = "DELETE FROM coupons";
            preparedStatement = connection.prepareStatement(sqlStatement);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void deleteCompanyCoupons(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM coupons WHERE company_id = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, companyId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to delete company coupons from the database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int deleteCoupon(int couponId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM coupons WHERE coupon_id = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, couponId);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to delete coupon from database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int addCoupon(Coupon coupon) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "INSERT INTO coupons (code, title, description, start_date, end_date, category_id, amount, price, company_id, image)" +
                    "VALUES(?,?,?,?,?,( SELECT category_id FROM categories WHERE name LIKE (?) )," +
                    "?,?,(SELECT company_id FROM companies WHERE name LIKE(?)),?)";
            preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, coupon.getCode());
            preparedStatement.setString(2, coupon.getTitle());
            preparedStatement.setString(3, coupon.getDescription());
            preparedStatement.setDate(4, coupon.getStartDate());
            preparedStatement.setDate(5, coupon.getEndDate());
            preparedStatement.setString(6, coupon.getCategory().toString());
            preparedStatement.setInt(7, coupon.getAmount());
            preparedStatement.setFloat(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getCompanyName());
            preparedStatement.setString(10, coupon.getImage());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new ServerException(ErrorType.FAILED_TO_GENERATE_ID, "Invalid coupon key during creation");
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Unable to insert new coupon into database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    private Coupon setCoupon(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String code = resultSet.getString(2);
        String title = resultSet.getString(3);
        String description = resultSet.getString(4);
        Date startDate = resultSet.getDate(5);
        Date endDate = resultSet.getDate(6);
        Category category = Category.valueOf(resultSet.getString(7));
        int amount = resultSet.getInt(8);
        float price = resultSet.getFloat(9);
        String companyName = resultSet.getString(10);
        String image = resultSet.getString(11);

        return new Coupon(id, code, companyName, category,
                title, description, startDate, endDate, amount, price, image);
    }
}