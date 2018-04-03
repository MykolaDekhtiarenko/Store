package dao;

import utils.Config;

public abstract class DaoFactory {

    public abstract DepartmentDao createDepartmentDao();
    public abstract EmployeeDao createEmployeeDao();
    public abstract PaidSalaryDao createPaidSalaryDao();
    public abstract PaidBonusDao createPaidBonusDao();
    public abstract CashFlowDao createCashFlowDao();

    public static DaoFactory getInstance() {
        String className = Config.getInstance().getFactoryClassName();
        DaoFactory factory = null;
        try {
            factory = (DaoFactory) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }
}