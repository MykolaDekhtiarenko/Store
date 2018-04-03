package com.micka.borscha.mysql;

import com.micka.borscha.DAO.AbstractJDBCDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Order;
import com.micka.borscha.Utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlOrderDao extends AbstractJDBCDao<Order,Integer> {

    private class PersistOrder extends Order{
        @Override
        public void setId(int id) {
            super.setId(id);
        }
    }

    public MySqlOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, date, employee_id, supplier_id, delivery_id FROM store.order";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO store.order (date, employee_id, supplier_id, delivery_id) \n" +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE store.order \n" +
                "SET date = ?, employee_id  = ?, supplier_id = ?, delivery_id = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM store.order WHERE id= ?;";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Order object) throws PersistException {
        try{
            statement.setDate(1, DbUtils.convert(object.getOrder_date()));
            statement.setInt(2,object.getFk_order_employee_id());
            statement.setInt(3,object.getFk_order_vendor_id());
            statement.setInt(4,object.getFk_order_supply_id());
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Order object) throws PersistException {
        try {
            statement.setDate(1,DbUtils.convert(object.getOrder_date()));
            statement.setInt(2,object.getFk_order_employee_id());
            statement.setInt(3,object.getFk_order_vendor_id());
            statement.setInt(4,object.getFk_order_supply_id());
            statement.setInt(5,object.getId());
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

    @Override
    protected List<Order> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Order> result = new LinkedList<Order>();

        try {
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrder_date(rs.getDate("date"));
                order.setFk_order_employee_id(rs.getInt("employee_id"));
                order.setFk_order_vendor_id(rs.getInt("supplier_id"));
                order.setFk_order_supply_id(rs.getInt("delivery_id"));
                result.add(order);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public Order create() throws PersistException {
        PersistOrder order = new PersistOrder();
        return persist(order);
    }
}
