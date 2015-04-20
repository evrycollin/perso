package com.programmingfree.springservice.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task_User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String login;

    @Column
    private String firstname;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;

    @OneToOne
    private Address address;

    @ManyToMany
    @JoinTable(
	    name = "task_users_groups", 
	    joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, 
	    inverseJoinColumns={@JoinColumn(name="GROUP_ID", referencedColumnName="ID")} 
	    )
    private List<Group> groups;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getFirstname() {
	return firstname;
    }

    public void setFirstname(String firstname) {
	this.firstname = firstname;
    }

    public Set<Task> getTasks() {
	return tasks;
    }

    public void setTasks(Set<Task> tasks) {
	this.tasks = tasks;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public List<Group> getGroups() {
	return groups;
    }

    public void setGroups(List<Group> groups) {
	this.groups = groups;
    }
}
