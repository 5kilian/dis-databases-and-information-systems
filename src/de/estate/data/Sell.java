package de.estate.data;

public class Sell {

    private static final String createSQL = "SELECT * FROM SELL WHERE ID = ?";
    private static final String insertSQL = "INSERT INTO SELLS (PERSON, HOUSE, PURCHASE") +
            " VALUES ( ?, ?, ?)";
    private static final String updateSQL = "UPDATE SELLS PERSON = ?, HOUSE = ?," +
            " PURCHASE = ?  WHERE id = ?";

    private int id = -1;

    private Person person;

    private House house;

    private Purchase purchase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public static Sell load(int id) {
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement(createSQL);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Sell ts = new Sell();
                ts.setId(id);

                ts.setPerson(result.getPerson("person")); //?
                ts.setHouse(result.getHouse("house")); //?
                ts.setPurchase(result.getPurchase("purchase"));


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

                statement.setPerson(1, getPerson()); //?
                statement.setHouse(2, getHouse());
                statement.setPurchase(3, getPurchase());

                statement.executeUpdate();

                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    setId(result.getInt(1));
                }

                result.close();
                statement.close();
            } else {
                PreparedStatement statement = con.prepareStatement(updateSQL);

                statement.setPerson(1, getPerson()); //?
                statement.setHouse(2, getHouse());
                statement.setPurchase(3, getPurchase());

                statement.setInt(4, getId());
                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}