package view;

import dao.impl.JDBCDaoFactory;
import model.Department;
import model.Employee;
import service.DepartmentService;
import service.EmployeeService;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class AddProductController extends JDialog{
    private JPanel contentPane;
    private JTextField firstName;
    private JTextField amount;
    private JTextField minAmount;
    private JComboBox department;
    private JButton createButton;
    private JButton cancelButton;
    private JTextField price;
    private Screen screen;
    private Connection conn;

    private List<Department> departmentList;

    public AddProductController(Screen screen) {
        this.screen = screen;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(createButton);
        initComponents();

        createButton.addActionListener(e -> onCreate());

        cancelButton.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        this.setPreferredSize(new Dimension(320, 220));
        this.pack();
        this.setVisible(true);
    }


    private void onCreate() {
        if(minAmountIsFine()&&hasDepartment()&&nameIsFine()&&amountIsFine()&priceIsFine()){
            conn = JDBCDaoFactory.getConnection();
            try {
                String query = "INSERT INTO product (`name`,`amount`,`min_amount`,`department_id`) values  ('"+firstName.getText()+"',"+amount.getText()+","+minAmount.getText()+","+departmentList.get(department.getSelectedIndex()).getId()+")";
                PreparedStatement pst = conn.prepareStatement(query);
                 pst.execute();
                 String query2 = "INSERT INTO `price` (`date`,`price`,`product_id`) values (now(),"+price.getText()+",LAST_INSERT_ID()); ";
                PreparedStatement pst2 = conn.prepareStatement(query2);
                pst2.execute();
            } catch (Exception x) {
                x.printStackTrace();
            }
            dispose();
            screen.update();
        }

    }

    private void onCancel() {
        dispose();
    }

    private void initComponents(){
        departmentList = DepartmentService.getInstance().findAll();
        DefaultComboBoxModel<Department> dlm = new DefaultComboBoxModel<>();
        for (Department d : departmentList) {
            dlm.addElement(d);
        }
        department.setModel(dlm);
    }

    private boolean minAmountIsFine(){
        String minAmountStr = minAmount.getText();
        Integer minAmount = null;
        try {
            minAmount = Integer.parseInt(minAmountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong number format!");
            return false;
        }
        return true;
    }
    private boolean priceIsFine(){
        String amountStr = price.getText();
        Integer amount = null;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong number format!");
            return false;
        }
        return true;
    }
    private boolean nameIsFine(){
        if(firstName.getText().length()<1){
            JOptionPane.showMessageDialog(null, "name can't be empty!");
            return false;
        }
        return true;
    }

    private boolean amountIsFine(){
        String amountStr = amount.getText();
        Integer amount = null;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong number format!");
            return false;
        }
        return true;
    }

    private boolean hasDepartment(){
        if(departmentList.size()<1){
            JOptionPane.showMessageDialog(null, "You should have department to create employee!");
            return false;
        }
        return departmentList.size()>0;
    }

}
