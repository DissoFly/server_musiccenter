package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dissofly.musicplayer.entity.ClickLike;

public interface IClickLikeService {
	
	ClickLike save (ClickLike like);
	void delectById(Integer id);
	Page<ClickLike> findByUserId(Integer userId,Integer page);
	ClickLike findCommentByUserById(Integer userId,Integer commentId);
	ClickLike findMusicByUserById(Integer userId,Integer musicId);
	List<ClickLike> findCommentById(Integer commentId);
	List<ClickLike> findMusicById(Integer musicId);
	ClickLike findById(Integer id);
	
	

}
