package com.programmingfree.springservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmingfree.springservice.domain.TaskEventLocation;

public interface TaskEventLocationRepository extends JpaRepository<TaskEventLocation, Integer> {

}
