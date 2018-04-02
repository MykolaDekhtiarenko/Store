package view;

import dao.impl.JDBCDaoFactory;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class StatisticsViewController implements Controller{

    private JPanel contentView;
    private JTable table;
    private Connection conn;
    private JButton btnProductStatistics;
    private JButton btnSellingStatistics;
    private Screen screen;


    public StatisticsViewController(Screen screen) {
        this.screen = screen;
        initialize();
    }

    /**
     * Initialize the contents of the contentView.
     */
    private void initialize() {
        conn = JDBCDaoFactory.getConnection();
        contentView = new JPanel();

        JButton btnLoadProducts = new JButton("Product list");
        btnLoadProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT department.name,product.name,price.price FROM (product JOIN department ON product.department_id = department.id) JOIN price ON product.id = price.product_id ORDER BY department.name";
                    PreparedStatement pst = conn.prepareStatement(query);
                    ResultSet rs = pst.executeQuery();
                    table.setModel(resultSetToTableModel(rs));
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        });
        btnLoadProducts.setBounds(10, 25, 128, 23);
        contentView.add(btnLoadProducts);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 47, 632, 300);
        contentView.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        btnProductStatistics = new JButton("Product statistics");
        btnProductStatistics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT department.name,product.name,price.price,product.amount FROM (product JOIN department ON product.department_id = department.id) JOIN price ON product.id = price.product_id ORDER BY department.name";
                    PreparedStatement pst = conn.prepareStatement(query);
                    ResultSet rs = pst.executeQuery();
                    table.setModel(resultSetToTableModel(rs));
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        });
        btnProductStatistics.setBounds(134, 25, 140, 23);
        contentView.add(btnProductStatistics);

        btnSellingStatistics = new JButton("Selling statistics");
        btnSellingStatistics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT department.name,purchase.date,product.name,product_has_purchase.amount FROM ((product JOIN product_has_purchase ON product.id = product_has_purchase.product_id) JOIN purchase ON purchase.id = product_has_purchase.purchase_id) JOIN department ON product.department_id = department.id ORDER BY department.name,purchase.date";
                    PreparedStatement pst = conn.prepareStatement(query);
                    ResultSet rs = pst.executeQuery();
                    table.setModel(resultSetToTableModel(rs));
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        });
        btnSellingStatistics.setBounds(273, 25, 140, 23);
        contentView.add(btnSellingStatistics);
    }

    private static TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<String>();


            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }


            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

            while (rs.next()) {
                Vector<Object> newRow = new Vector<Object>();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public JPanel getContentView() {
        return contentView;
    }

    private void goToDepartmentView() {
        screen.showAnotherView(this, screen.getDepartmentView());
    }

    private void goToEmployeeView() {
        screen.showAnotherView(this, screen.getDepartmentView());
    }

}
