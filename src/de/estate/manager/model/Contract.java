package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;
import java.util.Date;

public class Contract {

    private static final String createSQL = "SELECT * FROM CONTRACT WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO CONTRACTS (DATE , PLACE) VALUES (?, ?)";
    private static final String updateSQL = "UPDATE CONTRACTS SET DATE = ?, PLACE = ? WHERE ID = ?";

    protected int id = -1;

    protected String date;

    protected String place;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public static Contract load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Contract contract = new Contract();
                contract.setId(id);
                contract.setDate(result.getString("date"));
                contract.setPlace(result.getString("place"));

                result.close();
                statement.close();
                return contract;
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

                statement.setString(1, getDate());
                statement.setString(2, getPlace());

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setString(1, getDate());
                statement.setString(2, getPlace());
                statement.setInt(3, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

