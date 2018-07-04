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
    private String shop;

    @ManyToOne
    @JoinColumn(name="ARTICLEID")
    private String article;

    private int sales;

    private double revenue;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
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
