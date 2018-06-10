package de.estate.persistence.persistenceModels;

import java.util.Date;

public class TransIdHandler {

    int unique_id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    public synchronized int getUniqueId()
    {
        return unique_id;
    }

}
