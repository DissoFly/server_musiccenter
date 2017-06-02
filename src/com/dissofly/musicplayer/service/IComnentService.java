package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dissofly.musicplayer.entity.Comment;

public interface IComnentService {
	
	Comment save(Comment comment);
	Comment findById(Integer id);
	Page<Comment> findAll(Integer page);
	Page<Comment> findBySongId(Integer songId,Integer page);
	void delectById(Integer id);
	Page<Comment> findByUserId(Integer userId,Integer page);
	List<Comment> findByUserId(Integer userId);
	List<Comment> getAllBySongId(Integer songId);
	int getAllCount();
	

}
