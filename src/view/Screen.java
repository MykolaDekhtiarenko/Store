package view;


import javax.swing.*;
import java.awt.*;

public class Screen {


    private JFrame frame = new JFrame("Store");
    private DepartmentViewController departmentView = new DepartmentViewController(this);
    private EmployeeViewController employeeView = new EmployeeViewController(this);

    public void createAndShowGUI() {

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 400));

        frame.add(employeeView.getContentView());

        frame.pack();
        frame.setVisible(true);
    }


    public void showDepartmentView(){
        frame.remove(employeeView.getContentView());
        frame.invalidate();
        frame.add(departmentView.getContentView());
        frame.validate();
        frame.repaint();
    }

    public void showEmployeeView(){
        frame.remove(departmentView.getContentView());
        frame.invalidate();
        frame.add(employeeView.getContentView());
        frame.validate();
        frame.repaint();
    }

    public void update(){
        employeeView.refresh();
        departmentView.refresh();
    }
}
