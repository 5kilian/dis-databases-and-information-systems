package de.estate.warehouse.service;

import de.estate.warehouse.model.Article;
import de.estate.warehouse.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ArticleService {

    private SessionFactory sessionFactory;

    public ArticleService() {
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }

    public List getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List article = session.createQuery("FROM Article").list();
        session.getTransaction().commit();
        return article;
    }

    public Article getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Article a WHERE a.name = :name");
        query.setParameter("name", name);
        Article article = (Article) query.getSingleResult();
        session.getTransaction().commit();
        return article;
    }
}
