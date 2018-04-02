package view;

import model.Department;
import model.Employee;
import service.DepartmentService;
import service.EmployeeService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class DepartmentViewController implements Controller{
    private JList departmentList;
    private JButton goToEmployeeView;
    private JPanel contentView;
    private JButton renameDepartmentButton;
    private JList employeeList;
    private JButton createNewDepartmentButton;
    private JLabel departmentName;
    private JButton goToStatisticsView;
    private Screen screen;

    private List<Department> dataSource;
    private Department currentDepartment;

    public DepartmentViewController(Screen screen) {
        this.screen = screen;
        reloadDepartmentListData();

        createNewDepartmentButton.addActionListener(e -> onCreateNewDepartment());
        renameDepartmentButton.addActionListener(e -> onRenameDepartment());
        departmentList.addListSelectionListener(e -> onUpdateSelectedListItem(e));
        departmentList.setSelectedIndex(0);
        goToEmployeeView.addActionListener(e -> goToEmployeeView());
        goToStatisticsView.addActionListener(e -> goToStatisticsView());

    }

    public void refresh() {
        reloadDepartmentListData();
        repaintDetails();
    }

    private void onUpdateSelectedListItem(ListSelectionEvent e) {
        int index = departmentList.getSelectedIndex();
        if (index < 0) {
            return;
        }
        currentDepartment = dataSource.get(index);
        repaintDetails();
    }

    private void repaintDetails() {
        departmentName.setText(currentDepartment.getName());
        reloadEmployeeListData(currentDepartment.getId());

    }

    private void reloadDepartmentListData() {
        reloadDataSource();
        DefaultListModel<Department> dlm = new DefaultListModel<>();
        for (Department department : dataSource) {
            dlm.addElement(department);
        }
        departmentList.setModel(dlm);
    }

    private void reloadEmployeeListData(int departmentId) {
        DefaultListModel<Employee> dlm = new DefaultListModel<>();
        for (Employee employee : EmployeeService.getInstance().findByDepartmentId(departmentId)) {
            dlm.addElement(employee);
        }
        employeeList.setModel(dlm);
    }

    private void reloadDataSource() {
        dataSource = DepartmentService.getInstance().findAll();
    }

    private void onCreateNewDepartment() {
        String newDepartmentName = JOptionPane.showInputDialog(null, "Enter name of new department:");
        if (newDepartmentName.length() < 1) {
            JOptionPane.showMessageDialog(null, "Department title can't be empty!");
            return;
        }
        Department newDepartment = Department.builder()
                .name(newDepartmentName)
                .build();
        DepartmentService.getInstance().create(newDepartment);
        refresh();
    }

    private void onRenameDepartment() {
        String newDepartmentName = JOptionPane.showInputDialog(null, "Enter new title for the "+currentDepartment.toString()+":");
        if (newDepartmentName.length() < 1) {
            JOptionPane.showMessageDialog(null, "Department title can't be empty!");
            return;
        }
        currentDepartment.setName(newDepartmentName);
        DepartmentService.getInstance().update(currentDepartment);
        refresh();
    }

    public JPanel getContentView() {
        return contentView;
    }

    private void goToEmployeeView() {
        screen.showAnotherView(this, screen.getDepartmentView());
    }

    private void goToStatisticsView() {
        screen.showAnotherView(this, screen.getStatisticsView());
    }


}
