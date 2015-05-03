package com.programmingfree.springservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmingfree.springservice.domain.m1.Group;


public interface GroupRepository extends JpaRepository<Group, Integer> {

}
