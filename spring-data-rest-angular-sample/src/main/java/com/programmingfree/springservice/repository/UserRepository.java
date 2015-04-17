package com.programmingfree.springservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.programmingfree.springservice.domain.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Query(nativeQuery = true, value = "delete from task_users_groups where user_id=:user and group_id=:group")
    void removeGroupMembership(@Param("user") Integer user,
	    @Param("group") Integer group);

    @Transactional
    @Query(nativeQuery = true, value = "insert into task_users_groups (user_id, group_id) values (:user, :group)")
    void addGroupMembership(@Param("user") Integer user,
	    @Param("group") Integer group);
    
}
