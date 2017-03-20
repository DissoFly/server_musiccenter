package com.dissofly.musicplayer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.dissofly.musicplayer.entity.ClickLike;
import com.dissofly.musicplayer.entity.Comment;

public interface IClickLikeRepository extends
		PagingAndSortingRepository<ClickLike, Integer> {
	@Query("from ClickLike clickLike where clickLike.userId= ?1 ")
	Page<ClickLike> findByUserId(Integer userId, Pageable page);
	
	@Query("from ClickLike clickLike where clickLike.userId= ?1 and clickLike.beLikeId= ?2 and clickLike.isComment= true")
	ClickLike findCommentByUserById(Integer userId,Integer beLikeId);

	@Query("from ClickLike clickLike where clickLike.userId= ?1 and clickLike.beLikeId= ?2 and clickLike.isSong= true")
	ClickLike findMusicByUserById(Integer userId, Integer musicId);
	
	@Query("from ClickLike clickLike where clickLike.beLikeId= ?1 and clickLike.isComment= true")
	List<ClickLike> findCommentById(Integer commentId);
	@Query("from ClickLike clickLike where clickLike.beLikeId= ?1 and clickLike.isSong= true")
	List<ClickLike> findMusicById(Integer musicId);

	

}
