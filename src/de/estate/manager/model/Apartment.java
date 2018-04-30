package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;

public class Apartment extends Estate {


    private static final String createSQL = "SELECT * FROM APARTMENTS WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO APARTMENTS (ID, FLOOR, RENT, ROOMS, BALCONY, KITCHEN) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE APARTMENTS SET FLOOR = ?, RENT = ?, ROOMS = ?, BALCONY = ?, KITCHEN = ? WHERE ID = ?";


    private int floor;

    private double rent;

    private double rooms;

    private boolean balcony;

    private boolean kitchen;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getRooms() {
        return rooms;
    }

    public void setRooms(double rooms) {
        this.rooms = rooms;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }


    public static Apartment load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Apartment apartment = (Apartment) Estate.load(result.getInt("id"));
                apartment.setBalcony(result.getBoolean("balcony"));
                apartment.setFloor(result.getInt("floor"));
                apartment.setKitchen(result.getBoolean("kitchen"));
                apartment.setRent(result.getDouble("rent"));
                apartment.setRooms(result.getDouble("rooms"));

                result.close();
                statement.close();
                return apartment;
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

                statement.setInt(1, getId());
                statement.setInt(2, getFloor());
                statement.setDouble(3, getRent());
                statement.setDouble(4, getRooms());
                statement.setBoolean(5, isBalcony());
                statement.setBoolean(6, isKitchen());

                statement.executeUpdate();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setInt(1, getFloor());
                statement.setDouble(2, getRent());
                statement.setDouble(3, getRooms());
                statement.setBoolean(4, isBalcony());
                statement.setBoolean(5, isKitchen());
                statement.setInt(6, getId());

                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}