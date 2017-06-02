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

import com.dissofly.musicplayer.entity.UploadMessage;
import com.dissofly.musicplayer.repository.IUploadMessageRepository;
@Component
@Service
@Transactional
public class DefaultUploadMessageService implements IUploadMessageService{
	@Autowired
	IUploadMessageRepository uploadMessageRepository;

	@Override
	public UploadMessage save(UploadMessage uploadMessage) {
		return uploadMessageRepository.save(uploadMessage);
		
	}

	@Override
	public UploadMessage findById(Integer id) {
		// TODO Auto-generated method stub
		return uploadMessageRepository.findOne(id);
	}

	@Override
	public void deleteById(Integer id) {
		uploadMessageRepository.delete(id);
		
	}

	@Override
	public List<UploadMessage> getAll() {
		// TODO Auto-generated method stub
		return (List<UploadMessage>) uploadMessageRepository.findAll();
	}

	@Override
	public Page<UploadMessage> getAll(Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return uploadMessageRepository.findAll(pageReqeust);
	}

	@Override
	public Integer getCount() {
		// TODO Auto-generated method stub
		return (int) uploadMessageRepository.count();
	}
	
	
	
	
}
