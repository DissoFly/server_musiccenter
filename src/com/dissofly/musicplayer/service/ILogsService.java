package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dissofly.musicplayer.entity.Logs;

public interface ILogsService {
	Logs save(Logs logs);
	Logs save(int userId,String entity,String text);
	Integer getAllCount();
	Page<Logs> findAll(Integer page);
	List<Logs> findAll();

}
