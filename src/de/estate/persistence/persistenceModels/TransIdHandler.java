package de.estate.persistence.persistenceModels;

public class TransIdHandler {

    int uniqueId = 0;

    public synchronized int getUniqueId()
    {
        return uniqueId++;
    }

}
