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
@Table(name = "task_list")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String taskName;

    @Column
    private String taskDescription;

    @Column
    private String taskPriority;

    @Column
    private String taskStatus;

    @Column
    private int taskArchived = 0;

    @OneToMany(mappedBy = "task")
    Set<TaskEvent> events;

    @ManyToOne
    User user;

    public Set<TaskEvent> getEvents() {
	return events;
    }

    public void setEvents(Set<TaskEvent> events) {
	this.events = events;
    }

    public int getTaskId() {
	return id;
    }

    public void setTaskId(int taskId) {
	this.id = taskId;
    }

    public String getTaskName() {
	return taskName;
    }

    public void setTaskName(String taskName) {
	this.taskName = taskName;
    }

    public String getTaskDescription() {
	return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
	this.taskDescription = taskDescription;
    }

    public String getTaskPriority() {
	return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
	this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
	return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
	this.taskStatus = taskStatus;
    }

    public int isTaskArchived() {
	return taskArchived;
    }

    public void setTaskArchived(int taskArchived) {
	this.taskArchived = taskArchived;
    }

    @Override
    public String toString() {
	return "Task [id=" + id + ", taskName=" + taskName
		+ ", taskDescription=" + taskDescription + ", taskPriority="
		+ taskPriority + ",taskStatus=" + taskStatus + "]";
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

}
