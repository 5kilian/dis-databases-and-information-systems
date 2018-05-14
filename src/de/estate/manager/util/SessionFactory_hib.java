package de.estate.manager.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class SessionFactory_hib {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure(new File("src/de/estate/resources/hibernate.cfg.xml"));
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }

}
