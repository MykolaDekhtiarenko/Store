package service;

import dao.DaoFactory;
import dao.DepartmentDao;
import model.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentDao departmentDao;

    public DepartmentService(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    public Department findById(Integer id) {
        return departmentDao.findById(id);
    }

    public boolean create(Department department) {
        return departmentDao.create(department);
    }

    public boolean update(Department currentDepartment) {
        return departmentDao.update(currentDepartment);
    }

    private static class Holder{
        private static DepartmentService INSTANCE = new DepartmentService(DaoFactory.getInstance().createDepartmentDao());
    }

    public static DepartmentService getInstance(){
        return DepartmentService.Holder.INSTANCE;
    }
}
