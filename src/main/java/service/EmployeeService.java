package service;

import dao.DaoFactory;
import dao.EmployeeDao;
import model.Employee;

import java.util.List;

public class EmployeeService {

    private EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }


    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    public Employee findById(Integer id) {
        return employeeDao.findById(id);
    }

    public boolean create(Employee employee) {
        return employeeDao.create(employee);
    }

    public boolean update(Employee currentEmployee) {
        return employeeDao.update(currentEmployee);
    }

    public List<Employee> findByDepartmentId(int departmentId) {
        return employeeDao.findByDepartmentId(departmentId);
    }

    private static class Holder{
        private static EmployeeService INSTANCE = new EmployeeService(DaoFactory.getInstance().createEmployeeDao());
    }

    public static EmployeeService getInstance(){
        return EmployeeService.Holder.INSTANCE;
    }
}
