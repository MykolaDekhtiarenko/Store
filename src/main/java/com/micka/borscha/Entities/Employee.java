package com.micka.borscha.Entities;

import com.micka.borscha.DAO.Identified;


public class Employee implements Identified<Integer> {

    private int id;
    private String employee_name;
    private int employee_salary;

    public String getEmployee_name() {
        return employee_name;
    }

    public int getEmployee_salary() {
        return employee_salary;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public void setEmployee_salary(int employee_salary) {
        this.employee_salary = employee_salary;
    }

    @Override
    public Integer getId() {
        return id;
    }


}
