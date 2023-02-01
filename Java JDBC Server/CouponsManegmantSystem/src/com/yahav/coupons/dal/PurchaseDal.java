package com.yahav.coupons.dal;

import com.yahav.coupons.beans.Purchase;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDal {

    public PurchaseDal() {
    }

    public boolean isPurchaseExist(int purchaseId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM purchases WHERE purchase_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, purchaseId);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Something went wrong please try again");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public int updatePurchase(Purchase purchase) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "UPDATE purchases SET amount = ?, " +
                    "total_price = (SELECT ? * price FROM coupons WHERE coupon_id = (SELECT coupon_id FROM coupons WHERE code = ?)) " +
                    "WHERE purchase_id = ? ";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, purchase.getAmount());
            preparedStatement.setInt(2, purchase.getAmount());
            preparedStatement.setString(3, purchase.getCouponCode());
            preparedStatement.setInt(4, purchase.getPurchaseId());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Failed to update purchase: " + purchase.getUsername() + " , " + purchase.getCouponCode());
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int getId(String username, String couponCode) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT p.purchase_id FROM purchases AS p " +
                    "JOIN users AS u USING(user_id) " +
                    "JOIN coupons AS c USING(coupon_id) " +
                    "WHERE u.username = ? AND c.code = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, couponCode);

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

    public Purchase getPurchase(int purchaseId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT p.purchase_id, c.code, u.username, p.date, p.amount, p.total_price " +
                    "FROM purchases AS p " +
                    "JOIN users AS u USING(user_id) " +
                    "JOIN coupons AS c Using(coupon_id) " +
                    "WHERE purchase_id = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, purchaseId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return setPurchase(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Failed to get purchase");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Purchase> getCouponPurchases(int couponId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Purchase purchase = null;
        List<Purchase> purchases = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT p.purchase_id, c.code, u.username, p.date, p.amount, p.total_price " +
                    "FROM purchases AS p " +
                    "JOIN users AS u USING(user_id) " +
                    "JOIN coupons AS c USING(coupon_id) WHERE coupon_id = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, couponId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                purchase = setPurchase(resultSet);
                purchases.add(purchase);
            }

            return purchases;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to get all company purchases");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Purchase> getCompanyPurchases(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Purchase purchase = null;
        List<Purchase> purchases = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT p.purchase_id, c.code, u.username, p.date, p.amount, p.total_price " +
                    "FROM purchases AS p " +
                    "JOIN users AS u USING(user_id) " +
                    "JOIN coupons AS c USING(coupon_id) WHERE c.company_id = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                purchase = setPurchase(resultSet);
                purchases.add(purchase);
            }

            return purchases;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to get all company purchases");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Purchase> getUserPurchasesByMaxPrice(String username, float maxPrice) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Purchase purchase = null;
        List<Purchase> purchases = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT p.purchase_id, c.code, u.username, p.date, p.amount, p.total_price " +
                    "FROM purchases AS p " +
                    "JOIN users AS u USING(user_id) " +
                    "JOIN coupons AS c Using(coupon_id) WHERE u.username = ? AND c.price <= ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, username);
            preparedStatement.setFloat(2, maxPrice);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                purchase = setPurchase(resultSet);
                purchases.add(purchase);
            }

            return purchases;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Failed to get all purchases");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Purchase> getUserPurchases(int userId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Purchase purchase = null;
        List<Purchase> purchases = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT p.purchase_id, c.code, u.username, p.date, p.amount, p.total_price " +
                    "FROM purchases AS p " +
                    "JOIN users AS u USING(user_id) " +
                    "JOIN coupons AS c Using(coupon_id) WHERE u.user_id = ?;";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                purchase = setPurchase(resultSet);
                purchases.add(purchase);
            }

            return purchases;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to get all user purchases");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Purchase> getPurchases() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Purchase purchase = null;
        List<Purchase> purchases = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT p.purchase_id ,c.code, u.username, p.date, p.amount, p.total_price " +
                    "FROM purchases AS p " +
                    "JOIN users AS u USING(user_id) " +
                    "JOIN coupons AS c Using(coupon_id);";
            preparedStatement = connection.prepareStatement(sqlStatement);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                purchase = setPurchase(resultSet);
                purchases.add(purchase);
            }

            return purchases;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Something went wrong while trying to get all purchases");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public int clearContent() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM purchases";
            preparedStatement = connection.prepareStatement(sqlStatement);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to clear purchases content");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int deletePurchase(int purchaseId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM purchases WHERE purchase_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, purchaseId);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to delete purchase " + purchaseId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int addPurchase(Purchase purchase) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "INSERT INTO purchases (user_id, coupon_id, date, amount, total_price)" +
                    "VALUES ((SELECT user_id from users WHERE username = ?)," +
                    "(SELECT coupon_id from coupons WHERE code = ?)," +
                    "?,?,(SELECT price*? FROM coupons WHERE coupon_id = (SELECT coupon_id from coupons WHERE code = ?)));";
            preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, purchase.getUsername());
            preparedStatement.setString(2, purchase.getCouponCode());
            preparedStatement.setTimestamp(3, purchase.getPurchaseDate());
            preparedStatement.setInt(4, purchase.getAmount());
            preparedStatement.setInt(5, purchase.getAmount());
            preparedStatement.setString(6, purchase.getCouponCode());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new ServerException(ErrorType.FAILED_TO_GENERATE_ID);
            }
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Unable to insert new purchase into database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    private Purchase setPurchase(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String code = resultSet.getString(2);
        String username = resultSet.getString(3);
        Timestamp purchaseDate = resultSet.getTimestamp(4);
        int amount = resultSet.getInt(5);
        float totalPrice = resultSet.getFloat(6);

        return new Purchase(id, username, code, purchaseDate, amount, totalPrice);
    }
}