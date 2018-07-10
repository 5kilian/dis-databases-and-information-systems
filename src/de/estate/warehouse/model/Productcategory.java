package de.estate.warehouse.model;


import javax.persistence.*;

@Entity
@Table(name = "PRODUCTCATEGORYID")
public class Productcategory {


    @Id
    @GeneratedValue
    @Column(name = "PRODUCTCATEGORYID")
    private int id = -1;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}