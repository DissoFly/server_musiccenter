package com.dissofly.musicplayer.service;

import java.util.List;

import com.dissofly.musicplayer.entity.User;

public interface IUserService {
	
	void save(User user);
	
	User findByAccount(String account);
	
	List<User> getAll();

}
