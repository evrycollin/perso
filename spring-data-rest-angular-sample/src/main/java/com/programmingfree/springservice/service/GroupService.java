package com.programmingfree.springservice.service;

import org.springframework.stereotype.Service;

import com.programmingfree.springservice.domain.m1.Group;
import com.programmingfree.springservice.domain.m1.User;

@Service
public class GroupService {

	public void addUser(Group group, User user) {

		group.getUsers().add(user);

	}
	public void removeUser(Group group, User user) {

		group.getUsers().remove(user);

	}
}
