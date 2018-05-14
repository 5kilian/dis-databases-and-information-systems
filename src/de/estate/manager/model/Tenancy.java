package de.estate.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TENANCY")
public class Tenancy extends Contract {

    @Column(columnDefinition = "DATE")
    private Date start;

    @Column(columnDefinition = "INT")
    private int duration;

    @Column(columnDefinition = "DOUBLE")
    private double cost;

    @OneToOne(mappedBy = "APARTMENT")
    private Apartment apartment;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }


    public Tenancy() {

    }


    /**
     * Copy Constructor
     */
    public Tenancy(Contract contract) {

        id = contract.id;
        date = contract.date;
        place = contract.place;

    }
}
