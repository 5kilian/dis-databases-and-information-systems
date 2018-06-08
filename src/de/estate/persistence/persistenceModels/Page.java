package de.estate.persistence.persistenceModels;

import java.util.List;

public class Page {

    public int pid;

    public int lsn;

    public String data;

    public Page(int pid, int lsn, String data) {
        this.pid = pid;
        this.lsn = lsn;
        this.data = data;
    }
    public Page() {
    }

    public static void PagesToDB(List<Page> lst){

        for (Page:lst
             ) {



        }
    }


}
