package com.dissofly.musicplayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dissofly.musicplayer.entity.PublicSong;

@Repository
public interface IPublicSongRepository extends
		PagingAndSortingRepository<PublicSong, Integer> {
	
	@Query("from PublicSong where songName like %?1% ")
	List<PublicSong> getByName(String songName);
	
	@Query("from PublicSong where artist like %?1% ")
	List<PublicSong> getByArtist(String artist);
	
	@Query("from PublicSong where album like %?1% ")
	List<PublicSong> getByAlbum(String album);

}
