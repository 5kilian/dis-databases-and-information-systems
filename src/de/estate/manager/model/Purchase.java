package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;

public class Purchase extends Contract{


    private static final String createSQL = "SELECT * FROM PURCHASE WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO PURCHASES (INSTALLMENTS, RATE," +
            " DATE, PLACE) VALUES ( ?, ?, ?, ? )";
    private static final String updateSQL = "UPDATE PURCHASES SET INSTALLMENTS = ?, RATE = ?," +
            " DATE = ?, PLACE = ? WHERE id = ?";

    private int installments;

    private int rate;

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


    public static Purchase load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Purchase ts = new Purchase();
                ts.setId(id);

                ts.setInstallments(result.getInt("installments"));
                ts.setRate(result.getInt("rate"));

                ts.setDate(result.getDate("date"));
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
                PreparedStatement statement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, getInstallments());
                statement.setInt(2, getRate());

                // statement.setDate(3, getDate());
                statement.setString(4, getPlace());


                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                // statement.setDate(1, getInstallments());
                statement.setInt(2, getRate());

                // statement.setDate(3, getDate());
                statement.setString(4, getPlace());

                statement.setInt(5, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}