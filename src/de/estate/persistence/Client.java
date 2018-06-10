package de.estate.persistence;

import java.util.Random;
public class Client extends Thread {

    private int taId;
    private int pageIds;

    public Client(int pageIds) {
        this.pageIds = pageIds;
    }

    private PersistenceManager pM = PersistenceManager.getInstance();

    public void run() {
        this.taId = pM.beginTransaction();
        while (true) {
            //Für random muss noch dafür egsorgt werden, dass bei der recovery winnerpages erstellt werden die noch nicht persitent sind
            //Random random = new Random();
            int rand = 0; //random.nextInt(30 - 0 + 1);
            pM.write(taId, pageIds, "Swag my Page");
            pM.write(taId, pageIds + 1 + rand, "Swag my Page");
            pM.write(taId, pageIds + 2 + rand, "Swag my Page");
            pM.write(taId, pageIds + 3 + rand, "Swag my Page");
            pM.write(taId, pageIds + 4 + rand, "Swag my Page");
            pM.write(taId, pageIds + 5 + rand, "Swag my Page");
            pM.commit(taId);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
