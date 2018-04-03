package com.micka.borscha.UI;

import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Order;
import com.micka.borscha.Entities.Product;
import com.micka.borscha.Services.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProductTable {

    private ProductService productService = new ProductService();
    private Order transferOrder;
    private Product choosedProduct;

    public ProductTable(Order order) throws PersistException {

        this.transferOrder = order;

        JFrame frame = new JFrame();
        final JTable table = new JTable();
        JButton btnAdd = new JButton("Create Order");

        final Object[] columns = {"Id","Title", "Quantity","Minimum"};

        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);

        List<Product> productList = productService.getDao().getAll();

        for (int i = 0; i <productList.size() ; i++) {
            Object[] rowdata = new Object[]{productList.get(i).getId(),productList.get(i).getProduct_title(),productList.get(i).getProduct_quantity(),productList.get(i).getProduct_minimum()};
            System.out.print("test");
            model.addRow(rowdata);
        }

        table.setModel(model);

        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);

        String array[] = {"All","By minimum"};
        JComboBox comboBox = new JComboBox(array);
        comboBox.setBounds(20, 200, 100, 25);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);


        btnAdd.setBounds(375, 200, 150, 25);
        frame.setLayout(null);

        frame.add(pane);
        frame.add(comboBox);
        frame.add(btnAdd);
        frame.setSize(900,400);
        frame.setLocationRelativeTo(null);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        btnAdd.addActionListener(new MyActionListener());


        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                    DefaultTableModel model2 = (DefaultTableModel) table.getModel();
                    model2.setRowCount(0);
                    model2.setColumnIdentifiers(columns);
                    if(e.getItem().toString() == "By minimum" ) {
                        try {
                            List<Product> productList2 = productService.fillteredArray();

                            for (int i = 0; i < productList2.size(); i++) {
                                Object[] rowdata = new Object[]{productList2.get(i).getId(), productList2.get(i).getProduct_title(), productList2.get(i).getProduct_quantity(), productList2.get(i).getProduct_minimum()};
                                model2.addRow(rowdata);
                            }
                            table.setModel(model2);

                        } catch (PersistException e1) {
                            e1.printStackTrace();
                        }
                    }else{
                        try {
                            List<Product> productList2 = productService.getDao().getAll();
                            for (int i = 0; i < productList2.size(); i++) {
                                Object[] rowdata = new Object[]{productList2.get(i).getId(), productList2.get(i).getProduct_title(), productList2.get(i).getProduct_quantity(), productList2.get(i).getProduct_minimum()};
                                model2.addRow(rowdata);
                            }
                            table.setModel(model2);
                        } catch (PersistException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

        });
        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                // i = the index of the selected row
                int i = table.getSelectedRow();

                Product selectedProduct = new Product();
                selectedProduct.setId(Integer.parseInt(model.getValueAt(i, 0).toString()));
                selectedProduct.setProduct_title(model.getValueAt(i,1).toString());
                selectedProduct.setProduct_quantity(Integer.parseInt(model.getValueAt(i,2).toString()));
                selectedProduct.setProduct_minimum(Integer.parseInt(model.getValueAt(i,3).toString()));

                choosedProduct = selectedProduct;
            }
        });

    }
    private class MyActionListener implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent e) {

                new ProductQuantity(choosedProduct,transferOrder);

        }
    }
}
