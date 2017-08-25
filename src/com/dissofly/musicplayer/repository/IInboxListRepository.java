package com.dissofly.musicplayer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.Inbox;
import com.dissofly.musicplayer.entity.InboxList;

public interface IInboxListRepository extends
		PagingAndSortingRepository<InboxList, Integer> {

	@Query("from InboxList inboxList where inboxList.userId= ?1")
	Page<InboxList> findByUserId(Integer userId, Pageable page);

	@Query("from InboxList inboxList where (inboxList.userId= ?1 and inboxList.friendId=?2) or (inboxList.userId= ?2 and inboxList.friendId=?1)")
	List<InboxList> findTwoId(Integer userId, Integer friendId);

	@Query("from InboxList inboxList where inboxList.userId= ?1 and inboxList.friendId=?2")
	InboxList findOneByTwoId(Integer userId, Integer friendId);

}
