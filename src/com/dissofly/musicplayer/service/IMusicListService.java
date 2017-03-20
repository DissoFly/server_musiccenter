package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dissofly.musicplayer.entity.MusicList;

public interface IMusicListService {

	MusicList save(MusicList musicList);
	void delectById(Integer id);
	MusicList findById(Integer id);
	Page<MusicList> getAll(Integer page);
	Page<MusicList> findByUserId(Integer id,Integer page);
	Page<MusicList> findByName(String listName,Integer page);
}
