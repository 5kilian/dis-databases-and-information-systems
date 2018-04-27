package de.estate.data;

public class Rent {

    private static final String createSQL = "SELECT * FROM RENT WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO RENTS (APARTMENT, TENANCY, PERSON") +
            " VALUES ( ?, ?, ?)";
    private static final String updateSQL = "UPDATE RENTS APARTMENT = ?, TENANCY = ?," +
            " PERSON = ?  WHERE id = ?";

    private int id = -1;

    private Apartment apartment;

    private Tenancy tenancy;

    private Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Tenancy getTenancy() {
        return tenancy;
    }

    public void setTenancy(Tenancy tenancy) {
        this.tenancy = tenancy;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public static Rent load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Rent ts = new Rent();
                ts.setId(id);

                ts.setPerson(result.setPerson("person")); //?
                ts.setApartment(result.setApartment("apartment")); //?
                ts.setTenancy(result.setTenancy("tenancy"));


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

                statement.setApartment(1, getApartment());
                statement.setTenancy(2,getTenancy());
                statement.setPerson(3, getPerson()); //?

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setApartment(1, getApartment());
                statement.setTenancy(2,getTenancy());
                statement.setPerson(3, getPerson()); //?

                statement.setInt(4, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}