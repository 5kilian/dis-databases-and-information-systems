package de.estate.warehouse;

import de.estate.warehouse.model.CsvProduct;
import de.estate.warehouse.service.ArticleService;
import de.estate.warehouse.service.ShopService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ETL {

    private ShopService shopService;

    private ArticleService articleService;

    private List<CsvProduct> csvProducts = new ArrayList<>();

    public static void main(String[] args) {
        new ETL();
    }

    public ETL() {
        shopService = new ShopService();
        articleService = new ArticleService();

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
                    product.setShop(shopService.getByName(productLine[1]));
                    product.setArticle(articleService.getByName(productLine[2]));
                    product.setSales(Integer.valueOf(productLine[3]));
                    product.setRevenue(Double.valueOf(productLine[4].replace(',', '.')));

                    csvProducts.add(product);
                } catch (Exception e) {
                    System.out.println("Cannot read line: " + line);
                    e.printStackTrace();
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
