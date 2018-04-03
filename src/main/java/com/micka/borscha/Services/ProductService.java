package com.micka.borscha.Services;

import com.micka.borscha.DAO.DaoFactory;
import com.micka.borscha.DAO.GenericDao;
import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Order;
import com.micka.borscha.Entities.Product;
import com.micka.borscha.Entities.Supply;
import com.micka.borscha.Utils.DbUtils;
import com.micka.borscha.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductService {


    DaoFactory<Connection> factory;
    Connection connection;
    private GenericDao dao;

    private VendorService vendorService = new VendorService();
    private String insertSQl = "INSERT INTO store.order_has_product (product_id,order_id,amount)";

    public ProductService(){
            factory = new MySqlDaoFactory();
            try {
                connection = factory.getContext();
                dao = factory.getDao(connection, Product.class);
            } catch (PersistException e) {
                e.printStackTrace();
            }
    }

    public GenericDao getDao() {
        return dao;
    }

    public List<Product> fillteredArray() throws PersistException {
        List<Product> allProduct = dao.getAll();
        List<Product> filteredByQuantity = new ArrayList<Product>();

        for(Product product:allProduct){
            if(product.getProduct_quantity()<=product.getProduct_minimum())
                filteredByQuantity.add(product);
        }
        return filteredByQuantity;
    }


    public void pushOrderProduct(Product selectedProduct,Order selectedOrder,Integer quantity){
        try {
            Statement statement = connection.createStatement();
            String sql = insertSQl+"VALUES("+selectedProduct.getId()+","+selectedOrder.getId()+","+quantity+");";
            System.out.println(sql);
            statement.executeUpdate(sql);
        }catch (Exception e){
            DbUtils.showError(e);
        }
    }

    public void pushProductToDeivery(Product product, Supply supply, Integer amount, Integer price){
        try{
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO store.delivery_has_product (delivery_id,product_id,price,amount)"+"VALUES("+supply.getId()+","+product.getId()+","+price+","+amount+");";
            statement.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public HashMap<String,Product> fillHashMap(){
        HashMap<String,Product> hashMap = new HashMap<>();
        try {
            List<Product> products = getDao().getAll();

            for(Product product:products){
                hashMap.put(product.getProduct_title(),product);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return hashMap;
    }
}
