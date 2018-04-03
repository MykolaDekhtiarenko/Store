package com.micka.borscha.UI;

import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Supply;
import com.micka.borscha.Entities.Vendor;
import com.micka.borscha.Services.SupplyService;
import com.micka.borscha.Services.VendorService;
import com.micka.borscha.Utils.DbUtils;
import com.micka.borscha.View.SupplyView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SupplyTable {

    private SupplyService supplyService = new SupplyService();
    private VendorService vendorService = new VendorService();
    private HashMap<String,Integer> hashMap = new HashMap<>();

    private Supply choosedDelivery;

    SupplyTable() throws PersistException {

        // create JFrame and JTable
        JFrame frame = new JFrame();
        final JTable table = new JTable();

        // create a table model and set a Column Identifiers to this model
        Object[] columns = {"Id","Supply Date", "Vendor"};
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);

        ArrayList<SupplyView> supplyViews = supplyService.getAllSuppliesWithVendors();
            for (int i = 0; i <supplyViews.size() ; i++) {
                Object[] rowdata = new Object[]{supplyViews.get(i).getSupply_id(),supplyViews.get(i).getSupply_date().toString(),supplyViews.get(i).getVendor_title()};
                model.addRow(rowdata);
            }

        fillHashMap();

        table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);

        List<Vendor> vendors = vendorService.getDao().getAll();
        Object array[] = new Object[vendors.size()];
        for (int i = 0; i <vendors.size() ; i++) {
            array[i] = vendors.get(i).getVendorTitle();
        }

        // create JTextFields
        final JTextField textId = new JTextField();
        final JTextField textFname = new JTextField();
        final JComboBox comboBox = new JComboBox(array);

        JTextField textAge = new JTextField();
        JLabel hidenLabel = new JLabel();

        // create JButtons
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");
        JButton addProductToDelivery = new JButton("Add product");
        JButton showProductToSupply = new JButton("Show products in supply");

        textId.setBounds(20, 220, 100, 25);
        textFname.setBounds(20, 250, 100, 25);
        comboBox.setBounds(20, 280, 100, 25);
        textAge.setBounds(20, 310, 100, 25);

        textId.setEnabled(false);

        hidenLabel.setBounds(100,310,100,100);

        btnAdd.setBounds(150, 220, 100, 25);
        btnUpdate.setBounds(150, 265, 100, 25);
        btnDelete.setBounds(150, 310, 100, 25);
        addProductToDelivery.setBounds(400,220,125,25);
        showProductToSupply.setBounds(400,275,125,25);
        // create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);

        frame.setLayout(null);

        frame.add(pane);

        // add JTextFields to the jframe
        frame.add(textId);
        frame.add(textFname);
        frame.add(comboBox);
        frame.add(hidenLabel);
        // add JButtons to the jframe
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);
        frame.add(addProductToDelivery);
        frame.add(showProductToSupply);

        // create an array of objects to set the row data
        final Object[] row = new Object[3];

        // button add row
        btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                row[0] = Integer.parseInt(textId.getText())+1;
                row[1] = textFname.getText();
                row[2] = comboBox.getSelectedItem().toString();

                String inputString = textFname.getText();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date inputDate = dateFormat.parse(inputString);
                    Supply supply = new Supply();
                    supply.setSupply_date(DbUtils.convert(inputDate));
                    supply.setVendor_id(hashMap.get(comboBox.getSelectedItem().toString()));
                    supplyService.getDao().persist(supply);

                    model.addRow(row);

                } catch (ParseException | PersistException e1) {
                   DbUtils.showError(e1);
                }


                // add row to the model

            }
        });

        // button remove row
        btnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();
                if(i >= 0){
                    String inputString = textFname.getText();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date inputDate = dateFormat.parse(inputString);
                        Supply supply = new Supply();
                        supply.setId(Integer.parseInt(textId.getText()));
                        supply.setSupply_date(DbUtils.convert(inputDate));
                        supply.setVendor_id(hashMap.get(comboBox.getSelectedItem().toString()));
                        supplyService.getDao().delete(supply);

                    } catch (ParseException | PersistException e1) {
                        DbUtils.showError(e1);
                    }
                    // remove a row from jtable
                    model.removeRow(i);
                }
                else{
                    System.out.println("Delete Error");
                }
            }
        });

        // get selected row data From table to textfields
        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                // i = the index of the selected row
                int i = table.getSelectedRow();

                Supply choosedSupply = new Supply();
                choosedSupply.setId(Integer.parseInt(model.getValueAt(i, 0).toString()));
                choosedSupply.setSupply_date(DbUtils.convertFromString(model.getValueAt(i,1).toString()));
                choosedSupply.setVendor_id(hashMap.get(model.getValueAt(i,2).toString()));

                choosedDelivery= choosedSupply;


                textId.setText(model.getValueAt(i, 0).toString());
                textFname.setText(model.getValueAt(i, 1).toString());
                comboBox.setSelectedItem(model.getValueAt(i,2).toString());



            }
        });

        // button update row
        btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();

                if(i >= 0)
                {
                    model.setValueAt(textId.getText(), i, 0);
                    model.setValueAt(textFname.getText(), i, 1);
                    String inputString = textFname.getText();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date inputDate = dateFormat.parse(inputString);
                        Supply supply = new Supply();
                        supply.setId(Integer.parseInt(textId.getText()));
                        supply.setSupply_date(DbUtils.convert(inputDate));
                        supply.setVendor_id(hashMap.get(comboBox.getSelectedItem().toString()));
                        supplyService.getDao().update(supply);

                    } catch (ParseException | PersistException e1) {
                        e1.printStackTrace();
                    }

                }
                else{
                    System.out.println("Update Error");
                }
            }
        });

        showProductToSupply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductView(choosedDelivery);
            }
        });

        addProductToDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductToDelivery(choosedDelivery);
            }
        });

        frame.setSize(900,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void fillHashMap(){
        try {
            List<Vendor> vendors =vendorService.getDao().getAll();
            for(Vendor vendor:vendors){
                hashMap.put(vendor.getVendorTitle(),vendor.getId());
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }
}
