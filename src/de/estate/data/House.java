package de.estate.data;

public class House extends Estate {

    private static final String createSQL = "SELECT * FROM HOUSE WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO HOUSES (FLOORS, PRICE, GARDEN," +
            " CITY, PLZ, AREA, STREET, MANAGES, NUMBER,) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE HOUSES FLOORS = ?, PRICE = ?, GARDEN = ?"  +
            "CITY = ?, PLZ = ?, AREA = ?, STREET = ?, MANAGES = ?, NUMBER = ? WHERE id = ?";

    private int floors;

    private double price;

    private boolean garden;

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }



    public static House load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                House ts = new House();
                ts.setId(id);
                ts.setFloors(result.getString("floors"));
                ts.setPrice(result.getString("price"));
                ts.setGarden(result.getString("balcony"));

                ts.setCity(result.getString("city"));
                ts.setPlz(result.getString("plz"));
                ts.setArea(result.getString("area"));
                ts.setStreet(result.getString("street"));
                ts.setManeges(result.getString("manages"));
                ts.setNumber(result.getString("number"));


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

                statement.setInt(1, getFloors());
                statement.setDouble(2, getPrice());
                statement.setBoolean(3, isgetGarden());

                statement.setString(4, getCity());
                statement.setInt(5, getPlz());
                statement.setInt(6, getArea());
                statement.setString(7, getStreet());
                statement.setInt(8, getManeges());
                statement.setInt(9, getNumber());

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setInt(1, getFloors());
                statement.setDouble(2, getPrice());
                statement.setBoolean(3, isgetGarden());

                statement.setString(4, getCity());
                statement.setInt(5, getPlz());
                statement.setInt(6, getArea());
                statement.setString(7, getStreet());
                statement.setInt(8, getManeges());
                statement.setInt(9, getNumber());

                statement.setInt(10, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}