package com.micka.borscha.mysql;

import com.micka.borscha.DAO.AbstractJDBCDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlProductDao extends AbstractJDBCDao<Product, Integer> {

    private class PersistProduct extends Product{
        @Override
        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return  "SELECT id, name, amount, min_amount FROM store.product";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO store.product(name, amount,min_amount)+\n"+
                "VALUES (?, ?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE store.product \n" +
                "SET name = ?, amount  = ?, min_amount  = ?\n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM store.product WHERE id= ?;";
    }

    @Override
    protected List<Product> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Product> result = new LinkedList<Product>();
        try {
            while (rs.next()) {
                PersistProduct product = new PersistProduct();
                product.setId(rs.getInt("id"));
                product.setProduct_title(rs.getString("name"));
                product.setProduct_quantity(rs.getInt("amount"));
                product.setProduct_minimum(rs.getInt("min_amount"));
                result.add(product);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Product object) throws PersistException {
        try {
            statement.setString(1,object.getProduct_title());
            statement.setInt(2,object.getProduct_quantity());
            statement.setInt(3,object.getProduct_minimum());
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Product object) throws PersistException {
        try {
            statement.setString(1,object.getProduct_title());
            statement.setInt(2,object.getProduct_quantity());
            statement.setInt(3,object.getProduct_minimum());
            statement.setInt(4,object.getId());
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

    @Override
    public Product create() throws PersistException {
        Product product = new Product();
        return persist(product);
    }

    public MySqlProductDao(Connection connection) {
        super(connection);
    }
}
