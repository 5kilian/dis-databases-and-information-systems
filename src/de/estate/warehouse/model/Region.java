package de.estate.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "REGIONID")
public class Region {


    @Id
    @GeneratedValue
    private int regionid = -1;

    @ManyToOne
    @JoinColumn(name="landid", nullable=false)
    private int landId;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    public int getRegionid() {
        return regionid;
    }

    public void setRegionid(int regionid) {
        this.regionid = regionid;
    }

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
