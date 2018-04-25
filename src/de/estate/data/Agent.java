package de.estate.data;

import java.sql.*;

/**
 * 
 */
public class Agent {

    private int id;

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

    /**
     * Lädt einen Makler aus der Datenbank
     * @param id ID des zu ladenden Maklers
     * @return Makler-Instanz
     */
    public static Agent load(int id) {
        try {
            // Hole Verbindung
            Connection con = DB2Connection.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM AGENTS WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Agent ts = new Agent();
                ts.setId(id);
                ts.setLogin(rs.getString("login"));
                ts.setPassword(rs.getString("password"));
                ts.setAddress(rs.getString("address"));

                rs.close();
                pstmt.close();
                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
     * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
     */
    public void save() {
        // Hole Verbindung
        Connection con = DB2Connection.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (getId() == -1) {
                // Achtung, hier wird noch ein Parameter mitgegeben,
                // damit spC$ter generierte IDs zurC<ckgeliefert werden!
                String insertSQL = "INSERT INTO AGENTS(LOGIN, PASSWORD, ADDRESS) VALUES (?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Setze Anfrageparameter und fC<hre Anfrage aus
                pstmt.setString(1, getLogin());
                pstmt.setString(2, getPassword());
                pstmt.setString(3, getAddress());
                pstmt.executeUpdate();

                // Hole die Id des engefC<gten Datensatzes
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE AGENTS SET LOGIN = ?, PASSWORD = ?, ADDRESS = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setString(1, getLogin());
                pstmt.setString(2, getPassword());
                pstmt.setString(3, getAddress());
                pstmt.setInt(4, getId());
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
