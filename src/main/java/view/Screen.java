package view;


import lombok.Getter;

import javax.swing.*;
import java.awt.*;
@Getter
public class Screen {


    private JFrame frame = new JFrame("Store");
    private DepartmentViewController departmentView = new DepartmentViewController(this);
    private EmployeeViewController employeeView = new EmployeeViewController(this);
    private StatisticsViewController2 statisticsView = new StatisticsViewController2(this);
    private SaldoViewController saldoView = new SaldoViewController(this);

    public void createAndShowGUI() {
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 400));
        frame.add(employeeView.getContentView());
        frame.pack();
        frame.setVisible(true);
    }


    public void showAnotherView(Controller controllerToRemove, Controller controllerToSet){
        frame.remove(controllerToRemove.getContentView());
        frame.invalidate();
        frame.add(controllerToSet.getContentView());
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
