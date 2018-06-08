package de.estate.persistence.persistenceModels;

public class Log {

    public int lsn;

    public int tid;

    public int pid;

    public String redo;

    public Log(int lsn, int tid, int pid, String redo) {
        this.lsn = lsn;
        this.tid = tid;
        this.pid = pid;
        this.redo = redo;
    }

    public Log() {
    }

}
