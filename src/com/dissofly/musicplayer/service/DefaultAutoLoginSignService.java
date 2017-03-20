package com.dissofly.musicplayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dissofly.musicplayer.entity.AutoLoginSign;
import com.dissofly.musicplayer.repository.IAutoLoginSignRepository;

@Component
@Service
@Transactional
public class DefaultAutoLoginSignService implements IAutoLoginSignService{

	@Autowired
	IAutoLoginSignRepository autoLSRepo;
	
	@Override
	public AutoLoginSign findByUserId(int userId) {
		return autoLSRepo.findByUserId(userId);
	}

	@Override
	public void save(AutoLoginSign autoLoginSign) {
		autoLSRepo.save(autoLoginSign);
		
	}
	
}
