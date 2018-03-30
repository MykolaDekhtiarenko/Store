package view;

import model.*;
import service.DepartmentService;
import service.EmployeeService;
import service.PaidBonusService;
import service.PaidSalaryService;
import utils.DateComparator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeViewController {
    private JPanel contentView;
    private JList employeesList;
    private JButton goToDepartmentView;
    private JLabel fullName;
    private JLabel salary;
    private JLabel department;
    private JList paymentHistory;
    private JButton newSalary;
    private JButton addEmployee;
    private JButton paySalaryButton;
    private JButton payBonusButton;


    private List<Employee> dataSource;
    private Employee currentEmployee;

    private Screen screen;

    public EmployeeViewController(Screen screen) {
        this.screen=screen;
        reloadEmployeeListData();

        paySalaryButton.addActionListener(e -> onClickPaySalaryEvent());
        payBonusButton.addActionListener(e -> onClickPayBonusEvent());

        employeesList.addListSelectionListener(e -> onUpdateSelectedListItem(e));
        employeesList.setSelectedIndex(0);

        goToDepartmentView.addActionListener(e -> goToDepartmentView());
        newSalary.addActionListener(e -> onClickEditSalaryEvent());
        addEmployee.addActionListener(e -> showAddEmployeeView());
    }

    public JPanel getContentView() {
        return contentView;
    }

    public void refresh() {
        reloadEmployeeListData();
        repaintDetails();
    }

    private void reloadEmployeeListData() {
        reloadDataSource();
        DefaultListModel<Employee> dlm = new DefaultListModel<>();
        for (Employee employee : dataSource) {
            dlm.addElement(employee);
        }
        employeesList.setModel(dlm);
    }

    private void reloadDataSource() {
        dataSource = EmployeeService.getInstance().findAll();
    }


    private void onUpdateSelectedListItem(ListSelectionEvent e) {
        int index = employeesList.getSelectedIndex();
        if (index < 0) {
            return;
        }
        currentEmployee = dataSource.get(index);
        repaintDetails();
    }

    private void repaintDetails() {
        if (currentEmployee == null) {
            return;
        }
        fullName.setText(currentEmployee.getFirstName() + " " + currentEmployee.getLastName());
        salary.setText(currentEmployee.getSalary() + " hryvnias");
        Department d = DepartmentService.getInstance().findById(currentEmployee.getDepartmentId());
        department.setText(d.toString());
        reloadPaymentInfoListData(currentEmployee.getId());
    }

    private void reloadPaymentInfoListData(int employeeId) {
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (String component : collectPaymentHistoryInfo(employeeId)) {
            dlm.addElement(component);
        }
        paymentHistory.setModel(dlm);
    }

    private List<String> collectPaymentHistoryInfo(int employeeId) {
        List<SortableByDate> collectedInfo = new ArrayList<>();
        collectedInfo.addAll(PaidBonusService.getInstance().findByEmployeeId(employeeId));
        collectedInfo.addAll(PaidSalaryService.getInstance().findByEmployeeId(employeeId));
        collectedInfo.sort(new DateComparator());
        return collectedInfo
                        .stream()
                        .map(e -> e.toString())
                        .collect(Collectors.toList());

    }

    private void goToDepartmentView() {
        screen.showDepartmentView();
    }

    private void showAddEmployeeView() {
        AddEmployeeController addEmployeeController = new AddEmployeeController(screen);
    }

    private void onClickEditSalaryEvent() {
        String newSalaryStr = JOptionPane.showInputDialog(null, "Enter new salary for "+currentEmployee.getFirstName()+" "+currentEmployee.getLastName()+":");
        Double newSalary = null;
        try {
            newSalary = Double.parseDouble(newSalaryStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong number format!");
            return;
        }
        currentEmployee.setSalary(newSalary);
        EmployeeService.getInstance().update(currentEmployee);
        repaintDetails();
    }

    private void onClickPaySalaryEvent() {
        String salaryStr = JOptionPane.showInputDialog(null, "Set amount:", ""+currentEmployee.getSalary());
        Double salary = null;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong number format!");
            return;
        }
        PaidSalary paidSalary = PaidSalary.builder()
                .salary(salary)
                .date(new Date(Calendar.getInstance().getTimeInMillis()))
                .employeeId(currentEmployee.getId())
                .build();
        PaidSalaryService.getInstance().create(paidSalary);
        repaintDetails();
    }

    private void onClickPayBonusEvent() {
        String bonusStr = JOptionPane.showInputDialog(null, "Set bonus:");
        Double bonus = null;
        try {
            bonus = Double.parseDouble(bonusStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong number format!");
            return;
        }
        PaidBonus paidBonus = PaidBonus.builder()
                .size(bonus)
                .date(new Date(Calendar.getInstance().getTimeInMillis()))
                .employeeId(currentEmployee.getId())
                .build();
        PaidBonusService.getInstance().create(paidBonus);
        repaintDetails();
    }

}
