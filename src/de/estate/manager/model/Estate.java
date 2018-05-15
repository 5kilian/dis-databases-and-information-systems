package de.estate.manager.model;

import javax.persistence.*;


@Entity
@Table(name = "ESTATES")
@Inheritance(strategy = InheritanceType.JOINED)
public class Estate {

    @Id
    @GeneratedValue
    protected int id = -1;

    @Column(columnDefinition = "VARCHAR(255)")
    protected String city;

    @Column(columnDefinition = "INT")
    protected int zip;

    @Column(columnDefinition = "VARCHAR(255)")
    protected String street;

    @Column(columnDefinition = "INT")
    protected int number;

    @Column(columnDefinition = "INT")
    protected int area;

    @ManyToOne
    protected Agent agent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" + street + " " + number + "\n" + zip + " " + city + " " + area + "\n";
    }
}
