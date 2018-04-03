package com.micka.borscha.Utils;

import javax.swing.*;
import java.awt.*;

public class ExceptionDialog {

    private static JDialog d;
    private Exception exception;
    public ExceptionDialog(Exception e){

        this.exception = e;
        JFrame f= new JFrame();
        d = new JDialog(f , "Dialog Example", true);
        d.setLayout( new FlowLayout() );
        d.add( new JLabel ("Record is already exist"));

        d.setSize(300,300);
        d.setVisible(true);

    }

}
