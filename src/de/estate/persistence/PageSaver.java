package de.estate.persistence;

import de.estate.persistence.persistenceModels.Page;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PageSaver {

    public void savePage(Page page) {

        try {


            FileWriter fw = new FileWriter(
                    String.format("./src/de/estate/persistence/persistenceModels/Pages/%s", Integer.toString(page.pid)));

            fw.write(String.format("%d,%d,%s", page.pid, page.lsn, page.data));

            fw.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

        System.out.println("wrote swag Page");

    }


    public Page getPage(int id) {

        Page page = new Page();

        try {
            File file = new File(String.format("./src/de/estate/persistence/persistenceModels/Pages/%s", Integer.toString(id)));
            FileReader reader = new FileReader(file);

            StringBuffer stringBuffer = new StringBuffer();

            int numCharsRead;
            char[] charArray = new char[1024];
            while ((numCharsRead = reader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            reader.close();

            String info = stringBuffer.toString();
            page.pid = Integer.parseInt(info.split(",")[0]);
            page.lsn = Integer.parseInt(info.split(",")[1]);
            page.data = info.split(",")[2];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }
}
