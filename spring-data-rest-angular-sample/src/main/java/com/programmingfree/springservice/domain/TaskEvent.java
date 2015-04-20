package com.programmingfree.springservice.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "task_Event")
public class TaskEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String description;

    @ManyToOne
    private Task task;

    @OneToMany(mappedBy = "event")
    Set<TaskEventLocation> locations;

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Task getTask() {
	return task;
    }

    public void setTask(Task task) {
	this.task = task;
    }

    public Set<TaskEventLocation> getLocations() {
	return locations;
    }

    public void setLocations(Set<TaskEventLocation> locations) {
	this.locations = locations;
    }

}
