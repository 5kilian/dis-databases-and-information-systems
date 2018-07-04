package de.estate.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "SHOPID")
public class Shop {

    @Id
    @GeneratedValue
    @Column(name = "SHOPID")
    private int id = -1;

    @ManyToOne
    @JoinColumn(name="STADTID", nullable=false)
    private Stadt stadt;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stadt getStadt() {
        return stadt;
    }

    public void setStadt(Stadt stadt) {
        this.stadt = stadt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
