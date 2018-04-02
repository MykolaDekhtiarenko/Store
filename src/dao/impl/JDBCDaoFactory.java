package dao.impl;


import dao.*;
import dao.*;
import utils.Config;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public class JDBCDaoFactory extends DaoFactory {

    public static Connection getConnection(){
        Config config = Config.getInstance();

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(config.getUrl());
        ds.setPassword(config.getPass());
        ds.setUsername(config.getUser());

        Connection connection;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


    public EmployeeDao createEmployeeDao() {
        return new EmployeeDao(getConnection());
    }

    public DepartmentDao createDepartmentDao() {
        return new DepartmentDao(getConnection());
    }

    public PaidBonusDao createPaidBonusDao() {
        return new PaidBonusDao(getConnection());
    }

    public PaidSalaryDao createPaidSalaryDao() {
        return new PaidSalaryDao(getConnection());
    }

}