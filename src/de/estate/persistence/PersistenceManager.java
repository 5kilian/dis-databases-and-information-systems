package de.estate.persistence;

import de.estate.persistence.persistenceModels.Page;
import de.estate.persistence.persistenceModels.TransIdHandler;

import java.util.*;

public class PersistenceManager {

    HashMap<Page, int> buffer = new HashMap<Page, int>();
    private List<int> commitedTransactions;

    private int lsn = 0;  // muss noch persistent gemacht werden


    private PageSaver pagesaver = new PageSaver();
    private TransIdHandler transIdHandler = new TransIdHandler(); // muss noch persistent gemacht werden

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
        commitedTransactions.add(taId)
    }

    public void write(int taId, int pageid, String data) {

        for (Map.Entry<Page, int> entry : buffer.entrySet()) {
            Page page = entry.getKey();
            int taId = entry.getValue();
            if (page.pid == pageid) {
                buffer.remove(page, taId);
            }
        }

        Page page = new Page(pageid, lsn, data);
        buffer.put(page, taId);

        if (buffer.size() > 5) {
            makeBufferPersistent();
        }
    }

    private void makeBufferPersistent() {
        for (Map.Entry<Page, int> entry : buffer.entrySet()) {
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
