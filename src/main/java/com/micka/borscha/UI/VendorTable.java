package com.micka.borscha.UI;

import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Entities.Vendor;
import com.micka.borscha.Services.VendorService;
import com.micka.borscha.Utils.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VendorTable {

    private VendorService vendorService = new VendorService();

    private Integer lastIndex;

   VendorTable() throws PersistException {

        // create JFrame and JTable
        JFrame frame = new JFrame();
        final JTable table = new JTable();

        // create a table model and set a Column Identifiers to this model
        Object[] columns = {"Id","Title"};
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);
        List<Vendor> list = vendorService.getAllVendors();



       for (int i = 0; i <list.size() ; i++) {
           Object[] rowdata = new Object[]{list.get(i).getId(),list.get(i).getVendorTitle()};
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

        // create JButtons
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");

        textId.setBounds(20, 220, 100, 25);
        textFname.setBounds(20, 250, 100, 25);
        textId.setEnabled(false);

        btnAdd.setBounds(150, 220, 100, 25);
        btnUpdate.setBounds(150, 265, 100, 25);
        btnDelete.setBounds(150, 310, 100, 25);

        // create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);

        frame.setLayout(null);

        frame.add(pane);

        // add JTextFields to the jframe
        frame.add(textId);
        frame.add(textFname);


        // add JButtons to the jframe
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);

        // create an array of objects to set the row data
        final Object[] row = new Object[2];

        // button add row
        btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                row[0] = String.valueOf(Integer.parseInt(textId.getText())+1);
                row[1] = textFname.getText();

                Vendor vendor = new Vendor();
               // vendor.setVendorId(Integer.parseInt(textId.getText()));
                vendor.setVendorTitle(textFname.getText());

                try {
                     vendorService.getDao().persist(vendor);
                    model.addRow(row);
                } catch (PersistException e1) {
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
                   Integer deleteId = Integer.parseInt(table.getValueAt(i,0).toString());
                   String deleteTitle =(String) table.getValueAt(i,1);
                   Vendor vendor = new Vendor();
                   vendor.setVendorId(deleteId);
                   vendor.setVendorTitle(deleteTitle);
                    try {
                        vendorService.getDao().delete(vendor);
                        model.removeRow(i);
                    } catch (PersistException e1) {
                        DbUtils.showError(e1);
                    }

                    // remove a row from jtable

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



                textId.setText(model.getValueAt(i, 0).toString());
                textFname.setText(model.getValueAt(i, 1).toString());
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
                    Vendor vendor = new Vendor();
                    vendor.setVendorId(Integer.parseInt(textId.getText()));
                    vendor.setVendorTitle(textFname.getText());
                    try {
                        vendorService.getDao().update(vendor);
                    } catch (PersistException e1) {
                        DbUtils.showError(e1);
                    }

                }
                else{
                    System.out.println("Update Error");
                }
            }
        });

        frame.setSize(900,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

