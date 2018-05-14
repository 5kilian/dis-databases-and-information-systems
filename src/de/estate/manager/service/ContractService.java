package de.estate.manager.service;

import de.estate.manager.model.Contract;
import de.estate.manager.model.Purchase;
import de.estate.manager.model.Tenancy;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ContractService {

    private SessionFactory sessionFactory;

    public ContractService(){
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }


    // geht das so mit Object? :D
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

    public List getAllContracts(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Contract c where c.id not in " +
                "(from Contract c, Purchase p where c.id = p.id) " +
                "and c.id not in " +
                "(from Contract c, Tenancy t where c.id = t.id)").list();
    }

    public List getAllPurchases(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Purchase").list();
    }

    public List getAllTenacies(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Tenancy ").list();
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
        Contract contract = (Contract) session.get(Contract.class, id);
        session.delete(contract);
    }

    public void delete(Contract contract) {
        delete(contract.getId());
    }

    public Contract get(int contractId) {
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        Contract contract = (Contract) session.get(Contract.class, contractId);
        session.getTransaction().commit();
        return contract;
    }

}
