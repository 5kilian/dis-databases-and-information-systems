package de.estate.persistence;

import de.estate.persistence.persistenceModels.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {

    public void log(Log log) {

        try {

            String path = "./src/de/estate/persistence/persistenceModels/Logs/Log";
            FileWriter fw;
            File userDataFile = new File(path);

            if (userDataFile.exists()) {
                fw = new FileWriter(path, true);
                fw.write(String.format(",%d,%d,%d,%s", log.lsn, log.tid, log.pid, log.redo));

            } else {
                fw = new FileWriter(path, true);
                fw.write(String.format("%d,%d,%d,%s", log.lsn, log.tid, log.pid, log.redo));
            }

            fw.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

        System.out.println("wrote swag Log");

    }


    public List<Log> getAllLogs() {

        List<Log> allLogs = new ArrayList<Log>();

        try {

            File file = new File("./src/de/estate/persistence/persistenceModels/Logs/Log");
            FileReader reader = new FileReader(file);

            StringBuffer stringBuffer = new StringBuffer();

            int numCharsRead;
            char[] charArray = new char[1024];
            while ((numCharsRead = reader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            reader.close();

            String[] info = stringBuffer.toString().split(",");

            for (int i = 0; i + 1 < info.length ; i = i + 4) {

                Log log = new Log();

                log.lsn = Integer.parseInt(info[i]);
                log.tid = Integer.parseInt(info[i + 1]);
                log.pid = Integer.parseInt(info[i + 2]);
                log.redo = info[i + 3];

                allLogs.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allLogs;
    }

    public int getLastLsn() {

        int lastLsn = 0;

        try {

            FileReader reader;
            String path = "./src/de/estate/persistence/persistenceModels/Logs/Log";
            File userDataFile = new File(path);


            if (userDataFile.exists()) {
                reader = new FileReader(userDataFile);

                StringBuffer stringBuffer = new StringBuffer();

                int numCharsRead;
                char[] charArray = new char[1024];
                while ((numCharsRead = reader.read(charArray)) > 0) {
                    stringBuffer.append(charArray, 0, numCharsRead);
                }
                reader.close();

                String[] info = stringBuffer.toString().split(",");
                lastLsn = Integer.parseInt(info[info.length - 4]);
            } else {
                return 0;
            }
        } catch (IOException ex) {

            ex.printStackTrace();

        }

        return lastLsn;

    }

}

