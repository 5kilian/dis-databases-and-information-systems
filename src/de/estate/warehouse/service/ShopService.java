package de.estate.warehouse.service;

import de.estate.warehouse.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.List;

public class ShopService {

    private SessionFactory sessionFactory;

    public ShopService() {
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }

    public List getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List shops = session.createQuery("FROM SHOPID").list();
        session.getTransaction().commit();
        return shops;
    }
}
