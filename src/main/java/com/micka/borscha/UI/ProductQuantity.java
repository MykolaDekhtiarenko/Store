package com.micka.borscha.UI;

import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Order;
import com.micka.borscha.Entities.Product;
import com.micka.borscha.Services.OrderService;
import com.micka.borscha.Services.ProductService;
import com.micka.borscha.Utils.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductQuantity extends JFrame {

    private ProductService productService = new ProductService();
    private OrderService orderService = new OrderService();
    private JMenuBar menuBar;
    private JButton apply;
    private JButton openProductTable;
    private JButton button2;
    private JComboBox comboOrder;
    private JComboBox combobox1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textfield1;
    private Product transferProduct;
    private Order transferOrder;
    private Integer productAmount;

    ProductQuantity(Product product, Order order){
        this.transferOrder = order;
        this.transferProduct = product;
        initUi();
    }

    private void initUi(){
        this.setTitle("GUI_project");
        this.setSize(500,400);
        //menu generate method


        this.setJMenuBar(menuBar);

        //pane with null layout
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500,400));
        contentPane.setBackground(new Color(192,192,192));


        apply = new JButton();
        apply.setBounds(277,299,90,35);
        apply.setBackground(new Color(214,217,223));
        apply.setForeground(new Color(0,0,0));
        apply.setEnabled(true);
        apply.setFont(new Font("sansserif",0,12));
        apply.setText("Apply");
        apply.setVisible(true);
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productAmount = Integer.parseInt(textfield1.getText());
                productService.pushOrderProduct(transferProduct, transferOrder,productAmount);
            }
        });




        openProductTable = new JButton();
        openProductTable.setBounds(341,5,158,22);
        openProductTable.setBackground(new Color(214,217,223));
        openProductTable.setForeground(new Color(0,0,0));
        openProductTable.setEnabled(true);
        openProductTable.setFont(new Font("sansserif",0,12));
        openProductTable.setText("Open Product Table");
        openProductTable.setVisible(true);


        button2 = new JButton();
        button2.setBounds(61,299,90,35);
        button2.setBackground(new Color(214,217,223));
        button2.setForeground(new Color(0,0,0));
        button2.setEnabled(true);
        button2.setFont(new Font("sansserif",0,12));
        button2.setText("Cancel");
        button2.setVisible(true);
        button2.addActionListener(new MyActionListener(this));

        List<Order> vendors = null;
        try {
            vendors = orderService.getDao().getAll();
            Object array[] = new Object[vendors.size()];
            for (int i = 0; i <vendors.size() ; i++) {
                array[i] = vendors.get(i).getOrder_date();
                comboOrder = new JComboBox(array);
            }
        } catch (PersistException e) {
            DbUtils.showError(e);

        }



        comboOrder.setBounds(240,145,90,35);
        comboOrder.setBackground(new Color(214,217,223));
        comboOrder.setForeground(new Color(0,0,0));
        comboOrder.setEnabled(true);
        comboOrder.setFont(new Font("sansserif",0,12));
        comboOrder.setVisible(true);
        comboOrder.setSelectedItem(transferOrder.getOrder_date().toString());

        List<Product> products = null;
        try {
            products = productService.getDao().getAll();
            Object array[] = new Object[products.size()];
            for (int i = 0; i <products.size() ; i++) {
                array[i] = products.get(i).getProduct_title();
                combobox1 = new JComboBox(array);
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }

        combobox1.setBounds(237,91,90,35);
        combobox1.setBackground(new Color(214,217,223));
        combobox1.setForeground(new Color(0,0,0));
        combobox1.setEnabled(true);
        combobox1.setFont(new Font("sansserif",0,12));
        combobox1.setVisible(true);
        combobox1.setSelectedItem(transferProduct.getProduct_title().toString());


        label1 = new JLabel();
        label1.setBounds(78,95,121,32);
        label1.setBackground(new Color(214,217,223));
        label1.setForeground(new Color(0,0,0));
        label1.setEnabled(true);
        label1.setFont(new Font("sansserif",0,12));
        label1.setText("Ordered Product: ");
        label1.setVisible(true);

        label2 = new JLabel();
        label2.setBounds(79,151,122,25);
        label2.setBackground(new Color(214,217,223));
        label2.setForeground(new Color(0,0,0));
        label2.setEnabled(true);
        label2.setFont(new Font("sansserif",0,12));
        label2.setText("Attached to order: ");
        label2.setVisible(true);

        label3 = new JLabel();
        label3.setBounds(75,201,105,33);
        label3.setBackground(new Color(214,217,223));
        label3.setForeground(new Color(0,0,0));
        label3.setEnabled(true);
        label3.setFont(new Font("sansserif",0,12));
        label3.setText("Products Quantity:");
        label3.setVisible(true);

        textfield1 = new JTextField();
        textfield1.setBounds(239,200,90,35);
        textfield1.setBackground(new Color(255,255,255));
        textfield1.setForeground(new Color(0,0,0));
        textfield1.setEnabled(true);
        textfield1.setFont(new Font("sansserif",0,12));
        textfield1.setText("");
        textfield1.setVisible(true);

        //adding components to contentPane panel
        contentPane.add(apply);
        contentPane.add(openProductTable);
        contentPane.add(button2);
        contentPane.add(comboOrder);
        contentPane.add(combobox1);
        contentPane.add(label1);
        contentPane.add(label2);
        contentPane.add(label3);
        contentPane.add(textfield1);

        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
       // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private class MyActionListener implements ActionListener{
        private JFrame jFrame;
        MyActionListener(JFrame jFrame){
            this.jFrame=jFrame;}
        @Override
        public void actionPerformed(ActionEvent e) {
            jFrame.dispose();
        }
    }
}




















