package de.estate.manager.model;

import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;

import javax.persistence.*;


@Entity
@Table(name = "AGENTS")
@SQLInsert(sql = "SELECT * FROM AGENTS WHERE ID = ?")
@SQLUpdate(sql = "UPDATE AGENTS SET NAME = ?, LOGIN = ?, PASSWORD = ?, ADDRESS = ? WHERE ID = ?")
@Loader(namedQuery = "Agent")
@NamedNativeQuery(name = "Agent", query = "SELECT * FROM AGENTS WHERE ID = ?", resultClass = Agent.class)
public class Agent {



    @Id
    @GeneratedValue
    private int id = -1;

    @Column(columnDefinition = "VARCHAR(255)")
    private String address;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(columnDefinition = "VARCHAR(255)")
    private String login;

    @Column(columnDefinition = "VARCHAR(255)")
    private String password;

    public Agent() {

    }

    public Agent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
