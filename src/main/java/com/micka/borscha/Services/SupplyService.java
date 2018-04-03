package com.micka.borscha.Services;

import com.micka.borscha.DAO.DaoFactory;
import com.micka.borscha.DAO.GenericDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Supply;
import com.micka.borscha.View.ProductToSUpplyView;
import com.micka.borscha.View.SupplyView;
import com.micka.borscha.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SupplyService {

    DaoFactory<Connection> factory;
    Connection connection;
    private GenericDao dao;
    private VendorService vendorService = new VendorService();

    public SupplyService(){
        factory = new MySqlDaoFactory();
        try {
            connection = factory.getContext();
            dao = factory.getDao(connection, Supply.class);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

   public ArrayList<SupplyView> getAllSuppliesWithVendors(){
        ArrayList<SupplyView> supplyViews = new ArrayList<SupplyView>();
        String sql = "SELECT * \n" +
                "FROM store.delivery inner join store.supplier " +
                "On store.delivery.supplier_id = store.supplier.id;";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()){
                SupplyView supplyView = new SupplyView();
                supplyView.setSupply_id(resultset.getInt("id"));
                supplyView.setSupply_date(resultset.getDate("date"));
                supplyView.setFk_vendor_id(resultset.getInt("supplier_id"));
                supplyView.setVendor_title(resultset.getString("name"));
                supplyViews.add(supplyView);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplyViews;
    }

    public ArrayList<ProductToSUpplyView> getAllProductsBySupplyId(Supply supply){
        ArrayList<ProductToSUpplyView> products = new ArrayList<>();
        System.out.println(supply.getId());
        String sql = "Select product_id,price,delivery_has_product.amount,product.name " +
                "From store.product Inner Join store.delivery_has_product " +
                "On store.delivery_has_product.product_id = store.product.id  " +
                "Where delivery_id ="+supply.getId()+";";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                ProductToSUpplyView productToSUpplyView = new ProductToSUpplyView();
                productToSUpplyView.setId(resultSet.getInt("product_id"));
                productToSUpplyView.setPrice(resultSet.getInt("price"));
                productToSUpplyView.setAmount(resultSet.getInt("amount"));
                productToSUpplyView.setName(resultSet.getString("name"));
                products.add(productToSUpplyView);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

        public GenericDao getDao() {
            return dao;
        }
}
