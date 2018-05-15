package de.estate.manager.service;

import de.estate.manager.model.Agent;
import de.estate.manager.model.Person;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PersonService {

    private SessionFactory sessionFactory;

    public PersonService(){
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }

    public List getAll(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Person ").list();
    }

    public Integer addPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Integer did = (Integer) session.save(person);
        session.getTransaction().commit();
        return did;
    }

    public Person getPerson(int id) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Person person = (Person) session.get(Person.class, id);
        session.getTransaction().commit();
        return person;
    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Person person = (Person) session.get(Person.class, id);
        session.delete(person);
    }

    public void delete(Person person) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        session.delete(person);
    }


    public Person loadPerson(int personId) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Person person = (Person) session.get(Person.class, personId); session.getTransaction().commit();
        return person;
    }

}
