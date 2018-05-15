package de.estate.manager.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "CONTRACTS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Contract {

    @Id
    @GeneratedValue
    protected int id = -1;

    @Column(columnDefinition = "DATE")
    protected Date date;

    @Column(columnDefinition = "VARCHAR(255)")
    protected String place;

    @ManyToOne
    protected Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean hasPerson() {
        return person != null;
    }
}


