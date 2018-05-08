package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;

public class Estate {
    private static final String createSQL = "SELECT * FROM ESTATES WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO ESTATES (CITY, ZIP, STREET, NUMBER, AREA, AGENT) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE ESTATES SET CITY = ?, ZIP = ?, STREET = ?, NUMBER = ?, AREA = ?, AGENT = ? WHERE ID = ?";

    protected int id = -1;

    protected String city;

    protected int zip;

    protected String street;

    protected int number;

    protected int area;

    protected Agent agent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public static Estate load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Estate estate = new Estate();
                estate.setId(id);
                estate.setCity(result.getString("city"));
                estate.setZip(result.getInt("zip"));
                estate.setArea(result.getInt("area"));
                estate.setStreet(result.getString("street"));
                estate.setNumber(result.getInt("number"));
                estate.setAgent(Agent.load(result.getInt("agent")));

                result.close();
                statement.close();
                return estate;
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

                statement.setString(1, getCity());
                statement.setInt(2, getZip());
                statement.setString(3, getStreet());
                statement.setInt(4, getNumber());
                statement.setInt(5, getArea());
                statement.setInt(6, getAgent().getId());

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setString(1, getCity());
                statement.setInt(2, getZip());
                statement.setString(3, getStreet());
                statement.setInt(4, getNumber());
                statement.setInt(5, getArea());
                statement.setInt(6, getAgent().getId());
                statement.setInt(7, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" + street + " " + number + "\n" + zip + " " + city + " " + area + "\n";
    }
}
