package de.estate.warehouse.service;

import de.estate.warehouse.model.Shop;
import de.estate.warehouse.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ShopService {

    private SessionFactory sessionFactory;

    public ShopService() {
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }

    public List getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List shops = session.createQuery("FROM Shop").list();
        session.getTransaction().commit();
        return shops;
    }

    public Shop getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Shop s WHERE s.name = :name");
        query.setParameter("name", name);
        Shop shop = (Shop) query.getSingleResult();
        session.getTransaction().commit();
        return shop;
    }
}
