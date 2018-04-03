package com.micka.borscha.UI; /**
*Text genereted by Simple GUI Extension for BlueJ
*/

import com.micka.borscha.DAO.PersistException;
import com.micka.borscha.Services.LoginService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class LoginUI extends JFrame{

	private JMenuBar menuBar;
	private JLabel label1;
	private JLabel label3;
	private JButton loginButtonApply;
	private JTextField loginUsernameRF;
	private JPanel panel1;
	private LoginService loginService = new LoginService();

	//Constructor 
	public LoginUI() throws PersistException {

		this.setTitle("GUI_project");
		this.setSize(500,400);
		//menu generate method
		generateMenu();
		this.setJMenuBar(menuBar);

		//pane with null layout
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(500,400));
		contentPane.setBackground(new Color(192,192,192));


		label3 = new JLabel();
		label3.setBounds(29,30,115,30);
		label3.setBackground(new Color(214,217,223));
		label3.setForeground(new Color(0,0,0));
		label3.setEnabled(true);
		label3.setFont(new Font("sansserif",0,12));
		label3.setText("Input your name:");
		label3.setVisible(true);




		loginUsernameRF = new JTextField();
		loginUsernameRF.setBounds(145,29,266,31);
		loginUsernameRF.setBackground(new Color(255,255,255));
		loginUsernameRF.setForeground(new Color(0,0,0));
		loginUsernameRF.setEnabled(true);
		loginUsernameRF.setFont(new Font("sansserif",0,12));
		loginUsernameRF.setText("");
		loginUsernameRF.setVisible(true);

		panel1 = new JPanel(null);
		panel1.setBorder(BorderFactory.createEtchedBorder(1));
		panel1.setBounds(5,124,494,118);
		panel1.setBackground(new Color(214,217,223));
		panel1.setForeground(new Color(0,0,0));
		panel1.setEnabled(true);
		panel1.setFont(new Font("sansserif",0,12));
		panel1.setVisible(true);


		loginButtonApply = new JButton();
		loginButtonApply.setBounds(186,202,90,35);
		loginButtonApply.setBackground(new Color(214,217,223));
		loginButtonApply.setForeground(new Color(0,0,0));
		loginButtonApply.setEnabled(true);
		loginButtonApply.setFont(new Font("sansserif",0,12));
		loginButtonApply.setText("Apply");
		loginButtonApply.setVisible(true);
		//Set methods for mouse events
		//Call defined methods
		loginButtonApply.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				try {
					loginEmployee(evt);
				} catch (PersistException e) {
					e.printStackTrace();
				}
			}
		});



		//adding components to contentPane panel

		panel1.add(label3);
		contentPane.add(loginButtonApply);
		panel1.add(loginUsernameRF);
		contentPane.add(panel1);

		//adding panel to JFrame and seting of window position and close operation
		this.add(contentPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	//Method mouseClicked for loginButtonApply
	private void loginEmployee(MouseEvent evt) throws PersistException {
		boolean allowed = loginService.loginById(Integer.valueOf(loginUsernameRF.getText()));
		if (allowed) {
			new NavigationMenu();
			setVisible(false);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Wrong employee id!");

		}
	}

	//method for generate menu
	public void generateMenu(){
		menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu tools = new JMenu("Tools");
		JMenu help = new JMenu("Help");

		JMenuItem open = new JMenuItem("Open   ");
		JMenuItem save = new JMenuItem("Save   ");
		JMenuItem exit = new JMenuItem("Exit   ");
		JMenuItem preferences = new JMenuItem("Preferences   ");
		JMenuItem about = new JMenuItem("About   ");


		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		tools.add(preferences);
		help.add(about);

		menuBar.add(file);
		menuBar.add(tools);
		menuBar.add(help);
	}


}