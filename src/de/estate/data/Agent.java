package de.estate.data;

import java.sql.*;


public class Agent {

    private static final String createSQL = "SELECT * FROM AGENTS WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO AGENTS(LOGIN, PASSWORD, ADDRESS) VALUES (?, ?, ?)";
    private static final String updateSQL = "UPDATE AGENTS SET LOGIN = ?, PASSWORD = ?, ADDRESS = ? WHERE id = ?";

    private int id = -1;

    private String address;

    private String login;

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                Agent ts = new Agent();
                ts.setId(id);
                ts.setLogin(result.getString("login"));
                ts.setPassword(result.getString("password"));
                ts.setAddress(result.getString("address"));

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

                statement.setString(1, getLogin());
                statement.setString(2, getPassword());
                statement.setString(3, getAddress());
                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setString(1, getLogin());
                statement.setString(2, getPassword());
                statement.setString(3, getAddress());
                statement.setInt(4, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
