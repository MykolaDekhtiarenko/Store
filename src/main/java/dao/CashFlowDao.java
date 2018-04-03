package dao;

import model.CashFlow;
import utils.EntityRetriever;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CashFlowDao implements AutoCloseable {

    private Connection connection;

    private static final String selTable = "SELECT * FROM cash_flow WHERE date > ? AND date < ?";
    private static final String createCashFlow = "INSERT INTO cash_flow VALUES ( ?, ?)";

    public CashFlowDao(Connection connection) {
        this.connection = connection;
    }

    public List<CashFlow> getCashFlowInRange (Date minDate, Date maxDate) {
        List<CashFlow> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(selTable)){
            statement.setDate(1, minDate);
            statement.setDate(2, maxDate);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {

                result.add(EntityRetriever.retrieveCashFlow(rs));
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return result;
    }

    public boolean create(CashFlow cashFlow) {
        try (PreparedStatement statement
                     = connection.prepareStatement(createCashFlow)) {

            statement.setString(1, String.valueOf(cashFlow.getDate()));
            statement.setString(1, String.valueOf(cashFlow.getAmount()));
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

}
