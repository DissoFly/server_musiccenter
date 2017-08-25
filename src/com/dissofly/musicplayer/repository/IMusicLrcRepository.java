package com.dissofly.musicplayer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.InboxList;
import com.dissofly.musicplayer.entity.MusicLrc;
import com.dissofly.musicplayer.entity.PublicSong;

public interface IMusicLrcRepository extends
PagingAndSortingRepository<MusicLrc, Integer> {
	
	@Query("from MusicLrc musicLrc where musicLrc.songId= ?1")
	MusicLrc findBySongId(Integer songId);

}
