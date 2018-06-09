package de.estate.persistence;

public class Client extends Thread {

    int taId;


    private PersistenceManager pM = PersistenceManager.getInstance();

    public initTransaction() {
        this.taId = pM.beginTransaction();
    }

    private writeTransaction(int pageId, String data) {
        pM.write(taId, pageId, data);
    }

    private commintTransaction() {
        pM.commit(taId);
    }

    public void run() {
        while (true) {
            this.initTransaction();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
