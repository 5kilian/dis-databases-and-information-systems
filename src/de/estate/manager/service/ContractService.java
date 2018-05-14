package de.estate.manager.service;

import de.estate.manager.model.Contract;
import de.estate.manager.util.SessionFactory_hib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ContractService {

    private SessionFactory sessionFactory;

    public ContractService(){
        sessionFactory = SessionFactory_hib.getSessionFactory();
    }

    public List<Contract> getAll(){
        return  getAllContracts().addAll(getAllPurchases().addAll(getAllTenacies())).
    }

    public List<Contract> getAllContracts(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Contract ").list();
    }

    public List<Contract> getAllPurchases(){
        Session session = sessionFactory.getCurrentSession(); session.beginTransaction();
        return session.createQuery("FROM Purchase").list();
    }

    public List<Contract> getAllTenacies(){
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
