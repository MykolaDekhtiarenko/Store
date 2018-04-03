package dao;

import utils.EntityRetriever;
import model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao implements AutoCloseable {

    private static final String SELECT_ALL = "SELECT * FROM department";
    private static final String SELECT_BY_ID = "SELECT * FROM department WHERE id = ?";
    private static final String CREATE = "INSERT INTO department (name)\n" +
            "VALUES (?);";
    private static final String UPDATE = "UPDATE department SET " +
            "name = ? " +
            "WHERE id = ?";


    private Connection connection;

    public DepartmentDao(Connection connection) {
        this.connection = connection;
    }

    public List<Department> findAll() {
        List<Department> allEmployee = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allEmployee.add(EntityRetriever.retrieveDepartment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEmployee;
    }

    public Department findById(Integer id) {
        Department department = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                department = EntityRetriever.retrieveDepartment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    public boolean create(Department department) {
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE)) {

            statement.setString(1, department.getName());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(Department infoForUpdate) {
        Department current = findById(infoForUpdate.getId());
        try (PreparedStatement statement
                     = connection.prepareStatement(UPDATE)){
            if(infoForUpdate.getName()!=null)
                statement.setString(1, infoForUpdate.getName());
            else
                statement.setString(1, current.getName());

            statement.setInt(2, infoForUpdate.getId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void close() throws Exception {
        connection.close();
    }


}


