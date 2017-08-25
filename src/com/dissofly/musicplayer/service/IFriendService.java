package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dissofly.musicplayer.entity.Friend;

public interface IFriendService {
	Friend save(Friend friend);
	List<Friend> getByAllUserId(Integer userId);
	List<Friend> getByUserId(Integer userId);
	Page<Friend> getByUserId(Integer userId,Integer page);
	List<Friend> getByFriendId(Integer friendsId);
	Page<Friend> getByFriendId(Integer friendsId,Integer page);
	Friend getByUserIdByFriendId(Integer userId,Integer friendsId);
	List<Friend> getByBlacklist(Integer userId);
	void delect(Integer id);
}
