package de.estate.manager.service;

import de.estate.manager.model.Agent;
import de.estate.manager.util.DB2Connection;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AgentService {


    private SessionFactory sessionFactory;

    public AgentService(){
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }



    public List getAll(){
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
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Agent agent = (Agent) session.get(Agent.class, agentId);
        session.getTransaction().commit();
        return agent;
    }

    public void save(Agent agent) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(agent);
        session.getTransaction().commit();
    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
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
        try {
            PreparedStatement statement = DB2Connection.getConnection().prepareStatement("SELECT * FROM AGENTS WHERE LOGIN = ? AND PASSWORD = ?");
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Agent agent = new Agent();
                agent.setId(result.getInt("id"));
                agent.setName(result.getString("name"));
                agent.setLogin(result.getString("login"));
                agent.setPassword(result.getString("password"));
                agent.setAddress(result.getString("address"));
                return agent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
