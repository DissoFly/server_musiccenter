package com.dissofly.musicplayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dissofly.musicplayer.entity.MusicList;
import com.dissofly.musicplayer.repository.IMusicListRepository;

@Component
@Service
@Transactional
public class DefaultMusicListService implements IMusicListService{

	@Autowired
	IMusicListRepository listRepo;
	@Override
	public MusicList save(MusicList musicList) {
		// TODO Auto-generated method stub
		return listRepo.save(musicList);
	}

	@Override
	public void delectById(Integer id) {
		// TODO Auto-generated method stub
		listRepo.delete(id);
	}

	@Override
	public MusicList findById(Integer id) {
		// TODO Auto-generated method stub
		return listRepo.findOne(id);
	}

	@Override
	public Page<MusicList> getAll(Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return listRepo.findAll(pageReqeust);
	}

	@Override
	public Page<MusicList> findByUserId(Integer id,Integer page ) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return listRepo.findByUserId(page, pageReqeust);
	}

	@Override
	public Page<MusicList> findByName(String listName, Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return listRepo.findByName(listName, pageReqeust);
	}

}
