package de.estate.manager.model;

import javax.persistence.*;

@Entity
@Table(name = "HOUSES")
public class House extends Estate {

    @Column(columnDefinition = "INT")
    private int floors;

    @Column(columnDefinition = "DOUBLE")
    private double price;

    @Column(columnDefinition = "SMALLINT")
    private boolean garden;

    @OneToOne(mappedBy = "house")
    private Purchase purchase;

    public House() {

    }

    /**
     * Copy Constructor
     */
    public House(Estate estate) {
        id = estate.id;
        city = estate.city;
        zip = estate.zip;
        street = estate.street;
        number = estate.number;
        area = estate.area;
        agent = estate.agent;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }


    @Override
    public String toString() {
        return super.toString() + "floors: " + floors + " price: " + price + (garden? " +garden" : "");
    }

}
