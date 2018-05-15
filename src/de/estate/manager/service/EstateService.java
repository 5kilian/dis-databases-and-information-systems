package de.estate.manager.service;

import de.estate.manager.model.*;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class EstateService {

    private SessionFactory sessionFactory;

    public EstateService(){
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }


    // geht das so mit Object? :D

    public List<Estate> getAll(){


            List list = new ArrayList();

            for (Object element : getAllEstates()) {
                list.add(element);
            }
            for (Object element : getAllApartments()) {
                list.add(element);
            }

            for (Object element : getAllHouses()) {
                list.add(element);
            }

            return list;
        }

    public List<Estate> getAllEstates(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Estate e where e.id not in " +
                "(from Estate e, House h where e.id = h.id) " +
                "and e.id not in " +
                "(from Estate e, Apartment a where e.id = a.id)").list();
    }

    public List<Estate> getAllHouses(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM House ").list();
    }

    public List<Estate> getAllApartments(){
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
        Estate estate = (Estate) session.get(Estate.class, id);
        session.delete(estate);
    }

    public void delete(Estate estate) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        session.delete(estate);
    }

    public Estate get(int estateId) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Estate estate = (Estate) session.get(Estate.class, estateId);
        session.getTransaction().commit();
        return estate;
    }
}
