package com.micka.borscha.mysql;

import com.micka.borscha.DAO.AbstractJDBCDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlEmployeeDao extends AbstractJDBCDao<Employee,Integer> {


    private class PersistEmployee extends Employee{
        @Override
        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return  "SELECT id, first_name, salary FROM store.employee";
    }




    @Override
    public String getDeleteQuery() {
         return "DELETE FROM store.employee WHERE id= ?;";
    }

    @Override
    public Employee create() throws PersistException {
        Employee employee = new Employee();
        return persist(employee);
    }

    public MySqlEmployeeDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Employee> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Employee> result = new LinkedList<Employee>();
        try {
            while (rs.next()) {
                PersistEmployee student = new PersistEmployee();
                student.setId(rs.getInt("id"));
                student.setEmployee_name(rs.getString("first_name"));
                student.setEmployee_salary(rs.getInt("salary"));
                result.add(student);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO store.employee (first_name, salary) \n" +
                "VALUES (?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Employee object) throws PersistException {
        try{
            statement.setString(1,object.getEmployee_name());
            statement.setInt(2,object.getEmployee_salary());
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE store.employee \n" +
                "SET first_name = ?, salary  = ?\n" +
                "WHERE id = ?;";
    }
    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Employee object) throws PersistException {
        try {
            statement.setString(1,object.getEmployee_name());
            statement.setInt(2,object.getEmployee_salary());
            statement.setInt(3,object.getId());
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

}
