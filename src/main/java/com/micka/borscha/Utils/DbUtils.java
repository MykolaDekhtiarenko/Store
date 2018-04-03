package com.micka.borscha.Utils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbUtils {

    private static final String DB_DRIVER="com.mysql.jdbc.Driver";
    private static final String DB_URL="jdbc:mysql://localhost:3306/store";
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="";

    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            System.out.println("Connection Ok");
        } catch (ClassNotFoundException  | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    public static java.sql.Date convert(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    public static Date convertFromString(String stringDate) {
        Date date = new Date();
        try {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        date = formatter.parse(stringDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void showError(Object error) {
        String mainMessage = null;
        String title = null;
        if (error instanceof String) {
            mainMessage = (String) error;
            title = "Error!";
        } else if (error instanceof Exception) {
            Exception exceptionError = (Exception) error;
            mainMessage = "Message: " + exceptionError.getMessage();
            title = exceptionError.getClass().getName();
        }

        JOptionPane.showMessageDialog(null, mainMessage, title, JOptionPane.ERROR_MESSAGE);
    }

}
