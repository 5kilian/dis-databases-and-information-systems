package de.estate.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "REGIONID")
public class Region {


    @Id
    @GeneratedValue
    @Column(name = "REGIONID")
    private int regionid = -1;

    @ManyToOne
    @JoinColumn(name="LANDID", nullable=false)
    private Land land;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    public int getRegionid() {
        return regionid;
    }

    public void setRegionid(int regionid) {
        this.regionid = regionid;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
