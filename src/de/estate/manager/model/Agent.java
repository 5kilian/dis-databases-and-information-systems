package de.estate.manager.model;

import de.estate.manager.util.DB2Connection;

import java.sql.*;


public class Agent {

    private static final String createSQL = "SELECT * FROM AGENTS WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO AGENTS(NAME, LOGIN, PASSWORD, ADDRESS) VALUES (?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE AGENTS SET NAME = ?, LOGIN = ?, PASSWORD = ?, ADDRESS = ? WHERE ID = ?";

    private int id = -1;

    private String address;

    private String name;

    private String login;

    private String password;

    public Agent() {

    }

    public Agent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Agent load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Agent agent = new Agent();
                agent.setId(id);
                agent.setName(result.getString("name"));
                agent.setLogin(result.getString("login"));
                agent.setPassword(result.getString("password"));
                agent.setAddress(result.getString("address"));

                result.close();
                statement.close();
                return agent;
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

                statement.setString(1, getName());
                statement.setString(2, getLogin());
                statement.setString(3, getPassword());
                statement.setString(4, getAddress());
                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setString(1, getName());
                statement.setString(2, getLogin());
                statement.setString(3, getPassword());
                statement.setString(4, getAddress());
                statement.setInt(5, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
