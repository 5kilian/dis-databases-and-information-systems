package de.estate.persistence;

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
            pM.write(taId, pageIds, "Swag my Page");
            pM.write(taId, pageIds + 1, "Swag my Page");
            pM.write(taId, pageIds + 2, "Swag my Page");
            pM.write(taId, pageIds + 3, "Swag my Page");
            pM.write(taId, pageIds + 4, "Swag my Page");
            pM.write(taId, pageIds + 5, "Swag my Page");
            pM.commit(taId);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
