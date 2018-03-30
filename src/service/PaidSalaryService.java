package service;

import dao.DaoFactory;
import dao.PaidSalaryDao;
import model.PaidSalary;

import java.util.List;

public class PaidSalaryService {

    private PaidSalaryDao paidSalaryDao;

    public PaidSalaryService(PaidSalaryDao paidSalaryDao) {
        this.paidSalaryDao = paidSalaryDao;
    }

    public List<PaidSalary> findAll() {
        return paidSalaryDao.findAll();
    }

    public PaidSalary findById(Integer id) {
        return paidSalaryDao.findById(id);
    }

    public boolean create(PaidSalary paidSalary) {
        return paidSalaryDao.create(paidSalary);
    }

    public List<PaidSalary> findByEmployeeId(int employeeId) {
        return paidSalaryDao.findByEmployeeId(employeeId);
    }

    private static class Holder{
        private static PaidSalaryService INSTANCE = new PaidSalaryService(DaoFactory.getInstance().createPaidSalaryDao());
    }

    public static PaidSalaryService getInstance(){
        return PaidSalaryService.Holder.INSTANCE;
    }
}
