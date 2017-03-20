package com.dissofly.musicplayer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.AutoLoginSign;
import com.dissofly.musicplayer.entity.MusicList;



public interface IMusicListRepository extends PagingAndSortingRepository<MusicList, Integer>{

	
	@Query("from MusicList musiclist where musiclist.userId= ?1 ")
	Page<MusicList>findByUserId(Integer userId,Pageable page);
	
	@Query("from MusicList musiclist where musiclist.listName like %?1% ")
	Page<MusicList>findByName(String listName,Pageable page);
}
