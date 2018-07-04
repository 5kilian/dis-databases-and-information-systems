package de.estate.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "CSVPRODUCTID")
public class CsvProduct {

    @Id
    @GeneratedValue
    private int id;

    private String date;

    @ManyToOne
    @JoinColumn(name="SHOPID")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="ARTICLEID")
    private Article article;

    private int sales;

    private double revenue;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
