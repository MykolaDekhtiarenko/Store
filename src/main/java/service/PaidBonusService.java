package service;

import dao.CashFlowDao;
import dao.DaoFactory;
import dao.PaidBonusDao;
import model.CashFlow;
import model.PaidBonus;

import java.util.List;

public class PaidBonusService {

    private PaidBonusDao paidBonusDao;
    private CashFlowDao cashFlowDao;

    public PaidBonusService(PaidBonusDao paidBonusDao) {
        this.paidBonusDao = paidBonusDao;
    }

    public List<PaidBonus> findAll() {
        return paidBonusDao.findAll();
    }

    public PaidBonus findById(Integer id) {
       return paidBonusDao.findById(id);
    }

    public boolean create(PaidBonus paidBonus) {
        cashFlowDao.create(
                CashFlow.builder()
                .amount((int)paidBonus.getSize()*100)
                .date(paidBonus.getDate())
                .build()
        );
        return paidBonusDao.create(paidBonus);
    }

    public List<PaidBonus> findByEmployeeId(int employeeId) {
        return paidBonusDao.findByEmployeeId(employeeId);
    }


    private static class Holder{
        private static PaidBonusService INSTANCE = new PaidBonusService(DaoFactory.getInstance().createPaidBonusDao());
    }

    public static PaidBonusService getInstance(){
        return PaidBonusService.Holder.INSTANCE;
    }
}
