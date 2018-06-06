package de.estate.persistence;

public class PersistenceManager {

    private String buffer;

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
