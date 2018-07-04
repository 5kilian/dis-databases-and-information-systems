package de.estate.warehouse.model;


import javax.persistence.*;

@Entity
@Table(name = "LANDID")
public class Land {

    @Id
    @GeneratedValue
    private int landid = -1;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;



    public int getLandid() {
        return landid;
    }

    public void setLandid(int landid) {
        this.landid = landid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
