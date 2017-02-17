package com.dissofly.musicplayer.service;

import com.dissofly.musicplayer.entity.User;

public interface IUserService {
	
	void save(User user);
	
	User findByAccount(String account);

}
