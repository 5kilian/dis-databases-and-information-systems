package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;

public class Purchase extends Contract{


    private static final String createSQL = "SELECT * FROM PURCHASE WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO PURCHASES (INSTALLMENTS, RATE," +
            " DATE, PLACE, SELL) VALUES ( ?, ?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE PURCHASES SET INSTALLMENTS = ?, RATE = ?," +
            " DATE = ?, PLACE = ?, SELL = ? WHERE id = ?";

    private int installments;

    private int rate;

    private Sell sell;

    public Purchase() {

    }

    /**
     * Copy Constructor
     */
    public Purchase(Contract contract) {

        id = contract.id;
        date = contract.date;
        place = contract.place;


    }


    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Sell getSell() {
        return sell;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }


    public static Purchase load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Purchase ts = new Purchase(Contract.load(id));
                ts.setId(id);

                ts.setInstallments(result.getInt("installments"));
                ts.setRate(result.getInt("rate"));
                ts.setSell(Sell.load(result.getInt("sell")));

                ts.setDate(result.getString("date"));
                ts.setPlace(result.getString("place"));


                result.close();
                statement.close();
                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save() {
        Connection con = DB2Connection.getConnection();

        try {
            if (getId() == -1) {
                super.save();
                PreparedStatement statement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, getInstallments());
                statement.setInt(2, getRate());
                 statement.setString(3, getDate());
                statement.setString(4, getPlace());
                statement.setInt(5, getSell().getId());

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                super.save();
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setInt(1, getInstallments());
                statement.setInt(2, getRate());

                statement.setString(3, getDate());
                statement.setString(4, getPlace());
                statement.setInt(5, getSell().getId());
                statement.setInt(6, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}