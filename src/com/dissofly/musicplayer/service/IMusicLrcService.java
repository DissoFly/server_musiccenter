package com.dissofly.musicplayer.service;

import com.dissofly.musicplayer.entity.MusicLrc;

public interface IMusicLrcService {
	MusicLrc save(MusicLrc musicLrc);
	MusicLrc findBySongId(Integer songId);
	MusicLrc findById(Integer id);
	void delectById(Integer id);

}
