package de.estate.manager.service;

import de.estate.manager.model.*;
import de.estate.manager.util.DB2Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ContractService {

    public Contract get(int id) {
        return Contract.load(id);
    }

    public List<Contract> getAll() {
        List<Contract> contracts = new ArrayList<>();

        try {
            ResultSet contractss = DB2Connection.getConnection().prepareStatement("SELECT * FROM CONTRACTS c WHERE c.ID NOT IN (\n" +
                    "  SELECT c.ID FROM CONTRACTS c, PURCHASES s WHERE c.ID = s.ID\n" +
                    ") AND c.ID NOT IN (\n" +
                    "  SELECT c.ID FROM CONTRACTS c, TENANCIES t WHERE c.ID = t.ID\n" +
                    ")").executeQuery();
            ResultSet purchases = DB2Connection.getConnection().prepareStatement("SELECT * FROM CONTRACTS c, PURCHASES p WHERE c.ID = p.ID").executeQuery();
            ResultSet tenancies = DB2Connection.getConnection().prepareStatement("SELECT * FROM CONTRACTS c, TENANCIES t WHERE c.ID = t.ID").executeQuery();

            while (contractss.next()) {
                Contract contract = new Contract();
                contract.setId(contractss.getInt("id"));
                contract.setPlace(contractss.getString("place"));
                contract.setDate(contractss.getDate("date"));
                if (contractss.getInt("person") > 0) {
                    contract.setPerson(Person.load(contractss.getInt("person")));
                }

                contracts.add(contract);
            }

            while (purchases.next()) {
                Purchase contract = new Purchase();
                contract.setId(purchases.getInt("id"));
                contract.setDate(purchases.getDate("date"));
                contract.setPlace(purchases.getString("place"));

                contract.setRate(purchases.getInt("rate"));
                contract.setInstallments(purchases.getInt("installments"));
                contract.setHouse(House.load(purchases.getInt("house")));

                if (purchases.getInt("person") > 0) {
                    contract.setPerson(Person.load(purchases.getInt("person")));
                }

                contracts.add(contract);
            }

            while (tenancies.next()) {
                Tenancy contract = new Tenancy();

                contract.setId(tenancies.getInt("id"));
                contract.setDate(tenancies.getDate("date"));
                contract.setPlace(tenancies.getString("place"));


                contract.setCost(tenancies.getDouble("cost"));
                contract.setDuration(tenancies.getInt("duration"));
                contract.setStart(tenancies.getDate("start"));
                contract.setApartment(Apartment.load(tenancies.getInt("apartment")));

                if (tenancies.getInt("person") > 0) {
                    contract.setPerson(Person.load(tenancies.getInt("person")));
                }

                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contracts.sort(Comparator.comparingInt(Contract::getId));
        return contracts;
    }

    public void delete(Contract contract) {
        delete(contract.getId());
    }

    public void delete(int id) {
        try {
            List<PreparedStatement> statements = new ArrayList<>();
            statements.add(DB2Connection.getConnection().prepareStatement("DELETE FROM PURCHASES WHERE ID = ?"));
            statements.add(DB2Connection.getConnection().prepareStatement("DELETE FROM TENANCIES WHERE ID = ?"));
            statements.add(DB2Connection.getConnection().prepareStatement("DELETE FROM CONTRACTS WHERE ID = ?"));

            for (PreparedStatement statement : statements) {
                statement.setInt(1, id);
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
