package com.dissofly.musicplayer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.Friend;
import com.dissofly.musicplayer.entity.Inbox;

public interface IInboxRepository extends
PagingAndSortingRepository<Inbox, Integer>{

	@Query("from Inbox inbox where (inbox.userId= ?1 and inbox.geterId= ?2) or (inbox.userId= ?2 and inbox.geterId= ?1)")
	Page<Inbox>findByTwoId(Integer userId,Integer geterId,Pageable page);
}
