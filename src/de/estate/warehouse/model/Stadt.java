package de.estate.warehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "STADTID")
public class Stadt {

    @Id
    @GeneratedValue
    @Column(name = "STADID")
    private int id = -1;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @ManyToOne
    private Region region;

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
