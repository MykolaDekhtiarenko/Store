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

    private  Employee getEmployeeById(Integer integer) throws PersistException {
        Employee employee = (Employee) dao.getByPK(integer);
        return employee;
    }

    public void loginById(Integer integer) throws PersistException {
        Employee employee = getEmployeeById(integer);
        if(employee.getId() == integer){
            Main.employee = employee;
            System.out.print("Write login");
            System.out.print(Main.employee.getEmployee_name());


        }
        else{
            System.out.print("WrongLogin");
        }
    }

}
