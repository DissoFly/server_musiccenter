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

import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.repository.IPublicSongRepository;

@Component
@Service
@Transactional
public class DefaultPublicSongService implements IPublicSongService{

	@Autowired
	IPublicSongRepository songRepo;

	@Override
	public PublicSong getBySongId(Integer songId) {
		
		return songRepo.findOne(songId);
	}

	@Override
	public PublicSong save(PublicSong publicSong) {
		// TODO Auto-generated method stub
		return songRepo.save(publicSong);
	}

	@Override
	public void delectById(Integer songId) {
		songRepo.delete(songId);
	}

	@Override
	public List<PublicSong> getByName(String name) {
		System.out.println(name);
		return songRepo.getByName(name);
	}

	@Override
	public List<PublicSong> getByArtist(String artist) {
		// TODO Auto-generated method stub
		return songRepo.getByArtist(artist);
	}

	@Override
	public List<PublicSong> getByAlbum(String album) {
		// TODO Auto-generated method stub
		return songRepo.getByAlbum(album);
	}

	@Override
	public Page<PublicSong> getAll(Integer page) {
		Sort sort = new Sort(Direction.ASC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return songRepo.findAll(pageReqeust);
	}

	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return (int) songRepo.count();
	}
}
