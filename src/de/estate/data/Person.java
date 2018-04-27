package de.estate.data;

public class Person {

    private static final String createSQL = "SELECT * FROM PERSON WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO PERSONS (FIRSTNAME, NAME," +
            " ADRESS VALUES ( ?, ?, ? )";
    private static final String updateSQL = "UPDATE PERSONS FIRSTNAME = ?, NAME = ?," +
            " ADDRESS = ? WHERE id = ?";

    private int id = -1;

    private String firstName;

    private String name;

    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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


    public static Person load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Person ts = new Person();
                ts.setId(id);

                ts.setFirstName(result.getString("firstName"));
                ts.setName(result.getString("name"));
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

                statement.setString(1, getFirstName());
                statement.setString(2, getName());
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

                statement.setString(1, getFirstName());
                statement.setString(2, getName());
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