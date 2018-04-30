package de.estate.manager.service;

import de.estate.manager.model.Agent;
import de.estate.manager.model.Estate;
import de.estate.manager.util.DB2Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstateService {

    public Estate get(int id) {
        return Estate.load(id);
    }

    public List<Estate> getAll() {
        List<Estate> estates = new ArrayList<>();

        try {
            ResultSet result = DB2Connection.getConnection().prepareStatement("SELECT * FROM ESTATES").executeQuery();

            while (result.next()) {
                Estate estate = new Estate();
                estate.setId(result.getInt("id"));
                estates.add(estate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estates;
    }

    public void delete(Estate estate) {
        delete(estate.getId());
    }

    public void delete(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement("DELETE FROM ESTATES WHERE ID = ?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
