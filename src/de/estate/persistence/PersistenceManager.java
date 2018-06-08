package de.estate.persistence;

import de.estate.persistence.persistenceModels.Page;
import de.estate.persistence.persistenceModels.TransIdHandler;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PersistenceManager {

    private List<Page> buffer;
    private int lsn = 0;  // muss noch persistent gemacht werden?


    private PageSaver pagesaver = new PageSaver();
    private TransIdHandler transIdHandler = new TransIdHandler();

    private static PersistenceManager instance = null;

    private PersistenceManager() {

    }

    public static PersistenceManager getInstance() {
        if (instance == null) {
            instance = new PersistenceManager();
        }
        return instance;
    }

    public int beginTransaction() {
        transIdHandler.getUniqueId();
    }

    public PersistenceManager commit(int taid) {
        return this;
    }

    public void PersistenceManager write(int taid, int pageid, String data) {

        for (Page p : buffer) {
            if (p.pid == pageid){
                buffer.remove(p);
            }
        }

        Page page = new Page(pageid,lsn,data);
        buffer.add(Page);

        if (buffer.size()>5){
            makeBufferPersistent();
        }
    }

    private void makeBufferPersistent(){
        for (Page p : buffer) {
            pagesaver.savePage(p);
        }
        buffer.removeAll();
    }

}
