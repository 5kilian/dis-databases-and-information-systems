package de.estate.manager.service;

import de.estate.manager.model.Agent;
import de.estate.manager.util.DB2Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentService {

    public Agent get(int id) {
        return Agent.load(id);
    }

    public List<Agent> getAll() {
        List<Agent> agents = new ArrayList<>();

        try {
            ResultSet result = DB2Connection.getConnection().prepareStatement("SELECT * FROM AGENTS").executeQuery();

            while (result.next()) {
                Agent agent = new Agent();
                agent.setId(result.getInt("id"));
                agent.setName(result.getString("name"));
                agent.setLogin(result.getString("login"));
                agent.setPassword(result.getString("password"));
                agent.setAddress(result.getString("address"));
                agents.add(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }

    public void delete(Agent agent) {
        delete(agent.getId());
    }

    public void delete(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement("DELETE FROM AGENTS WHERE ID = ?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Agent validate(String name, String password) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement("SELECT * FROM AGENTS WHERE LOGIN = ? AND PASSWORD = ?");
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Agent agent = new Agent();
                agent.setId(result.getInt("id"));
                agent.setName(result.getString("name"));
                agent.setLogin(result.getString("login"));
                agent.setPassword(result.getString("password"));
                agent.setAddress(result.getString("address"));
                return agent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
