package com.dissofly.musicplayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dissofly.musicplayer.entity.Information;
import com.dissofly.musicplayer.repository.IInformationRepository;
import com.dissofly.musicplayer.repository.IMusicLrcRepository;
@Component
@Service
@Transactional
public class DefaultInformationService implements IInformationService{

	@Autowired
	IInformationRepository inforRepo;
	
	
	@Override
	public Information save(Information information) {
		// TODO Auto-generated method stub
		return inforRepo.save(information);
	}

	@Override
	public void delectById(Integer id) {
		inforRepo.delete(id);
		
	}

	@Override
	public Page<Information> getAll(Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return inforRepo.findAll(pageReqeust);
	}

	@Override
	public Information getById(Integer id) {
		// TODO Auto-generated method stub
		return inforRepo.findOne(id);
	}

}
