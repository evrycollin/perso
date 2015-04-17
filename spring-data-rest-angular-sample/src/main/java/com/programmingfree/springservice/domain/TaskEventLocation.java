package com.programmingfree.springservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task_Event_location")
public class TaskEventLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String location;

    @ManyToOne
    private TaskEvent event;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public TaskEvent getEvent() {
	return event;
    }

    public void setEvent(TaskEvent event) {
	this.event = event;
    }

}
