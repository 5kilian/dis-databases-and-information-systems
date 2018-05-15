package de.estate.manager.service;

import de.estate.manager.model.Contract;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ContractService {

    private SessionFactory sessionFactory;

    public ContractService(){
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }

    public List getAll(){

        List list = new ArrayList();

        for (Object element : getAllContracts()) {
            list.add(element);
        }
        for (Object element : getAllPurchases()) {
            list.add(element);
        }

        for (Object element : getAllTenacies()) {
            list.add(element);
        }

        return list;
    }

    public List getAllContracts() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List contracts = session.createQuery("FROM Contract c WHERE c.id NOT IN " +
                "(SELECT c.id FROM Contract c, Purchase p WHERE c.id = p.id) " +
                "AND c.id NOT IN " +
                "(SELECT c.id FROM Contract c, Tenancy t WHERE c.id = t.id)").list();
        session.getTransaction().commit();
        return contracts;
    }

    public List getAllPurchases() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List purchases = session.createQuery("FROM Purchase").list();
        session.getTransaction().commit();
        return purchases;
    }

    public List getAllTenacies() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List tenancies = session.createQuery("FROM Tenancy ").list();
        session.getTransaction().commit();
        return tenancies;
    }

    public Integer addContract(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Integer did = (Integer) session.save(contract);
        session.getTransaction().commit();
        return did;
    }


    public void delete(int id){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Contract contract = session.get(Contract.class, id);
        session.delete(contract);
        session.getTransaction().commit();
    }

    public void delete(Contract contract) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        session.delete(contract);
        session.getTransaction().commit();
    }

    public Contract get(int contractId) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Contract contract = session.get(Contract.class, contractId);
        session.getTransaction().commit();
        return contract;
    }

    public void save(Contract contract){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(contract);
        session.getTransaction().commit();
    }

}
