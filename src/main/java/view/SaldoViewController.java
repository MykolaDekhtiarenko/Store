package view;

import javax.swing.*;

public class SaldoViewController implements Controller{
    private JPanel contentView;
    private JTextField fromField;
    private JTextField toField;
    private JButton SHOWButton;
    private JList resultList;

    private Screen screen;

    public SaldoViewController(Screen screen) {
        System.out.println(screen);
        this.screen = screen;
        initialize();
    }

    public JPanel getContentView() {
        return contentView;
    }

    private void initialize() {

    }
}
