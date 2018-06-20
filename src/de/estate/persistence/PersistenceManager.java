package de.estate.persistence;

import de.estate.persistence.persistenceModels.Log;
import de.estate.persistence.persistenceModels.Page;
import de.estate.persistence.persistenceModels.TransIdHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PersistenceManager {

    HashMap<Page, Integer> prebuffer = new HashMap<Page, Integer>();
    Map<Page, Integer> buffer = new ConcurrentHashMap(prebuffer);

    private List<Integer> commitedTransactions = new ArrayList<Integer>();

    private PageSaver pagesaver = new PageSaver();
    private TransIdHandler transIdHandler = new TransIdHandler(); // muss noch persistent gemacht werden
    private Logger logger = new Logger();
    private WinLoseManager winLoseManager = new WinLoseManager();

    private int lsn;

    private static PersistenceManager instance = null;

    private PersistenceManager() {
        this.lsn = logger.getLastLsn() + 10;
    }

    public synchronized static PersistenceManager getInstance() {
        if (instance == null) {
            instance = new PersistenceManager();
        }
        return instance;
    }

    public synchronized int beginTransaction() {
        return transIdHandler.getUniqueId();
    }

    public synchronized void commit(int taId) {

        commitedTransactions.add(taId);
        winLoseManager.safeCommitedTa(taId);
    }

    public synchronized void write(int taId, int pageid, String data) {

        bufferContaining(pageid);

        Page page = new Page(pageid, lsn, data);
        buffer.put(page, new Integer(taId));
        logger.log(new Log(lsn, taId, pageid, data));
        lsn += 10;

        if (buffer.size() > 5) {
            makeBufferPersistent();
        }

    }


    private synchronized void bufferContaining(int pageid) {
        if (buffer.size() > 1) {
            for (Map.Entry<Page, Integer> entry : buffer.entrySet()) {
                Page page = entry.getKey();
                int taIds = entry.getValue();
                if (page.pid == pageid) {
                    buffer.remove(page, new Integer(taIds));
                }
            }
        }
    }

    private synchronized void makeBufferPersistent() {
        for (Map.Entry<Page, Integer> entry : buffer.entrySet()) {
            Page page = entry.getKey();
            int taId = entry.getValue();
            if (this.commitedTransactions.contains(taId)) {
                pagesaver.savePage(page);
                this.commitedTransactions.remove(new Integer(taId));
                buffer.remove(page, new Integer(taId));
                winLoseManager.persistedTa(taId);
            }
        }
    }
}
