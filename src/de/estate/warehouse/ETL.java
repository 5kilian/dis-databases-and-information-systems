package de.estate.warehouse;

import de.estate.warehouse.model.CsvProduct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ETL {

    private List<CsvProduct> csvProducts = new ArrayList<>();

    public static void main(String[] args) {
        new ETL();
    }

    public ETL() {
        extract("./src/de/estate/resources/warehouse/sales.csv");
        transform();
        load();
    }

    public void extract(String csv) {
        String line;

        System.out.println("Read CSV: " + csv);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csv));

            // skip the first row
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] productLine = line.split(";");

                try {
                    CsvProduct product = new CsvProduct();
                    product.setDate(productLine[0]);
                    product.setShop(productLine[1]);
                    product.setArticle(productLine[2]);
                    product.setSales(Integer.valueOf(productLine[3]));
                    product.setRevenue(Double.valueOf(productLine[4].replace(',', '.')));

                    csvProducts.add(product);
                } catch (Exception e) {
                    System.out.println("Cannot read line: " + line);
                }
            }

            System.out.printf("%d Products extracted\n", csvProducts.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transform() {

    }

    public void load() {

    }

}
