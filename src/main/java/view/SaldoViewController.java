package view;

import dao.DaoFactory;
import model.CashFlow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class SaldoViewController implements Controller{
    private JPanel contentView;
    private JTextField fromField;
    private JTextField toField;
    private JButton SHOWButton;
    private JList resultList;
    private JButton goToEmployeeViewButton;
    private JButton goToDepartmentViewButton;
    private JButton goToStatisticViewButton;

    private Screen screen;

    public SaldoViewController(Screen screen) {
        this.screen = screen;
        initialize();
    }

    public JPanel getContentView() {
        return contentView;
    }

    private void initialize() {
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        resultList.setModel(listModel);
        SHOWButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                countSaldo(resultList, listModel, toField, fromField);
            }
        });

        goToEmployeeViewButton.addActionListener(e -> goToEmployeeView());
        goToDepartmentViewButton.addActionListener(e -> goToDepartmentView());
        goToStatisticViewButton.addActionListener(e -> goToStatisticsView());
    }


    public void countSaldo(JList list, DefaultListModel listModel, JTextField txtTo, JTextField txtFrom) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

        Date minDate = Date.valueOf(LocalDate.parse(txtFrom.getText(), formatter));
        Date maxDate = Date.valueOf(LocalDate.parse(txtTo.getText(), formatter));
        List<CashFlow> result = DaoFactory.getInstance().createCashFlowDao().getCashFlowInRange(minDate, maxDate);

        setListModel(list, result, listModel);

    }

    private void setListModel(JList list, List<CashFlow> result, DefaultListModel listModel) {
        DefaultListModel model = (DefaultListModel) list.getModel();

        for (CashFlow cashFlow: result
             ) {
            model.addElement(cashFlow.toString());
        }
        list.setModel(model);
    }

    private void goToEmployeeView() {
        screen.showAnotherView(this, screen.getEmployeeView());
    }

    private void goToStatisticsView() {
        screen.showAnotherView(this, screen.getStatisticsView());
    }

    private void goToDepartmentView() {
        screen.showAnotherView(this, screen.getDepartmentView());
    }



}
