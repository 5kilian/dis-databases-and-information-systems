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

    @OneToMany(mappedBy="productfamily")
    private List<Productgroup> productgroup;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Productgroup> getProductgroup() {
        return productgroup;
    }

    public void setProductgroup(List<Productgroup> productgroup) {
        this.productgroup = productgroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
