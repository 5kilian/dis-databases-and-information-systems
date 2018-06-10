package de.estate.persistence;

import de.estate.persistence.persistenceModels.Page;
import de.estate.persistence.persistenceModels.TransIdHandler;

import java.util.*;

public class PersistenceManager {

    HashMap<Page, Integer> buffer = new HashMap<Page, Integer>();
    private List<Integer> commitedTransactions;


    private PageSaver pagesaver = new PageSaver();
    private TransIdHandler transIdHandler = new TransIdHandler(); // muss noch persistent gemacht werden
    private Logger logger = new Logger();

    private int lsn = logger.getLastLsn() +10 ;

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
        return transIdHandler.getUniqueId();
    }

    public void commit(int taId) {
        commitedTransactions.add(taId);
    }

    public void write(int taId, int pageid, String data) {

        for (Map.Entry<Page, Integer> entry : buffer.entrySet()) {
            Page page = entry.getKey();
            Integer  taIds = entry.getValue();
            if (page.pid == pageid) {
                buffer.remove(page, taIds);
            }
        }

        Page page = new Page(pageid, lsn, data);
        buffer.put(page, taId);

        lsn += 10;

        if (buffer.size() > 5) {
            makeBufferPersistent();
        }
    }

    private void makeBufferPersistent() {
        for (Map.Entry<Page, Integer> entry : buffer.entrySet()) {
            Page page = entry.getKey();
            int taId = entry.getValue();
            if (commitedTransactions.contains(taId)) {
                pagesaver.savePage(page);
                buffer.remove(page, taId);
                commitedTransactions.remove(taId);
            }
        }
    }
}
