package de.estate.persistence;

import de.estate.persistence.persistenceModels.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    public void log(Log log) {

        try {

            FileWriter fw = new FileWriter(
                    String.format("./src/de/estate/persistence/persistenceModels/Logs/%s", Integer.toString(log.lsn)));

            fw.write(String.format("%d,%d,%d,%s", log.lsn, log.tid, log.pid, log.redo));

            fw.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

        System.out.println("wrote swag Log");

    }


    public Log getLog(int id) {

        Log log = new Log();

        try {
            File file = new File(String.format("./src/de/estate/persistence/persistenceModels/Logs/%s", Integer.toString(id)));
            FileReader reader = new FileReader(file);

            StringBuffer stringBuffer = new StringBuffer();

            int numCharsRead;
            char[] charArray = new char[1024];
            while ((numCharsRead = reader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            reader.close();

            String info = stringBuffer.toString();
            log.lsn = Integer.parseInt(info.split(",")[0]);
            log.tid = Integer.parseInt(info.split(",")[1]);
            log.pid = Integer.parseInt(info.split(",")[2]);
            log.redo = info.split(",")[3];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return log;
    }
}

