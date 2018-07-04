package de.estate.resources.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTGROUPID")
public class Productgroup {

    @Id
    @GeneratedValue
    @Column(name = "PRODUCTGROUPID")
    private int id = -1;

    @ManyToOne
    @JoinColumn(name="PRODUCTFAMILYID", nullable=false)
    private int productfamilyId;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductfamilyId() {
        return productfamilyId;
    }

    public void setProductfamilyId(int productfamilyId) {
        this.productfamilyId = productfamilyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
