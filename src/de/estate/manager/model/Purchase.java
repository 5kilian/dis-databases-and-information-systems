package de.estate.manager.model;

import javax.persistence.*;

@Entity
@Table(name = "PURCHASES")
public class Purchase extends Contract {

    @Column(columnDefinition = "INT")
    private int installments;

    @Column(columnDefinition = "INT")
    private int rate;

    @OneToOne
    @JoinColumn(name = "house")
    @Column(unique = true)
    private House house;

    public Purchase() {

    }

    /**
     * Copy Constructor
     */
    public Purchase(Contract contract) {

        id = contract.id;
        date = contract.date;
        place = contract.place;


    }


    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}