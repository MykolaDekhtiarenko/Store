package com.micka.borscha.mysql;

import com.micka.borscha.DAO.AbstractJDBCDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySqlVendorDao extends AbstractJDBCDao<Vendor,Integer> {

    private class PersistVendor extends Vendor{
        @Override
        public void setVendorId(int vendorId) {
            super.setVendorId(vendorId);
        }
    }

    @Override
    public String getSelectQuery() {
        return "Select id,name From store.supplier";
    }

    @Override
    public String getDeleteQuery() {
        return"DELETE FROM store.supplier WHERE id= ?;";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE store.supplier \n" +
                "SET name = ?\n" +
                "WHERE id = ?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO store.supplier (id,name) \n" +
                "VALUES (?,?);";
    }

    public MySqlVendorDao(Connection connection) {
        super(connection);
    }


    public Vendor create() throws PersistException {
        Vendor vendor = new Vendor();
        return persist(vendor);
    }

    @Override
    protected List<Vendor> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Vendor> result = new LinkedList<Vendor>();

        try {
            while (rs.next()){
                Vendor vendor = new Vendor();
                vendor.setVendorId(rs.getInt("id"));
                vendor.setVendorTitle(rs.getString("name"));
                result.add(vendor);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Vendor object) throws PersistException {
        try {
            statement.setInt(1,object.getId());
            statement.setString(2,object.getVendorTitle());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Vendor object) throws PersistException {
        try {
           statement.setInt(2,object.getId());
           statement.setString(1,object.getVendorTitle());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
