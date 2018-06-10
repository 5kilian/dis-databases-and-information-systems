package de.estate.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WinLoseManager {

    String path = "./src/de/estate/persistence/persistenceModels/Logs/CommitedTransactions";

    public WinLoseManager() {
    }

    public static List<Integer> getWinnerTaIds() {

        List<Integer> winnerTa = new ArrayList<Integer>();
        try {

            File file = new File("./src/de/estate/persistence/persistenceModels/Logs/CommitedTransactions");
            FileReader reader = new FileReader(file);

            StringBuffer stringBuffer = new StringBuffer();

            int numCharsRead;
            char[] charArray = new char[1024];
            while ((numCharsRead = reader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            reader.close();

            String[] info = stringBuffer.toString().split(",");

            for (int i = 0; i + 1 < info.length; i++) {

                winnerTa.add(Integer.parseInt(info[0]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return winnerTa;
    }

    public void safeCommitedTa(int taId) {

        try {
            FileWriter fw = new FileWriter(path, true);
            fw.write(String.format("%d,", taId));
            fw.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    public void persistedTa(int taId) {
        try {

            File file = new File(path);
            FileReader reader = new FileReader(file);

            StringBuffer stringBuffer = new StringBuffer();

            int numCharsRead;
            char[] charArray = new char[1024];
            while ((numCharsRead = reader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            reader.close();

            String[] info = stringBuffer.toString().split(",");

            List<String> list = new ArrayList<String>(Arrays.asList(info));
            for (String s : info) {
                if (s.equals(Integer.toString(taId))) {
                    list.remove(s);
                    info = list.toArray(new String[0]);
                }
            }
            for (String s : info) {
                this.safeCommitedTa(Integer.parseInt(s));
            }


        } catch (
                Exception e)

        {
            e.printStackTrace();
        }


    }

}
