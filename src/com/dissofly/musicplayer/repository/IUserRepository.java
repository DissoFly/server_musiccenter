package com.dissofly.musicplayer.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dissofly.musicplayer.entity.User;
@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Integer>{

	@Query("from User where account= ?1 ")
	User findByAccount(String account);
	
}
