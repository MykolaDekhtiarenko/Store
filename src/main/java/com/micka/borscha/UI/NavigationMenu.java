package com.micka.borscha.UI; /**
*Text genereted by Simple GUI Extension for BlueJ
*/

import com.micka.borscha.DAO.PersistException;
import view.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class NavigationMenu extends JFrame {

	private JMenuBar menuBar;
	private JButton btnOpenOrderTable;
	private JButton btnOpenSupply;
	private JButton btnOpenVendor;
	private JButton kolyaModule;

	//Constructor 
	public NavigationMenu(){

		this.setTitle("GUI_project");
		this.setSize(500,400);
		//menu generate method
		this.setJMenuBar(menuBar);

		//pane with null layout
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(500,400));
		contentPane.setBackground(new Color(192,192,192));


		btnOpenOrderTable = new JButton();
		btnOpenOrderTable.setBounds(147,236,202,70);
		btnOpenOrderTable.setBackground(new Color(214,217,223));
		btnOpenOrderTable.setForeground(new Color(0,0,0));
		btnOpenOrderTable.setEnabled(true);
		btnOpenOrderTable.setFont(new Font("sansserif",0,12));
		btnOpenOrderTable.setText("Order Product");
		btnOpenOrderTable.setVisible(true);

		btnOpenOrderTable.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try{
					new OrderTable();
				}catch (Exception el){
					el.printStackTrace();
				}

			}
		});

		btnOpenSupply = new JButton();
		btnOpenSupply.setBounds(146,140,202,70);
		btnOpenSupply.setBackground(new Color(214,217,223));
		btnOpenSupply.setForeground(new Color(0,0,0));
		btnOpenSupply.setEnabled(true);
		btnOpenSupply.setFont(new Font("sansserif",0,12));
		btnOpenSupply.setText("Open Supply Table");
		btnOpenSupply.setVisible(true);
		btnOpenSupply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					new SupplyTable();
				} catch (PersistException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnOpenVendor = new JButton();
		btnOpenVendor.setBounds(147,46,202,70);
		btnOpenVendor.setBackground(new Color(214,217,223));
		btnOpenVendor.setForeground(new Color(0,0,0));
		btnOpenVendor.setEnabled(true);
		btnOpenVendor.setFont(new Font("sansserif",0,12));
		btnOpenVendor.setText("Open Vendor Table");
		btnOpenVendor.setVisible(true);

		btnOpenVendor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					new VendorTable();
				} catch (PersistException e1) {
					e1.printStackTrace();
				}
				setVisible(false);
				dispose();
			}
		});

		kolyaModule = new JButton();
		kolyaModule.setBounds(147,400,202,70);
		kolyaModule.setBackground(new Color(214,217,223));
		kolyaModule.setForeground(new Color(0,0,0));
		kolyaModule.setEnabled(true);
		kolyaModule.setFont(new Font("sansserif",0,12));
		kolyaModule.setText("Nickolay's work");
		kolyaModule.setVisible(true);

		kolyaModule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Screen mainScreen = new Screen();
				mainScreen.createAndShowGUI();
			}
		});
		//adding components to contentPane panel
		contentPane.add(btnOpenOrderTable);
		contentPane.add(btnOpenSupply);
		contentPane.add(btnOpenVendor);
		contentPane.add(kolyaModule);

		//adding panel to JFrame and seting of window position and close operation
		this.add(contentPane);
		//this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}



}