package de.estate.resources.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTFAMILYID")
public class Productfamily {

    @Id
    @GeneratedValue
    @Column(name = "PRODUCTFAMILYID")
    private int id = -1;

    @ManyToOne
    @JoinColumn(name="PRODUCTCATEGORYID", nullable=false)
    private int productgroupid;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;


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
}
