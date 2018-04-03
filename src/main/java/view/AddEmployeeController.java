package view;

import model.Department;
import model.Employee;
import service.DepartmentService;
import service.EmployeeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class AddEmployeeController extends JDialog {
    private JButton createButton;
    private JButton cancelButton;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField salary;
    private JComboBox department;
    private JPanel contentPane;

    private Screen screen;

    private List<Department> departmentList;

    public AddEmployeeController(Screen screen) {
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
        if(salaryIsFine()&&hasDepartment()&&nameIsFine()&&lastNameIsFine()){
            Employee employee = Employee.builder()
                    .firstName(firstName.getText())
                    .lastName(lastName.getText())
                    .salary(Double.parseDouble(salary.getText()))
                    .departmentId(departmentList.get(department.getSelectedIndex()).getId())
                    .build();
            EmployeeService.getInstance().create(employee);
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

    private boolean salaryIsFine(){
        String salaryStr = salary.getText();
        Double salary = null;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong number format!");
            return false;
        }
        return true;
    }

    private boolean nameIsFine(){
        if(firstName.getText().length()<1){
            JOptionPane.showMessageDialog(null, "Last name can't be empty!");
            return false;
        }
        return true;
    }

    private boolean lastNameIsFine(){
        if(lastName.getText().length()<1){
            JOptionPane.showMessageDialog(null, "Name can't be empty!");
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
