package com.programmingfree.springservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.programmingfree.springservice.domain.TaskEvent;

@RepositoryRestResource
public interface TaskEventRepository extends JpaRepository<TaskEvent, Integer> {

}
