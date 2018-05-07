package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;
import java.util.Date;


public class Tenancy extends Contract {

    private static final String createSQL = "SELECT * FROM TENANCY WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO TENANCIES (START, DURATION, COST," +
            " DATE, PLACE, RENT) VALUES ( ?, ?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE TENANCIES SET START = ?, DURATION = ?," +
            " COST = ?, DATE = ?, PLACE = ?, RENT = ? WHERE id = ?";


    private String start;

    private int duration;

    private double cost;

    private Rent rent;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }


    public Tenancy() {

    }


    /**
     * Copy Constructor
     */
    public Tenancy(Contract contract) {

        id = contract.id;
        date = contract.date;
        place = contract.place;

    }


    public static Tenancy load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Tenancy ts = new Tenancy(Contract.load(id));

                ts.setStart(result.getString("start"));
                ts.setDuration(result.getInt("duration"));
                ts.setCost(result.getDouble("cost"));
                ts.setRent(Rent.load(result.getInt("rent")));

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

                statement.setString(1, getStart());
                statement.setInt(2, getDuration());
                statement.setDouble(3, getCost());

                statement.setString(4, getDate());
                statement.setString(5, getPlace());
                statement.setInt(6, getRent().getId());

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

                statement.setString(1, getStart());
                statement.setInt(2, getDuration());
                statement.setDouble(3, getCost());

                statement.setString(4, getDate());
                statement.setString(5, getPlace());
                statement.setInt(6,getRent().getId());

                statement.setInt(7, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}