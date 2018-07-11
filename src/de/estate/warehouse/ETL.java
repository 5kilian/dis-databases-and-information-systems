package de.estate.warehouse;

import de.estate.warehouse.model.Article;
import de.estate.warehouse.model.Shop;
import de.estate.warehouse.model.Sold;
import de.estate.warehouse.service.ArticleService;
import de.estate.warehouse.service.ShopService;
import de.estate.warehouse.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ETL {

    private ShopService shopService;

    private ArticleService articleService;

    private List<Sold> csvProducts = new ArrayList<>();

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

            List shops = shopService.getAll();
            List articles = articleService.getAll();


            SessionFactory sessionFactory = SessionFactory_hib.getSessionFactory();

            while ((line = reader.readLine()) != null) {


                String[] productLine = line.split(";");

                try {
                    Sold sold = new Sold();
                    sold.setDate(productLine[0]);
                    sold.setShop(getShopByName(shops, productLine[1]));
                    sold.setArticle(getArticleByName(articles, productLine[2]));
                    sold.setSales(Integer.valueOf(productLine[3]));
                    sold.setRevenue(Double.valueOf(productLine[4].replace(',', '.')));

                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();
                    session.save(sold);
                    session.getTransaction().commit();

                    csvProducts.add(sold);
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

    private Shop getShopByName(List<Shop> shops, String name) {
        for (Shop shop : shops) {
            if (shop.getName().equals(name)) return shop;
        }
        return null;
    }
    private Article getArticleByName(List<Article> articles, String name) {
        for (Article article : articles) {
            if (article.getName().equals(name)) return article;
        }
        return null;
    }


    public void transform() {

    }

    public void load() {

    }

}
