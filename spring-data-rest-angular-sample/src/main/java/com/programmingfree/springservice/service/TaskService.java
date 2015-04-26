package com.programmingfree.springservice.service;

import java.util.List;

import javax.inject.Inject;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Service;

import com.fastrest.core.EntityParam;
import com.programmingfree.springservice.domain.Task;
import com.programmingfree.springservice.repository.TaskRepository;

@Service
public class TaskService {

	@Inject
	private TaskRepository taskRepository;

	public List<Task> getTaskServiceMethode( @EntityParam Task task, String param1, Integer param2) {
		return taskRepository.findAll();
	}
}
