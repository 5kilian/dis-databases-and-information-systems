package de.estate.manager.service;

import de.estate.manager.model.Apartment;
import de.estate.manager.model.Contract;
import de.estate.manager.model.Estate;
import de.estate.manager.model.House;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EstateService {

    private SessionFactory sessionFactory;

    public EstateService(){
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }


    public List<Estate> getAll(){

    }

    public List<Estate> getAllEstates(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Estate ").list();
    }

    public List<House> getAllHouses(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM House ").list();
    }

    public List<Apartment> getAllApartments(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Apartment ").list();
    }

    public Integer addEstate(Estate Estate) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Integer did = (Integer) session.save(Estate);
        session.getTransaction().commit();
        return did;
    }


    public void delete(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Estate estate = (Contract) session.get(Estate.class, id);
        session.delete(estate);
    }

    public void delete(Contract contract) {
        delete(contract.getId());
    }

    public Contract get(int estateId) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Estate estate = (Estate) session.get(Estate.class, estateId);
        session.getTransaction().commit();
        return estate;
    }

}