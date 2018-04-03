package com.micka.borscha.Services;

import com.micka.borscha.DAO.DaoFactory;
import com.micka.borscha.DAO.GenericDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Order;
import com.micka.borscha.Entities.Supply;
import com.micka.borscha.Entities.Vendor;
import  com.micka.borscha.View.ProductToOrderView;
import com.micka.borscha.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderService {

    DaoFactory<Connection> factory;
    Connection connection;
    private GenericDao dao;
    private VendorService vendorService = new VendorService();
    private SupplyService supplyService = new SupplyService();
    private HashMap<Integer, String> vendorsMap;
    private HashMap<Integer, String> supplyMap;

    public OrderService(){
        factory = new MySqlDaoFactory();
        try {
            connection = factory.getContext();
            dao = factory.getDao(connection, Order.class);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    public GenericDao getDao() {
        return dao;
    }

    public HashMap<Integer, String> getVendorsMap() {
        vendorsMap = new HashMap<Integer, String>();
        try {
            List<Vendor> vendorList = vendorService.getDao().getAll();
            for(Vendor vendor:vendorList){
                vendorsMap.put(vendor.getId(),vendor.getVendorTitle());
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return vendorsMap;
    }

    public ArrayList<ProductToOrderView> getAllProductsByOrderId(Order order){
        System.out.println(order.getId());
        ArrayList<ProductToOrderView> products = new ArrayList<>();
        String sql = "Select product.id,product.name,order_has_product.amount \n" +
                "from store.product Inner Join store.order_has_product\n" +
                "On store.order_has_product.product_id=store.product.id\n" +
                "where store.order_has_product.order_id="+order.getId()+";";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                ProductToOrderView productToOrderView = new ProductToOrderView();
                productToOrderView.setId(resultSet.getInt("id"));
                productToOrderView.setName(resultSet.getString("name"));
                productToOrderView.setAmount(Integer.valueOf(resultSet.getString("amount")));
                products.add(productToOrderView);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    public HashMap<Integer, String> getSupplyMap(){
        supplyMap = new HashMap<Integer, String>();
        try {
            List<Supply> supplyList = supplyService.getDao().getAll();
            for(Supply supply:supplyList){
                supplyMap.put(supply.getId(),supply.getSupply_date().toString());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return supplyMap;
    }
}
