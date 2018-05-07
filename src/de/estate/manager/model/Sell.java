package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;

public class Sell {

    private static final String createSQL = "SELECT * FROM SELL WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO SELLS (PERSON, HOUSE, PURCHASE)" +
            " VALUES ( ?, ?, ?)";
    private static final String updateSQL = "UPDATE SELLS SET PERSON = ?, HOUSE = ?," +
            " PURCHASE = ?  WHERE id = ?";

    private int id = -1;

    private Person person;

    private House house;

    private Purchase purchase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public static Sell load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Sell ts = new Sell();
                ts.setId(id);

                 ts.setPerson(Person.load(result.getInt("person")));
                 ts.setHouse(House.load(result.getInt("house")));
                 ts.setPurchase(Purchase.load(result.getInt("purchase")));



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

                statement.setInt(1,getPerson().getId());
                statement.setInt(2,getHouse().getId());
                statement.setInt(3,getPurchase().getId());

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setInt(1,getPerson().getId());
                statement.setInt(2,getHouse().getId());
                statement.setInt(3,getPurchase().getId());

                statement.setInt(4, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}