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
            ResultSet contractss = DB2Connection.getConnection().prepareStatement("SELECT * FROM CONTRACTS c WHERE c.ID NOT IN (" +
                    "SELECT c.ID FROM CONTRACTS c, PURCHASES p , TENANCIES t WHERE c.ID = p.ID OR c.ID = t.ID" +
                    ")").executeQuery();
            ResultSet purchases = DB2Connection.getConnection().prepareStatement("SELECT * FROM CONTRACTS c, PURCHASES p WHERE c.ID = p.ID").executeQuery();
            ResultSet tenancies = DB2Connection.getConnection().prepareStatement("SELECT * FROM CONTRACTS c, TENANCIES t WHERE c.ID = t.ID").executeQuery();
            while (contractss.next()) {
                Contract contract = new Contract();
                contract.setId(contractss.getInt("id"));
                contract.setPlace(contractss.getString("place"));
                contract.setDate(contractss.getString("date"));

                contracts.add(contract);
            }

            while (purchases.next()) {
                Purchase contract = new Purchase();
                contract.setId(purchases.getInt("id"));
                contract.setDate(purchases.getString("date"));
                contract.setPlace(purchases.getString("place"));

                contract.setRate(purchases.getInt("rate"));
                contract.setInstallments(purchases.getInt("installments"));


                contracts.add(contract);
            }

            while (tenancies.next()) {
                Tenancy contract = new Tenancy();

                contract.setId(tenancies.getInt("id"));
                contract.setDate(tenancies.getString("date"));
                contract.setPlace(tenancies.getString("place"));


                contract.setCost(tenancies.getInt("cost"));
                contract.setDuration(tenancies.getInt("duration"));
                contract.setStart(tenancies.getString("start"));

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
