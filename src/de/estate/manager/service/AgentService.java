package de.estate.manager.service;

import de.estate.manager.model.Agent;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class AgentService {


    private SessionFactory sessionFactory;

    public AgentService() {
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }


    public List getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List agents = session.createQuery("FROM Agent").list();
        session.getTransaction().commit();
        return agents;
    }

    public Integer addAgent(Agent agent) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Integer did = (Integer) session.save(agent);
        session.getTransaction().commit();
        return did;
    }

    public Agent get(int agentId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Agent agent = (Agent) session.get(Agent.class, agentId);
        session.getTransaction().commit();
        return agent;
    }

    public void save(Agent agent) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(agent);
        session.getTransaction().commit();
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(Agent.class, id));
        session.getTransaction().commit();
    }

    public void delete(Agent agent) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(agent);
        session.getTransaction().commit();
    }


    public Agent validate(String name, String password) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Agent a WHERE a.login = :login AND a.password = :password ");
        query.setParameter("login", name);
        query.setParameter("password", password);

        List agents = query.list();
        session.getTransaction().commit();
        if (!agents.isEmpty()) {
            return (Agent) agents.get(0);

        }
        return null;
    }
}
