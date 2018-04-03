package com.micka.borscha;

import com.micka.borscha.Entities.Employee;
import com.micka.borscha.Entities.*;
import com.micka.borscha.UI.LoginUI;
import com.micka.borscha.UI.NavigationMenu;
import view.Screen;

import javax.swing.*;


public class Main  {


  public   static Employee employee;

    public static void main(String[] args) {

       System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {


                // TODO: 4/3/2018 AFTER ALL MERGE ADD EMPLOYEE TO DB

             try {
                 // TODO: 4/3/2018 SWITCH HERE TO LOGIN UI IN com.micka.borscha.UI.LoginUI
                 // TODO: 4/3/2018 IN TEXT FIELD INPUT ID OF EMPLOYEE TO GET ACCESS TO NAVMENU
                   new NavigationMenu();

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


    }



}
