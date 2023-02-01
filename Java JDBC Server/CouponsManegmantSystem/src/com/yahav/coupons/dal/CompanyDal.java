package com.yahav.coupons.dal;

import com.yahav.coupons.beans.Company;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDal {

    public CompanyDal() {
    }

    public boolean isCompanyExists(String name) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM companies WHERE name = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Something went wrong while trying to check if company exist");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public int updateCompany(Company company) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "UPDATE companies SET name = ? , phone = ? , foundation_date = ? , country = ?, city = ?, address = ? WHERE company_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getPhone());
            if (company.getFoundationDate() != null) {
                preparedStatement.setDate(3, company.getFoundationDate());
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }
            preparedStatement.setString(4, company.getCountry());
            preparedStatement.setString(5, company.getCity());
            preparedStatement.setString(6, company.getAddress());
            preparedStatement.setInt(7, company.getId());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to update company on database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int getId(String name) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT company_id FROM companies WHERE name = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to get id for: " + name);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public Company getCompany(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM companies WHERE company_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, companyId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return setCompany(resultSet);
            }
            return null;

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to get company from database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    public List<Company> getCompanies() throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Company> companies = new ArrayList<>();

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM companies";
            preparedStatement = connection.prepareStatement(sqlStatement);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Company company = setCompany(resultSet);
                companies.add(company);
            }
            return companies;

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
            String sqlStatement = "DELETE FROM companies";
            preparedStatement = connection.prepareStatement(sqlStatement);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int deleteCompany(int companyId) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE FROM companies WHERE company_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, companyId);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Failed to delete company from database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int addCompany(Company company) throws ServerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "INSERT INTO companies (name, phone, foundation_date, country, city, address) VALUES(?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getPhone());
            if (company.getFoundationDate() != null) {
                preparedStatement.setDate(3, company.getFoundationDate());
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }
            preparedStatement.setString(4, company.getCountry());
            preparedStatement.setString(5, company.getCity());
            preparedStatement.setString(6, company.getAddress());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new ServerException(ErrorType.FAILED_TO_GENERATE_ID, "Invalid company key during creation");
            }
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new ServerException(e, ErrorType.SQL_ERROR, "Unable to insert new company into database");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement, resultSet);
        }
    }

    private Company setCompany(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String phone = resultSet.getString(3);
        Date foundationDate = null;
        if (resultSet.getObject(4) != null) {
            foundationDate = resultSet.getDate(4);
        }
        String country = resultSet.getString(5);
        String city = resultSet.getString(6);
        String address = resultSet.getString(7);
        return new Company(id, name, phone, foundationDate, country, city, address);
    }
}