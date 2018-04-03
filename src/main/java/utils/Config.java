package utils;

import dao.impl.JDBCDaoFactory;

public class Config {

    private String url = "jdbc:mysql://localhost:3306/store";
    private String user = "root";
    private String pass= "admin";
    private String factoryClassName = JDBCDaoFactory.class.getName();





    private static class Holder{
         private static Config INSTANCE = new Config();
    }

    public static Config getInstance(){
        return Holder.INSTANCE;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
    public String getFactoryClassName() {
        return factoryClassName;
    }

}