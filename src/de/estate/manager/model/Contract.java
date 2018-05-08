package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;

public class Contract {

    private static final String createSQL = "SELECT * FROM CONTRACTS WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO CONTRACTS (DATE , PLACE, PERSON) VALUES (?, ?, ?)";
    private static final String updateSQL = "UPDATE CONTRACTS SET DATE = ?, PLACE = ?, PERSON = ? WHERE ID = ?";

    protected int id = -1;

    protected Date date;

    protected String place;

    protected Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean hasPerson() {
        return person != null;
    }
    public static Contract load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Contract contract = new Contract();
                contract.setId(id);
                contract.setDate(result.getDate("date"));
                contract.setPlace(result.getString("place"));
                contract.setPerson(Person.load(result.getInt("person")));

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
                PreparedStatement statement;
                if (hasPerson()) {
                    statement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                    statement.setDate(1, getDate());
                    statement.setString(2, getPlace());
                    statement.setInt(3, getPerson().getId());
                } else {
                    statement = con.prepareStatement("INSERT INTO CONTRACTS (DATE , PLACE) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setDate(1, getDate());
                    statement.setString(2, getPlace());
                }

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setDate(1, getDate());
                statement.setString(2, getPlace());
                statement.setInt(3, getPerson().getId());
                statement.setInt(4, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

