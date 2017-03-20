package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dissofly.musicplayer.entity.PublicSong;

public interface IPublicSongService {

	PublicSong getBySongId(Integer songId);
	PublicSong save(PublicSong publicSong);
	void delectById(Integer songId);
	
	List<PublicSong> getByName(String name);
	List<PublicSong> getByArtist(String artist);
	List<PublicSong> getByAlbum(String album);
	Page<PublicSong> getAll(Integer page);
	int getAllCount();
}
