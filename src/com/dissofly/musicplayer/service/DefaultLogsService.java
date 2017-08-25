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

import com.dissofly.musicplayer.entity.Logs;
import com.dissofly.musicplayer.repository.ILogsRepository;
@Component
@Service
@Transactional
public class DefaultLogsService implements ILogsService{

	@Autowired
	ILogsRepository logsRepo;
	
	@Override
	public Logs save(Logs logs) {
		// TODO Auto-generated method stub
		return logsRepo.save(logs);
	}
	
	@Override
	public Logs save(int userId, String entity, String text) {
		Logs logs=new Logs();
		logs.logsSave(userId, entity, text);
		return logsRepo.save(logs);
	}

	@Override
	public Page<Logs> findAll(Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return logsRepo.findAll(pageReqeust);
	}

	@Override
	public List<Logs> findAll() {
		return (List<Logs>) logsRepo.findAll();
	}

	@Override
	public Integer getAllCount() {
		// TODO Auto-generated method stub
		return (int) logsRepo.count();
	}

	
}
