package de.estate.persistence;

public class PersistenceManager {

    private String buffer;

    private static PersistenceManager instance = null;

    private PersistenceManager() {

    }

    public static PersistenceManager getInstance() {
        if (instance == null) {
            instance = new PersistenceManager();
        }
        return instance;
    }

    public PersistenceManager beginTransaction() {

        return this;
    }

    public PersistenceManager commit(int taid) {

        return this;
    }

    public PersistenceManager write(int taid, int pageid, String data) {

        return this;
    }

}
