package com.dissofly.musicplayer.service;

import org.springframework.data.domain.Page;

import com.dissofly.musicplayer.entity.Information;

public interface IInformationService {
	Information save(Information information);
	void delectById(Integer id);
	Page<Information> getAll(Integer page);
	Information getById(Integer id);

}
