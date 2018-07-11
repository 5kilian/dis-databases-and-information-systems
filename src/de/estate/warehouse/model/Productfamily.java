package de.estate.warehouse.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PRODUCTFAMILYID")
public class Productfamily {

    @Id
    @GeneratedValue
    @Column(name = "PRODUCTFAMILYID")
    private int id = -1;

    @ManyToOne
    @JoinColumn(name="PRODUCTCATEGORYID", nullable=false)
    private Productcategory productcategory;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Productcategory getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(Productcategory productcategory) {
        this.productcategory = productcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
