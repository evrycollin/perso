package com.programmingfree.springservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmingfree.springservice.domain.m1.TaskEvent;


public interface TaskEventRepository extends JpaRepository<TaskEvent, Integer> {

}
