package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.repository.IUserRepository;


@Component
@Service
@Transactional
public class DefaultUserService implements IUserService {
	
	@Autowired
	IUserRepository userRepo;

	@Override
	public void save(User user) {
		userRepo.save(user);
	}

	@Override
	public User findByAccount(String account) {
		return userRepo.findByAccount(account);
	}

	@Override
	public List<User> getAll() {
		
		return (List<User>) userRepo.findAll();
	}

	@Override
	public User findById(Integer id) {
		return userRepo.findOne(id);
	}
	
	

}
