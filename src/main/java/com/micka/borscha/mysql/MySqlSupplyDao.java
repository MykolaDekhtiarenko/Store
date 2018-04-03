package com.micka.borscha.mysql;

import com.micka.borscha.DAO.AbstractJDBCDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Supply;
import com.micka.borscha.Utils.DbUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class MySqlSupplyDao extends AbstractJDBCDao<Supply, Integer> {

    private class PersistSupply extends Supply{
        @Override
        public void setId(int id) {
            super.setId(id);
        }
    }

    private String db_name = "store.delivery";
    private String fk =  "supplier_id";

    @Override
    public String getSelectQuery() {
        return "SELECT id, date, supplier_id FROM store.delivery";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO store.delivery (date,"+fk+") \n" +
                "VALUES (?, ?);";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Supply object) throws PersistException {
        try {
            Date sqlDate = DbUtils.convert(object.getSupply_date());
            statement.setDate(1,sqlDate);
            statement.setInt(2,object.getVendor_id());
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE "+db_name+" \n" +
                "SET date = ?, supplier_id = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Supply object) throws PersistException {
        try {
            Date sqlDate = DbUtils.convert(object.getSupply_date());
            statement.setDate(1,sqlDate);
            statement.setInt(2,object.getVendor_id());
            statement.setInt(3,object.getId());
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM "+db_name+" WHERE id= ?;";
    }

    @Override
    protected List<Supply> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Supply> result = new LinkedList<Supply>();
        try {
            while (rs.next()) {
                PersistSupply student = new PersistSupply();
                student.setId(rs.getInt("id"));
                student.setSupply_date(rs.getDate("date"));
                student.setVendor_id(rs.getInt(fk));
                result.add(student);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    public Supply create() throws PersistException {
        Supply supply = new Supply();
        return persist(supply);
    }

    public MySqlSupplyDao(Connection connection) {
        super(connection);
    }


}
