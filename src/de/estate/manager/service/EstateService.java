package de.estate.manager.service;

import de.estate.manager.model.Agent;
import de.estate.manager.model.Apartment;
import de.estate.manager.model.Estate;
import de.estate.manager.model.House;
import de.estate.manager.util.DB2Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EstateService {

    public Estate get(int id) {
        return Estate.load(id);
    }

    public List<Estate> getAll() {
        List<Estate> estates = new ArrayList<>();

        try {
            ResultSet estatess = DB2Connection.getConnection().prepareStatement("SELECT * FROM ESTATES e WHERE e.ID NOT IN (" +
                    "SELECT e.ID FROM ESTATES e, HOUSES h, APARTMENTS a WHERE e.ID = h.ID OR e.ID = a.ID" +
                    ")").executeQuery();
            ResultSet houses = DB2Connection.getConnection().prepareStatement("SELECT * FROM ESTATES e, HOUSES h WHERE e.ID = h.ID").executeQuery();
            ResultSet apartments = DB2Connection.getConnection().prepareStatement("SELECT * FROM ESTATES e, APARTMENTS a WHERE e.ID = a.ID").executeQuery();

            while (estatess.next()) {
                Estate estate = new Estate();
                estate.setId(estatess.getInt("id"));
                estate.setStreet(estatess.getString("street"));
                estate.setArea(estatess.getInt("area"));
                estate.setCity(estatess.getString("city"));
                estate.setNumber(estatess.getInt("number"));
                estate.setZip(estatess.getInt("zip"));
                estate.setAgent(new Agent(estatess.getInt("agent")));
                estates.add(estate);
            }

            while (houses.next()) {
                House estate = new House();
                estate.setId(houses.getInt("id"));
                estate.setStreet(houses.getString("street"));
                estate.setArea(houses.getInt("area"));
                estate.setCity(houses.getString("city"));
                estate.setNumber(houses.getInt("number"));
                estate.setZip(houses.getInt("zip"));
                estate.setAgent(new Agent(houses.getInt("agent")));
                estate.setGarden(houses.getBoolean("garden"));
                estate.setPrice(houses.getDouble("price"));
                estate.setFloors(houses.getInt("floors"));
                estates.add(estate);
            }

            while (apartments.next()) {
                Apartment estate = new Apartment();
                estate.setId(apartments.getInt("id"));
                estate.setStreet(apartments.getString("street"));
                estate.setArea(apartments.getInt("area"));
                estate.setCity(apartments.getString("city"));
                estate.setNumber(apartments.getInt("number"));
                estate.setZip(apartments.getInt("zip"));
                estate.setAgent(new Agent(apartments.getInt("agent")));
                estate.setRooms(apartments.getDouble("rooms"));
                estate.setKitchen(apartments.getBoolean("kitchen"));
                estate.setRent(apartments.getDouble("rent"));
                estate.setFloor(apartments.getInt("floor"));
                estate.setBalcony(apartments.getBoolean("balcony"));
                estates.add(estate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        estates.sort(Comparator.comparingInt(Estate::getId));
        return estates;
    }

    public void delete(Estate estate) {
        delete(estate.getId());
    }

    public void delete(int id) {
        try {
            List<PreparedStatement> statements = new ArrayList<>();
            statements.add(DB2Connection.getConnection().prepareStatement("DELETE FROM HOUSES WHERE ID = ?"));
            statements.add(DB2Connection.getConnection().prepareStatement("DELETE FROM RENTS WHERE ID = ?"));
            statements.add(DB2Connection.getConnection().prepareStatement("DELETE FROM ESTATES WHERE ID = ?"));

            for (PreparedStatement statement : statements) {
                statement.setInt(1, id);
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
