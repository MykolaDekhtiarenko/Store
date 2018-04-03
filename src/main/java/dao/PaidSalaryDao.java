package dao;

import utils.EntityRetriever;
import model.PaidSalary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaidSalaryDao {
    private static final String SELECT_ALL = "SELECT * FROM paid_salary";
    private static final String SELECT_BY_ID = "SELECT * FROM paid_salary WHERE id = ?";
    private static final String CREATE = "INSERT INTO paid_salary (salary, date, employee_id)\n" +
            "VALUES (?, ?, ?);";
    private static final String SELECT_BY_EMPLOYEE = "SELECT * FROM paid_salary Where employee_id = ? ";



    private Connection connection;

    public PaidSalaryDao(Connection connection) {
        this.connection = connection;
    }

    public List<PaidSalary> findAll() {
        List<PaidSalary> paidSalaryList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                paidSalaryList.add(EntityRetriever.retrievePaidSalary(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paidSalaryList;
    }

    public PaidSalary findById(Integer id) {
        PaidSalary paidSalary = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                paidSalary = EntityRetriever.retrievePaidSalary(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paidSalary;
    }

    public boolean create(PaidSalary paidSalary) {
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE)) {

            statement.setInt(1, (int) (paidSalary.getSalary()*100));
            statement.setDate(2, paidSalary.getDate());
            statement.setInt(3, paidSalary.getEmployeeId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<PaidSalary> findByEmployeeId(int employeeId){
        List<PaidSalary> paidSalaryList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_EMPLOYEE)) {
            statement.setInt(1, employeeId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                paidSalaryList.add(EntityRetriever.retrievePaidSalary(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paidSalaryList;
    }

    public void close() throws Exception {
        connection.close();
    }
}
