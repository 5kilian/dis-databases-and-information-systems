package de.estate.manager.Hibernate;



import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.bytecode.internal.javassist.AccessOptimizerAdapter;
import org.hibernate.cfg.Configuration;
import de.estate.manager.model.*;
import java.util.List;

public class EstateService_Hib {
/*

    // Methoden für Persons

    public List<Person> getAllPersons(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List personen = session.createQuery("select * from Persons ");
        return personen;

    }

    public Long addPerson(String firstName, String name, String adress ) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Person person = new Person();
        person.setFirstName(firstName);
        person.setName(name);
        person.setAddress(adress);
        Long did = session.save(person);
        session.getTransaction().commit();
        return did;
    }

    public Person loadPerson(int personId) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Person person = (Person) session.get(Person.class, personId); session.getTransaction().commit();
        return person;
    }

    public void deletePerson(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Person person = (Person) session.get(Person.class, id);
        session.delete(person);
    }

    //Methoden für Agents

    public List<Agent> getAllAgents(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        List agents = session.createQuery("select * from Agents ");
        return agents;
    }

    public Long addAgent(String address ,String name, String login, String password) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Agent agent = new Agent();
        agent.setAddress(address);
        agent.setLogin(login);
        agent.setName(name);
        agent.setPassword(password);
        Long did = session.save(agent);
        session.getTransaction().commit();
        return did;
    }

    public Agent loadAgent(int agentId) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Agent agent = (Agent) session.get(Agent.class, agentId);
        session.getTransaction().commit();
        return agent;
    }

    public void deleteAgent(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Agent agent = (Agent) session.get(Person.class, id);
        session.delete(agent);
    }

    //Methoden für Houses

    public List<House> getAllHouses(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        List houses = session.createQuery("select * from Estates e, Houses h where e.id = h.id ");
        return houses;
    }

    public Long addHouse(String city , int zip, String street, int number,
                         int area, int floors, double price, boolean garden ) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        House house = new House();
        house.setCity(city);
        house.setZip(zip);
        house.setStreet(street);
        house.setNumber(number);
        house.setArea(area);
        house.setFloors(floors);
        house.setPrice(price);
        house.setGarden(garden);
        Long did = session.save(house);
        session.getTransaction().commit();
        return did;
    }

    public House loadHouse(int id) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        House house = (House) session.get(House.class, id);
        session.getTransaction().commit();
        return house;
    }

    public void deleteHouse(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        House house = (House) session.get(House.class, id);
        session.delete(house);
    }

    //Methoden für Apartments

    public List<Apartment> getAllApartments(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        List apartmens = session.createQuery("select * from Estates e, Apartmens a where e.id = a.id ");
        return apartmens;
    }

    public Long addApartment(String city , int zip, String street, int number,
                         int area, int floor, double rent, double rooms, boolean balcony, boolean kitchen) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Apartment apartment = new Apartment();
        apartment.setCity(city);
        apartment.setZip(zip);
        apartment.setStreet(street);
        apartment.setNumber(number);
        apartment.setArea(area);
        apartment.setFloor(floor);
        apartment.setRent(rent);
        apartment.setRooms(rooms);
        apartment.setBalcony(balcony);
        apartment.setKitchen(kitchen);
        Long did = session.save(apartment);
        session.getTransaction().commit();
        return did;
    }

    public Apartment loadApartment(int id) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Apartment apartment = (Apartment) session.get(Apartment.class, id);
        session.getTransaction().commit();
        return apartment;
    }

    public void deleteApartment(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Apartment apartment = (Apartment) session.get(Apartment.class, id);
        session.delete(apartment);
    }


    //Methoden für Tenencies

    public List<Tenancy> getAllTenancies(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        List tenancies = session.createQuery("select * from Tenancies t, Contracts c where c.id = t.id ");
        return tenancies;
    }

    public Long addTenacy(Date date, String place, Person person, Date start, int duration, double cost ) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Tenancy tenancy = new Tenancy();
        tenancy.setDate(date);
        tenancy.setPlace(place);
        tenancy.setPerson(person);
        tenancy.setStart(start);
        tenancy.setDuration(duration);
        tenancy.setCost(cost);

        Long did = session.save(tenancy);
        session.getTransaction().commit();
        return did;
    }

    public Tenancy loadTenancy(int id) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Tenancy tenancy = (Tenancy) session.get(Tenancy.class, id);
        session.getTransaction().commit();
        return tenancy;
    }

    public void deleteTenancy(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Tenancy tenancy = (Tenancy) session.get(Tenancy.class, id);
        session.delete(tenancy);
    }

    //Methoden für Purchases

    public List<Purchase> getAllPurchases(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        List purchases = session.createQuery("select * from Purchases p, Contracts c where c.id = p.id ");
        return purchases;
    }

    public Long addPurchase(Date date, String place, Person person, int installments, int rate ) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Purchase purchase = new Purchase();
        purchase.setDate(date);
        purchase.setPlace(place);
        purchase.setPerson(person);
        purchase.setInstallments(installments);
        purchase.setRate(rate);

        Long did = session.save(purchase);
        session.getTransaction().commit();
        return did;
    }

    public Purchase loadPurchase(int id) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Purchase purchase = (Purchase) session.get(Purchase.class, id);
        session.getTransaction().commit();
        return purchase;
    }

    public void deletePurchase(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Purchase purchase = (Purchase) session.get(Purchase.class, id);
        session.delete(purchase);
    }
    */
}
