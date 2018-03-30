package dao;

import utils.EntityRetriever;
import model.PaidBonus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaidBonusDao {
    private static final String SELECT_ALL = "SELECT * FROM paid_bonus";
    private static final String SELECT_BY_ID = "SELECT * FROM paid_bonus WHERE id = ?";
    private static final String CREATE = "INSERT INTO paid_bonus (size, date, employee_id)\n" +
            "VALUES (?, ?, ?);";
    private static final String SELECT_BY_EMPLOYEE = "SELECT * FROM paid_bonus Where employee_id = ? ";



    private Connection connection;

    public PaidBonusDao(Connection connection) {
        this.connection = connection;
    }

    public List<PaidBonus> findAll() {
        List<PaidBonus> paidBonusList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                paidBonusList.add(EntityRetriever.retrievePaidBonus(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paidBonusList;
    }

    public PaidBonus findById(Integer id) {
        PaidBonus paidBonus = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                 paidBonus = EntityRetriever.retrievePaidBonus(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paidBonus;
    }

    public boolean create(PaidBonus paidBonus) {
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE)) {

            statement.setInt(1, (int) (paidBonus.getSize()*100));
            statement.setDate(2, paidBonus.getDate());
            statement.setInt(3, paidBonus.getEmployeeId());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<PaidBonus> findByEmployeeId(int employeeId){
        List<PaidBonus> paidBonusList = new ArrayList<>();
        try (PreparedStatement statement
                     = connection.prepareStatement(SELECT_BY_EMPLOYEE)) {
            statement.setInt(1, employeeId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                paidBonusList.add(EntityRetriever.retrievePaidBonus(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paidBonusList;
    }


    public void close() throws Exception {
        connection.close();
    }
}
