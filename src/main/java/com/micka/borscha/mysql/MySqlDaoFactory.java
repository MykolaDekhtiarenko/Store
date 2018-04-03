package com.micka.borscha.mysql;


import com.micka.borscha.DAO.DaoFactory;
import com.micka.borscha.DAO.GenericDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DaoFactory<Connection> {

    private String user = "root";//Логин пользователя
    private String password = "admin";//Пароль пользователя
    private String url = "jdbc:mysql://localhost:3306/store";//URL адрес
    private String driver = "com.mysql.jdbc.Driver";//Имя драйвера
    private Map<Class, DaoCreator> creators;

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }


    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public MySqlDaoFactory() {
        try {
            Class.forName(driver);//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<Class, DaoCreator>();
        creators.put(Vendor.class, new DaoCreator<Connection>() {

            public GenericDao create(Connection connection) {
                return new MySqlVendorDao(connection);
            }
        });
        creators.put(Employee.class, new DaoCreator<Connection>() {

            public GenericDao create(Connection connection) {
                return new MySqlEmployeeDao(connection);
            }
        });
        creators.put(Supply.class, new DaoCreator<Connection>() {

            public GenericDao create(Connection connection) {
                return new MySqlSupplyDao(connection);
            }
        });
        creators.put(Order.class, new DaoCreator<Connection>() {

            public GenericDao create(Connection connection) {
                return new MySqlOrderDao(connection);
            }
        });
        creators.put(Product.class, new DaoCreator<Connection>() {

            public GenericDao create(Connection connection) {
                return new MySqlProductDao(connection);
            }
        });
    }
}
