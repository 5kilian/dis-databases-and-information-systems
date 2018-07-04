package de.estate.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "STADTID")
public class Stadt {

    @Id
    @GeneratedValue
    @Column(name = "STADID")
    private int id = -1;

    @ManyToOne
    @JoinColumn(name="REGIONID", nullable=false)
    private int regionId;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
