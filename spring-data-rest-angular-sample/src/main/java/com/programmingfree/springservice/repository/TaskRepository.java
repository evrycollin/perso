package com.programmingfree.springservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programmingfree.springservice.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByTaskArchived(@Param("archivedfalse") int taskArchivedFalse);

    List<Task> findByTaskStatus(@Param("status") String taskStatus);
    
    Page<Task> findAllByTaskStatus(@Param("status") String taskStatus, Pageable arg0);

    @Query("from Task t where lower(t.taskDescription) like concat('%', lower(:taskDescription), '%') ")
    List<Task> findByTaskDescription(@Param("taskDescription") String taskDescription);

    @Query("from Task t where lower(t.taskDescription) like concat('%', lower(:taskDescription), '%')  and t.taskStatus=:taskStatus")
    List<Task> findByTaskDescriptionAndTaskStatus(@Param("taskDescription") String taskDescription, @Param("taskStatus") String taskStatus );
    
    
}
