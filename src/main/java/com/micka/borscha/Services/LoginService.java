package com.micka.borscha.Services;

import com.micka.borscha.DAO.DaoFactory;
import com.micka.borscha.DAO.GenericDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Employee;
import com.micka.borscha.Main;
import com.micka.borscha.mysql.MySqlDaoFactory;

import java.sql.Connection;

public class LoginService {

        DaoFactory<Connection> factory;
        Connection connection;
        GenericDao dao;


    public LoginService()throws PersistException {
        factory = new  MySqlDaoFactory();
        connection = factory.getContext();
        dao = factory.getDao(connection, Employee.class);
    }

    private  Employee getEmployeeById(Integer integer) {
        Employee employee = null;
        try {
            employee = (Employee) dao.getByPK(integer);
        } catch (PersistException e) {
            return null;
        }
        return employee;
    }

    public boolean loginById(Integer employeeId) throws PersistException {
        Employee employee = getEmployeeById(employeeId);
        if(employee!=null&&
                employee.getId().equals(employeeId)){
            Main.employee = employee;
            System.out.print(Main.employee.getEmployee_name());
            return true;
        }
        else{
            return false;
        }
    }

}
