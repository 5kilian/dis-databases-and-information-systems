package de.estate.warehouse.model;

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
    private Productfamily productfamily;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Productfamily getProductfamily() {
        return productfamily;
    }

    public void setProductfamily(Productfamily productfamily) {
        this.productfamily = productfamily;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
