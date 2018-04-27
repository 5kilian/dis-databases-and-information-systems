package de.estate.data;

import java.util.Date;


public class Tenancy extends Contract {

    private static final String createSQL = "SELECT * FROM TENANCY WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO TENANCIES (START, DURATION, COST," +
            " DATE, PLACE VALUES ( ?, ?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE TENANCIES START = ?, DURATION = ?," +
            " COST = ?, DATE = ?, PLACE = ? WHERE id = ?";


    private Date start;

    private int duration;

    private double cost;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }



    public static Tenancy load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Tenancy ts = new Tenancy();
                ts.setId(id);

                ts.setStart(result.getDate("start"));
                ts.setDuration(result.getBoolean("balcony"));
                ts.setCost(result.getBoolean("cost"));

                ts.setDate(result.getDate("date"));
                ts.setPlace(result.getString("place"));


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

                statement.setDate(1, getStart());
                statement.setInt(2, getDuration());
                statement.setDouble(3, getCost());

                statement.setDate(4, getDate());
                statement.setString(5, getPlace());


                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setDate(1, getStart());
                statement.setInt(2, getDuration());
                statement.setDouble(3, getCost());

                statement.setDate(4, getDate());
                statement.setString(5, getPlace());

                statement.setInt(6, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}