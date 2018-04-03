package com.micka.borscha.Services;

import com.micka.borscha.DAO.DaoFactory;
import com.micka.borscha.DAO.GenericDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Vendor;
import com.micka.borscha.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.util.List;

public class VendorService {

    DaoFactory<Connection> factory;
    Connection connection;
    private GenericDao dao;

        public VendorService(){
            factory = new MySqlDaoFactory();
            try {
                connection = factory.getContext();
                dao = factory.getDao(connection, Vendor.class);
            } catch (PersistException e) {
                e.printStackTrace();
            }
        }

    public List<Vendor> getAllVendors() throws PersistException {
        List<Vendor> vendors = dao.getAll();
        return vendors;
    }

    public GenericDao getDao() {
        return dao;
    }
}
