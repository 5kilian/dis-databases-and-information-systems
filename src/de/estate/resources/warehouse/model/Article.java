package de.estate.resources.warehouse.model;


import javax.persistence.*;

@Entity
@Table(name = "ARTICLEID")
public class Article {

    @Id
    @GeneratedValue
    @Column(name = "ARTICLEID")
    private int id = -1;

    @ManyToOne
    @JoinColumn(name="PRODUCTGROUPID", nullable=false)
    private int productgroupid;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(columnDefinition = "DOUBLE")
    private double preis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductgroupid() {
        return productgroupid;
    }

    public void setProductgroupid(int productgroupid) {
        this.productgroupid = productgroupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }
}
