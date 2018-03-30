package utils;

import model.Employee;
import model.PaidSalary;
import model.Department;
import model.PaidBonus;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public class EntityRetriever {
    public static Employee retrieveEmployee(ResultSet rs) throws SQLException {
        return Employee.builder()
                .id(rs.getInt("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .departmentId(rs.getInt("department_id"))
                .salary(rs.getInt("salary")/100)
                .build();
    }

    public static Department retrieveDepartment(ResultSet rs) throws SQLException {
        return Department.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }

    public static PaidBonus retrievePaidBonus(ResultSet rs) throws SQLException {
        return PaidBonus.builder()
                .id(rs.getInt("id"))
                .size(rs.getInt("size")/100)
                .date(rs.getDate("date"))
                .employeeId(rs.getInt("employee_id"))
                .build();
    }

    public static PaidSalary retrievePaidSalary(ResultSet rs) throws SQLException {
        return PaidSalary.builder()
                .id(rs.getInt("id"))
                .salary(rs.getInt("salary")/100)
                .date(rs.getDate("date"))
                .employeeId(rs.getInt("employee_id"))
                .build();
    }






}