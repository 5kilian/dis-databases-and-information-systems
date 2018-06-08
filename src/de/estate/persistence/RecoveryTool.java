package de.estate.persistence;

import de.estate.persistence.persistenceModels.Page;

import java.io.File;

public class RecoveryTool {

    private Logger logger = new Logger();

    public Page recover(int lsn, Page page) {


        String path = "./src/de/estate/persistence/persistenceModels/Logs"

        int latestLogLsn =
                Integer.parseInt(getLatestFilefromDir(path).getName());
        if (latestLogLsn > page.lsn) {

           page = redo(latestLogLsn, page);

           return page;
        }
    }

    public Page redo(int lsn, Page page){
        String redo = logger.getLog(lsn).redo;
        page.data = redo;
        page.lsn = lsn;
        return page;
    }


    private File getLatestFilefromDir(String dirPath){
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }
}
