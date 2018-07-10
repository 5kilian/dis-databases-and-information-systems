package de.estate.warehouse.service;

import de.estate.warehouse.model.Article;
import de.estate.warehouse.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class ArticleService {

    private SessionFactory sessionFactory;

    public ArticleService() {
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }

    public Article getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT a FROM ARTICLEID a WHERE a.NAME = :name");
        query.setParameter("name", name);
        Article article = (Article) query.getSingleResult();
        session.getTransaction().commit();
        return article;
    }
}
