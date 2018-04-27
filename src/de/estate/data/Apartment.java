package de.estate.data;

public class Apartment extends Estate {


    private static final String createSQL = "SELECT * FROM APARTMENT WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO APARTMENTS (FLOOR, RENT, ROOMS, BALCONY, KITCHEN," +
            " CITY, PLZ, AREA, STREET, MANAGES, NUMBER) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String updateSQL = "UPDATE APARTMENTS FLOOR = ?, RENT = ?," +
            " ROOMS = ?, BALCONY = ?, KITCHEN = ?," +
            "CITY = ?, PLZ = ?, AREA = ?, STREET = ?, MANAGES = ?, NUMBER = ? WHERE id = ?";


    private int floor;

    private double rent;

    private double rooms;

    private boolean balcony;

    private boolean kitchen;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public double getRooms() {
        return rooms;
    }

    public void setRooms(double rooms) {
        this.rooms = rooms;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }


    public static Apartment load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Apartment ts = new Apartment();
                ts.setId(id);
                ts.setBalcony(result.getBoolean("balcony"));
                ts.setFloor(result.getInt("floor"));
                ts.setKitchen(result.getBoolean("kitchen"));
                ts.setRent(result.getDouble("rent"));
                ts.setRooms(result.getDouble("rooms"));

                ts.setCity(result.getString("city"));
                ts.setPlz(result.getInt("plz"));
                ts.setArea(result.getInt("area"));
                ts.setStreet(result.getString("street"));
                ts.setManeges(result.getInt("manages"));
                ts.setNumber(result.getInt("number"));


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

                statement.setInt(1, getFloor());
                statement.setDouble(2, getRent());
                statement.setDouble(3, getRooms());
                statement.setBoolean(4, isBalcony());
                statement.setBoolean(5, isKitchen());
                statement.setString(6, getCity());
                statement.setInt(7, getPlz());
                statement.setInt(8, getArea());
                statement.setString(9, getStreet());
                statement.setInt(10, getManeges());
                statement.setInt(11, getNumber());

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setInt(1, getFloor());
                statement.setDouble(2, getRent());
                statement.setDouble(3, getRooms());
                statement.setBoolean(4, isBalcony());
                statement.setBoolean(5, isKitchen());
                statement.setString(6, getCity());
                statement.setInt(7, getPlz());
                statement.setInt(8, getArea());
                statement.setString(9, getStreet());
                statement.setInt(10, getManeges());
                statement.setInt(11, getNumber());
                statement.setInt(12, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}