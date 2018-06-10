package de.estate.persistence;

import de.estate.persistence.persistenceModels.Log;
import de.estate.persistence.persistenceModels.Page;

import java.util.Collections;
import java.util.List;

public class RecoveryTool {

    private Logger logger = new Logger();
    private PageSaver pagesaver = new PageSaver();

    public void recover(){
        List<Page> recoveredPages =  this.getRecoveredPages();

        for (Page p: recoveredPages) {
            pagesaver.savePage(p);
        }
    }

    private List<Page> getRecoveredPages() {

        List<Page> recoverdPages = Collections.emptyList();

        List<Log> allLogs = logger.getAllLogs();


        for (Log l : allLogs) {
            if (l.lsn > pagesaver.getPage(l.pid).lsn) {
                Page page;
                int pageIndx = this.ContainingPageId(recoverdPages, l.pid);
                if (pageIndx != 0) {
                    page = recoverdPages.get(pageIndx);
                    recoverdPages.remove(page);
                } else {
                    page = pagesaver.getPage(l.pid);
                }

                this.redo(l, page);
                recoverdPages.add(page);
            }
        }
        return recoverdPages;
    }

    private int ContainingPageId(List<Page> pLst, int pId) {
        int counter = 0;
        for (Page p : pLst) {
            if (p.pid == pId) {
                return counter;
            }
            counter++;
        }
        return 0;
    }


    private Page redo(Log log, Page page) {
        String redo = log.redo;
        page.data = redo;
        page.lsn = log.lsn;
        return page;
    }

}
