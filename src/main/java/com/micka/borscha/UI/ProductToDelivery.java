package com.micka.borscha.UI; /**
 *Text genereted by Simple GUI Extension for BlueJ
 */

import com.micka.borscha.Entities.Product;
import com.micka.borscha.Entities.Supply;
import com.micka.borscha.Services.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;


public class ProductToDelivery extends JFrame {

    private HashMap<String,Product> productHashMap = new HashMap<>();

    private JMenuBar menuBar;
    private JButton btnBack;
    private JButton btnCommit;
    private JComboBox comboBoxDelivery;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextField productAmount;
    private JList productList;
    private JTextField productPrice;
    private HashMap<String,Product> hashMap = new HashMap<>();
    private ProductService productService = new ProductService();
    private Supply transferSupply;
    Product choosedProduct;
    //Constructor
    public ProductToDelivery(Supply supply){

        hashMap=productService.fillHashMap();
        transferSupply = supply;


        this.setTitle("GUI_project");
        this.setSize(500,400);
        //menu generate method

        this.setJMenuBar(menuBar);

        //pane with null layout
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500,400));
        contentPane.setBackground(new Color(192,192,192));


        btnBack = new JButton();
        btnBack.setBounds(262,300,90,35);
        btnBack.setBackground(new Color(214,217,223));
        btnBack.setForeground(new Color(0,0,0));
        btnBack.setEnabled(true);
        btnBack.setFont(new Font("sansserif",0,12));
        btnBack.setText("Back");
        btnBack.setVisible(true);

        btnCommit = new JButton();
        btnCommit.setBounds(107,301,90,35);
        btnCommit.setBackground(new Color(214,217,223));
        btnCommit.setForeground(new Color(0,0,0));
        btnCommit.setEnabled(true);
        btnCommit.setFont(new Font("sansserif",0,12));
        btnCommit.setText("Apply");
        btnCommit.setVisible(true);

        comboBoxDelivery = new JComboBox();
        comboBoxDelivery.setBounds(246,201,120,35);
        comboBoxDelivery.setBackground(new Color(214,217,223));
        comboBoxDelivery.setForeground(new Color(0,0,0));
        comboBoxDelivery.setEnabled(true);
        comboBoxDelivery.setFont(new Font("sansserif",0,12));
        comboBoxDelivery.setVisible(true);
        comboBoxDelivery.setEnabled(false);
        comboBoxDelivery.addItem(transferSupply.getSupply_date());

        label1 = new JLabel();
        label1.setBounds(9,5,154,29);
        label1.setBackground(new Color(214,217,223));
        label1.setForeground(new Color(0,0,0));
        label1.setEnabled(true);
        label1.setFont(new Font("sansserif",0,12));
        label1.setText("Products: ");
        label1.setVisible(true);

        label2 = new JLabel();
        label2.setBounds(231,28,151,24);
        label2.setBackground(new Color(214,217,223));
        label2.setForeground(new Color(0,0,0));
        label2.setEnabled(true);
        label2.setFont(new Font("sansserif",0,12));
        label2.setText("Input amount of product: ");
        label2.setVisible(true);

        label3 = new JLabel();
        label3.setBounds(232,102,136,31);
        label3.setBackground(new Color(214,217,223));
        label3.setForeground(new Color(0,0,0));
        label3.setEnabled(true);
        label3.setFont(new Font("sansserif",0,12));
        label3.setText("Input price of product: ");
        label3.setVisible(true);

        label4 = new JLabel();
        label4.setBounds(96,204,143,30);
        label4.setBackground(new Color(214,217,223));
        label4.setForeground(new Color(0,0,0));
        label4.setEnabled(true);
        label4.setFont(new Font("sansserif",0,12));
        label4.setText("Attached to delivery: ");
        label4.setVisible(true);

        productAmount = new JTextField();
        productAmount.setBounds(369,22,90,35);
        productAmount.setBackground(new Color(255,255,255));
        productAmount.setForeground(new Color(0,0,0));
        productAmount.setEnabled(true);
        productAmount.setFont(new Font("sansserif",0,12));
        productAmount.setText("");
        productAmount.setVisible(true);

        productList = new JList();
        productList.setBounds(5,29,200,107);
        productList.setBackground(new Color(255,255,255));
        productList.setForeground(new Color(0,0,0));
        productList.setEnabled(true);
        productList.setFont(new Font("sansserif",0,12));
        productList.setVisible(true);

        productPrice = new JTextField();
        productPrice.setBounds(370,96,90,35);
        productPrice.setBackground(new Color(255,255,255));
        productPrice.setForeground(new Color(0,0,0));
        productPrice.setEnabled(true);
        productPrice.setFont(new Font("sansserif",0,12));
        productPrice.setText("");
        productPrice.setVisible(true);

        //adding components to contentPane panel
        contentPane.add(btnBack);
        contentPane.add(btnCommit);
        contentPane.add(comboBoxDelivery);
        contentPane.add(label1);
        contentPane.add(label2);
        contentPane.add(label3);
        contentPane.add(label4);
        contentPane.add(productAmount);
        contentPane.add(productList);
        contentPane.add(productPrice);


        productList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String choosedValue = productList.getSelectedValue().toString();
                choosedProduct = hashMap.get(choosedValue);

                // TODO: 4/2/2018 push to bd  
            }
        });

        fillProductList();

    btnCommit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer amount = Integer.valueOf(productAmount.getText().toString());
            Integer price = Integer.valueOf(productPrice.getText().toString());
            productService.pushProductToDeivery(choosedProduct,transferSupply,amount,price);

        }
    });


        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }



    private void fillProductList(){
        try {
            List<Product> products = productService.getDao().getAll();
            final DefaultListModel defaultTableModel = new DefaultListModel();

            for(Product product:products)
                defaultTableModel.addElement(product.getProduct_title());

            productList.setModel(defaultTableModel);

        }catch (Exception e){
            e.printStackTrace();
        }
    }






}