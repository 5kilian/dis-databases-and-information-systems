package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;


public class Tenancy extends Contract {

    private static final String createSQL = "SELECT * FROM TENANCIES WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO TENANCIES (ID, START, DURATION, COST, APARTMENT) VALUES (?, ?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE TENANCIES SET START = ?, DURATION = ?, COST = ?, APARTMENT = ? WHERE ID = ?";


    private Date start;

    private int duration;

    private double cost;

    private Apartment apartment;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
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

                ts.setStart(result.getDate("start"));
                ts.setDuration(result.getInt("duration"));
                ts.setCost(result.getDouble("cost"));
                ts.setApartment(Apartment.load(result.getInt("rent")));

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

                statement.setInt(1, getId());
                statement.setDate(2, getDate());
                statement.setInt(3, getDuration());
                statement.setDouble(4, getCost());
                statement.setInt(5, getApartment().getId());

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

                statement.setDate(1, getStart());
                statement.setInt(2, getDuration());
                statement.setDouble(3, getCost());
                statement.setInt(4, getApartment().getId());
                statement.setInt(5, getId());

                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
