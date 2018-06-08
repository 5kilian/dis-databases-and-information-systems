package de.estate.manager;

import de.estate.persistence.PageSaver;
import de.estate.persistence.persistenceModels.Log;
import de.estate.persistence.Logger;
import de.estate.persistence.persistenceModels.Page;

public class Main {

    /*
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/home.fxml"));
        stage.setTitle("Estate manager");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        Connection connection = DB2Connection.getConnection();
        connection.setTransactionIsolation();

    }
*/
    public static void main(String[] args) {

        /*
        Log log = new Log(1,1,1, "hey");

        Logger logger = new Logger();
         System.out.println(logger.getLog(1));
        */

        Page page = new Page(1, 1, "hey");
        PageSaver ps = new PageSaver();
        ps.savePage(page);
        System.out.println(ps.getPage(1));


    }

}
