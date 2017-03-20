package com.dissofly.musicplayer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.MusicList;

public interface ICommentRepository extends
		PagingAndSortingRepository<Comment, Integer> {

	@Query("from Comment comment where comment.songId= ?1 ")
	Page<Comment>findBySongId(Integer songId,Pageable page);
	
	@Query("from Comment comment where comment.songId= ?1 ")
	List<Comment>findAllBySongId(Integer songId);
	
	@Query("from Comment comment where comment.userId= ?1 ")
	Page<Comment>findByUserId(Integer userId,Pageable page);
	
	
}
