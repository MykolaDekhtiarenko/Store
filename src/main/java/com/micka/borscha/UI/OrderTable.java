package com.micka.borscha.UI;

import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Order;
import com.micka.borscha.Entities.Supply;
import com.micka.borscha.Entities.Vendor;
import com.micka.borscha.Main;
import com.micka.borscha.Services.OrderService;
import com.micka.borscha.Services.ProductService;
import com.micka.borscha.Services.SupplyService;
import com.micka.borscha.Services.VendorService;
import com.micka.borscha.Utils.DbUtils;
import org.apache.commons.collections4.MapUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderTable {

    private OrderService orderService = new OrderService();
    private VendorService vendorService = new VendorService();
    private SupplyService supplyService = new SupplyService();
    private ProductService productService = new ProductService();
    private HashMap<Integer , String> vendors;
    private HashMap<Integer,String> supplies;
    private Map<String,Integer> reverseVendors;
    private Map<String,Integer> reverseSupplies;
    private Order choosedOrder;

    public OrderTable() throws PersistException {
        // create JFrame and JTable
        JFrame frame = new JFrame();
        final JTable table = new JTable();

        // create a table model and set a Column Identifiers to this model
        Object[] columns = new Object[4];
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);

        vendors = orderService.getVendorsMap();
        supplies = orderService.getSupplyMap();
        reverseVendors = MapUtils.invertMap(vendors);
        reverseSupplies = MapUtils.invertMap(supplies);


        List<Order> orders =  orderService.getDao().getAll();

        for (int i = 0; i <orders.size() ; i++) {
            Object[] rowdata = new Object[]{orders.get(i).getId(),orders.get(i).getOrder_date(),
                    vendors.get(orders.get(i).getFk_order_vendor_id()),supplies.get(orders.get(i).getFk_order_supply_id())};

            System.out.print("test");
            model.addRow(rowdata);
        }


        table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);



        // create JTextFields
        final JTextField textId = new JTextField();
        final JTextField textFname = new JTextField();
        final JComboBox comboVendor = new JComboBox(setComboBoxVendor());
        final JComboBox comboSupply = new JComboBox(setComboBoxSupply());


        textId.setText("1");
        JTextField textAge = new JTextField();
        JLabel hidenLabel = new JLabel();

        // create JButtons
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");
        JButton jButton = new JButton("Continue");
        JButton showProductInOrder = new JButton("Show products");

        textId.setBounds(20, 220, 100, 25);
        textId.setEnabled(false);
        textFname.setBounds(20, 250, 100, 25);

        textAge.setBounds(20, 310, 100, 25);

        hidenLabel.setBounds(100,310,100,100);

        btnAdd.setBounds(150, 220, 100, 25);
        btnUpdate.setBounds(150, 265, 100, 25);
        btnDelete.setBounds(150, 310, 100, 25);
        jButton.setBounds(400,220, 100, 25);
        showProductInOrder.setBounds(400,290,100,25);
        comboVendor.setBounds(20,280,100,25);
        comboSupply.setBounds(20,310,100,25);
        // create JScrollPane
        final JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);

        frame.setLayout(null);

        frame.add(pane);

        // add JTextFields to the jframe
        frame.add(textId);
        frame.add(textFname);
        frame.add(comboVendor);
        frame.add(comboSupply);
        frame.add(hidenLabel);
        // add JButtons to the jframe
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);
        frame.add(jButton);
        frame.add(showProductInOrder);

        frame.setSize(900,400);
        frame.setLocationRelativeTo(null);
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        showProductInOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                 new ProductView(choosedOrder);

            }
        });

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*new ProductQuantity(null,null);*/

                try{
                    new ProductTable(choosedOrder);
                }catch (Exception exce){
                    exce.printStackTrace();
                }

            }
        });


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int i = table.getSelectedRow();

                    textId.setText(model.getValueAt(i, 0).toString());
                    textFname.setText(model.getValueAt(i, 1).toString());
                    comboVendor.setSelectedItem(model.getValueAt(i, 2).toString());
                    comboSupply.setSelectedItem(model.getValueAt(i, 3).toString());

                    String date = textFname.getText();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsed = format.parse(date);
                    java.sql.Date sql = new java.sql.Date(parsed.getTime());


                    Order order = new Order();
                    order.setId(Integer.parseInt(textId.getText()));
                    order.setOrder_date(sql);
                    order.setFk_order_supply_id(reverseSupplies.get(comboSupply.getSelectedItem().toString()));
                    order.setFk_order_employee_id(Main.employee.getId());
                    order.setFk_order_vendor_id(reverseVendors.get(comboVendor.getSelectedItem().toString()));

                   choosedOrder = order;

                }catch (Exception ev){
                    ev.printStackTrace();
                }
            }
        });

        final Object[] row = new Object[4];
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                Order order = new Order();
                String date = textFname.getText();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parsed = format.parse(date);
                java.sql.Date sql = new java.sql.Date(parsed.getTime());


                order.setId(Integer.parseInt(textId.getText()));
                order.setOrder_date(sql);
                order.setFk_order_employee_id(Main.employee.getId());
                order.setFk_order_vendor_id(reverseVendors.get(comboVendor.getSelectedItem().toString()));
                order.setFk_order_supply_id(reverseSupplies.get(comboSupply.getSelectedItem().toString()));

                    row[0] = Integer.parseInt(textId.getText())+1;
                    row[1] = textFname.getText();
                    row[2] = comboVendor.getSelectedItem().toString();
                    row[3] = comboSupply.getSelectedItem().toString();


                    orderService.getDao().persist(order);
                    model.addRow(row);
                } catch (PersistException | ParseException e1) {
                   DbUtils.showError(e1);
                }

            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = table.getSelectedRow();
                try {

                    Order order = new Order();
                    String date = textFname.getText();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsed = format.parse(date);
                    java.sql.Date sql = new java.sql.Date(parsed.getTime());


                    order.setId(Integer.parseInt(textId.getText()));
                    order.setOrder_date(sql);
                    order.setFk_order_employee_id(Main.employee.getId());
                    order.setFk_order_vendor_id(reverseVendors.get(comboVendor.getSelectedItem().toString()));
                    order.setFk_order_supply_id(reverseSupplies.get(comboSupply.getSelectedItem().toString()));

                    orderService.getDao().delete(order);
                    model.removeRow(i);
                }catch (Exception ek){
                    DbUtils.showError(ek);
                }

            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int i = table.getSelectedRow();
                    Order order = new Order();
                    String date = textFname.getText();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsed = format.parse(date);
                    java.sql.Date sql = new java.sql.Date(parsed.getTime());


                    model.setValueAt(textId.getText(), i, 0);
                    model.setValueAt(textFname.getText(), i, 1);
                    model.setValueAt(comboVendor.getSelectedItem().toString(),i,2);
                    model.setValueAt(comboSupply.getSelectedItem().toString(),i,3);

                    order.setId(Integer.parseInt(textId.getText()));
                    order.setOrder_date(sql);
                    order.setFk_order_employee_id(Main.employee.getId());
                    order.setFk_order_vendor_id(reverseVendors.get(comboVendor.getSelectedItem().toString()));
                    order.setFk_order_supply_id(reverseSupplies.get(comboSupply.getSelectedItem().toString()));

                    orderService.getDao().update(order);
                    model.addRow(row);
                }catch (Exception ek){
                    DbUtils.showError(ek);
                }
            }

        });

    }


    private Object[] setComboBoxVendor() throws PersistException {

        List<Vendor> vendors = null;
        Object array[];
            vendors = vendorService.getDao().getAll();
            array = new Object[vendors.size()];
            for (int i = 0; i <vendors.size() ; i++) {
                array[i] = vendors.get(i).getVendorTitle();
            }

        return array;
    }

    private Object[] setComboBoxSupply() throws PersistException{
        List<Supply> supplyList = supplyService.getDao().getAll();
        Object[] array = new Object[supplyList.size()];
        for (int i =0 ; i<supplyList.size();i++){
            array[i] = supplyList.get(i).getSupply_date().toString();
        }
        return array;
    }






}
