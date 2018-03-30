package dao;


import utils.EntityRetriever;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements AutoCloseable{

    private static final String SELECT_ALL = "SELECT * FROM Employee";
    private static final String SELECT_BY_ID = "SELECT * FROM Employee WHERE id = ?";
    private static final String CREATE = "INSERT INTO Employee (first_name, last_name, department_id, salary)\n" +
            "VALUES (?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE employee SET " +
            "salary = ? " +
            "WHERE id = ?";
    private static final String SELECT_BY_DEPARTMENT_ID = "SELECT * FROM Employee WHERE department_id = ?";



    private Connection connection;

    public EmployeeDao(Connection connection) {
        this.connection = connection;
    }

    public List<Employee> findAll(){
        List<Employee> allEmployee = new ArrayList<>();
        try(PreparedStatement statement
                    = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allEmployee.add(EntityRetriever.retrieveEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEmployee;
    }

    public Employee findById(Integer id){
        Employee employee = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_ID)){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                employee = EntityRetriever.retrieveEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public boolean create(Employee employee){
        try(PreparedStatement statement
                    = connection.prepareStatement(CREATE)) {

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getDepartmentId());
            statement.setInt(4, (int) (employee.getSalary()*100));
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(Employee infoForUpdate) {
        Employee current = findById(infoForUpdate.getId());
        try (PreparedStatement statement
                     = connection.prepareStatement(UPDATE)){
            if(infoForUpdate.getSalary()!=0)
                statement.setInt(1, (int)infoForUpdate.getSalary()*100);
            else
                statement.setInt(1, (int) current.getSalary()*100);

            statement.setInt(2, infoForUpdate.getId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Employee> findByDepartmentId(int departmentId) {
        List<Employee> allEmployee = new ArrayList<>();
        try(PreparedStatement statement
                    = connection.prepareStatement(SELECT_BY_DEPARTMENT_ID)) {
            statement.setInt(1, departmentId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allEmployee.add(EntityRetriever.retrieveEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEmployee;
    }

    public void close() throws Exception {
        connection.close();
    }


}
