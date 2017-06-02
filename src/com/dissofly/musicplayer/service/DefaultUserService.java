package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	public User save(User user) {
		return userRepo.save(user);
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

	@Override
	public Page<User> findAll(Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return userRepo.findAll(pageReqeust);
	}

	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return (int) userRepo.count();
	}
	
	

}
