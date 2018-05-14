package de.estate.manager.model;

import javax.persistence.*;


@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue
    private int id = -1;

    @Column(columnDefinition = "VARCHAR(255)")
    private String firstName;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(columnDefinition = "VARCHAR(255)")
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}