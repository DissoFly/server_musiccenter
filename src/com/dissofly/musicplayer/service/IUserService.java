package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.User;

public interface IUserService {
	
	User save(User user);
	
	Page<User> findAll(Integer page);
	
	int getAllCount();
	
	User findByAccount(String account);
	
	List<User> getAll();
	
	User findById(Integer id);

}
