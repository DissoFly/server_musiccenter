package com.dissofly.musicplayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dissofly.musicplayer.entity.MusicLrc;
import com.dissofly.musicplayer.repository.IMusicLrcRepository;
@Component
@Service
@Transactional
public class DefaultMusicLrcService implements IMusicLrcService{
	
	@Autowired
	IMusicLrcRepository lrcRepo;
	

	@Override
	public MusicLrc save(MusicLrc musicLrc) {
		// TODO Auto-generated method stub
		return lrcRepo.save(musicLrc);
	}

	@Override
	public MusicLrc findBySongId(Integer songId) {
		// TODO Auto-generated method stub
		return lrcRepo.findBySongId(songId);
	}

	@Override
	public MusicLrc findById(Integer id) {
		// TODO Auto-generated method stub
		return lrcRepo.findOne(id);
	}

	@Override
	public void delectById(Integer id) {
		lrcRepo.delete(id);
		
	}

}
