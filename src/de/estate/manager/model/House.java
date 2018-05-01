package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;

public class House extends Estate {

    private static final String createSQL = "SELECT * FROM HOUSES WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO HOUSES (ID, FLOORS, PRICE, GARDEN) VALUES (?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE HOUSES SET FLOORS = ?, PRICE = ?, GARDEN = ? WHERE ID = ?";

    private int floors;

    private double price;

    private boolean garden;

    public House() {

    }

    /**
     * Copy Constructor
     */
    public House(Estate estate) {
        id = estate.id;
        city = estate.city;
        zip = estate.zip;
        street = estate.street;
        number = estate.number;
        area = estate.area;
        agent = estate.agent;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public static House load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                House house = new House(Estate.load(result.getInt("id")));

                house.setFloors(result.getInt("floors"));
                house.setPrice(result.getDouble("price"));
                house.setGarden(result.getBoolean("balcony"));

                result.close();
                statement.close();
                return house;
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
                statement.setInt(2, getFloors());
                statement.setDouble(3, getPrice());
                statement.setBoolean(4, isGarden());

                statement.executeUpdate();
                statement.close();
            } else {
                super.save();
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setInt(1, getFloors());
                statement.setDouble(2, getPrice());
                statement.setBoolean(3, isGarden());
                statement.setInt(4, getId());

                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
