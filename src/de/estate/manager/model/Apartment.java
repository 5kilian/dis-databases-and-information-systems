package de.estate.manager.model;

import javax.persistence.*;

@Entity
@Table(name = "APARTMENTS")
public class Apartment extends Estate {

    @Column(columnDefinition = "INT")
    private int floor;

    @Column(columnDefinition = "DOUBLE")
    private double rent;

    @Column(columnDefinition = "DOUBLE")
    private double rooms;

    @Column(columnDefinition = "SMALLINT")
    private boolean balcony;

    @Column(columnDefinition = "SMALLINT")
    private boolean kitchen;

    @OneToOne(mappedBy = "apartment")
    private Tenancy tenancy;

    public Apartment() {

    }

    /**
     * Copy Constructor
     */
    public Apartment(Estate estate) {
        id = estate.id;
        city = estate.city;
        zip = estate.zip;
        street = estate.street;
        number = estate.number;
        area = estate.area;
        agent = estate.agent;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getRooms() {
        return rooms;
    }

    public void setRooms(double rooms) {
        this.rooms = rooms;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }


    @Override
    public String toString() {
        return super.toString() + "floor: " + floor + " rent: " + rent + (balcony? " +balcony" : "") + (kitchen? " +kitchen" : "");
    }
}