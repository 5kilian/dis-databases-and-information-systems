package de.estate.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "SOLDID")
public class Sold {

    @Id
    @GeneratedValue
    @Column(name = "SOLDID")
    private int id;

    @Column(name = "DATE")
    private String date;

    @ManyToOne
    @JoinColumn(name="SHOPID")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="ARTICLEID")
    private Article article;

    @Column(name = "SALES")
    private int sales;

    @Column(name = "REVENUE")
    private double revenue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
