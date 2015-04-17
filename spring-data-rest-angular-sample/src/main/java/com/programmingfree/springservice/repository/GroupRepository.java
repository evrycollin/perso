package com.programmingfree.springservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.programmingfree.springservice.domain.Group;

@RepositoryRestResource
public interface GroupRepository extends JpaRepository<Group, Integer> {

}
