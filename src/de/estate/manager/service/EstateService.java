package de.estate.manager.service;

import de.estate.manager.model.*;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class EstateService {

    private SessionFactory sessionFactory;

    public EstateService() {
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }

    public List<Estate> getAll() {
        List list = new ArrayList();

        list.addAll(getAllApartments());
        list.addAll(getAllHouses());
        list.addAll(getAllEstates());

        return list;
    }

    public List getAllEstates() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List estates = session.createQuery("FROM Estate e WHERE e.id NOT IN " +
                "(SELECT e.id FROM Estate e, House h WHERE e.id = h.id) " +
                "AND e.id NOT IN " +
                "(SELECT e.id FROM Estate e, Apartment a WHERE e.id = a.id)").list();
        session.getTransaction().commit();
        return estates;
    }

    public List getAllHouses() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List houses = session.createQuery("FROM House").list();
        session.getTransaction().commit();
        return houses;
    }

    public List getAllApartments() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List apartments = session.createQuery("FROM Apartment ").list();
        session.getTransaction().commit();
        return apartments;
    }

    public Integer addEstate(Estate Estate) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Integer did = (Integer) session.save(Estate);
        session.getTransaction().commit();
        return did;
    }


    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Estate estate = (Estate) session.get(Estate.class, id);
        session.delete(estate);
    }

    public void delete(Estate estate) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(estate);
    }

    public Estate get(int estateId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Estate estate = (Estate) session.get(Estate.class, estateId);
        session.getTransaction().commit();
        return estate;
    }
}
