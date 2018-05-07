package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;

public class Rent {

    private static final String createSQL = "SELECT * FROM RENT WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO RENTS (APARTMENT, TENANCY, PERSON) VALUES ( ?, ?, ?)";
    private static final String updateSQL = "UPDATE RENTS SET APARTMENT = ?, TENANCY = ?, PERSON = ? WHERE ID = ?";

    private int id = -1;

    private Apartment apartment;

    private Tenancy tenancy;

    private Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Tenancy getTenancy() {
        return tenancy;
    }

    public void setTenancy(Tenancy tenancy) {
        this.tenancy = tenancy;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public static Rent load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Rent ts = new Rent();
                ts.setId(id);

                ts.setPerson(Person.load(result.getInt("person")));
                ts.setApartment(Apartment.load(result.getInt("apartment")));
                ts.setTenancy(Tenancy.load(result.getInt("tenancy")));


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

                statement.setInt(1, getApartment().getId());
                statement.setInt(2,getTenancy().getId());
                statement.setInt(3,getPerson().getId());

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setInt(1, getApartment().getId());
                statement.setInt(2,getTenancy().getId());
                statement.setInt(3,getPerson().getId());
                statement.setInt(4, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}