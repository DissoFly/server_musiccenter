package com.dissofly.musicplayer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.Friend;

public interface IFriendRepository extends
PagingAndSortingRepository<Friend, Integer>{

	@Query("from Friend friend where friend.userId= ?1 or friend.friendUserId=?1 and friend.isFollow=true")
	List<Friend> getByAllUserId(Integer userId);

	@Query("from Friend friend where friend.userId= ?1 and friend.isFollow=true")
	List<Friend> getByUserId(Integer userId);
	
	@Query("from Friend friend where friend.userId= ?1 and friend.isFollow=true")
	Page<Friend> getByUserId(Integer userId, Pageable page);

	@Query("from Friend friend where friend.friendUserId= ?1 and friend.isFollow=true")
	List<Friend> getByFriendId(Integer friendsId);
	
	@Query("from Friend friend where friend.friendUserId= ?1 and friend.isFollow=true")
	Page<Friend> getByFriendId(Integer friendsId, Pageable page);

	@Query("from Friend friend where friend.userId= ?1 and friend.friendUserId=?2 and friend.isFollow=true")
	Friend getByUserIdByFriendId(Integer userId, Integer friendsId);

	@Query("from Friend friend where friend.userId= ?1 and friend.isBlacklist=true")
	List<Friend> getByBlacklist(Integer userId);

	
	
	
	

}
