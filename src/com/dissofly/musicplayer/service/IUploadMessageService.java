package com.dissofly.musicplayer.service;

import java.util.List;

import com.dissofly.musicplayer.entity.UploadMessage;


public interface IUploadMessageService {
	UploadMessage save(UploadMessage uploadMessage);
	
	UploadMessage findById(Integer id);
	
	void deleteById(Integer id);
	
	List<UploadMessage> getAll();
	

}
