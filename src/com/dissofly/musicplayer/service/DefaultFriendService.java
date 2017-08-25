package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dissofly.musicplayer.entity.Friend;
import com.dissofly.musicplayer.repository.IClickLikeRepository;
import com.dissofly.musicplayer.repository.IFriendRepository;
@Component
@Service
@Transactional
public class DefaultFriendService implements IFriendService{

	@Autowired
	IFriendRepository friendRepo;
	
	
	@Override
	public Friend save(Friend friend) {
		// TODO Auto-generated method stub
		return friendRepo.save(friend);
	}

	@Override
	public List<Friend> getByAllUserId(Integer userId) {
		// TODO Auto-generated method stub
		return friendRepo.getByAllUserId(userId);
	}

	@Override
	public List<Friend> getByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return friendRepo.getByUserId(userId);
	}
	
	@Override
	public Page<Friend> getByUserId(Integer userId, Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return friendRepo.getByUserId(userId,pageReqeust);
	}
	
	@Override
	public List<Friend> getByFriendId(Integer friendsId) {
		// TODO Auto-generated method stub
		return friendRepo.getByFriendId(friendsId);
	}
	
	@Override
	public Page<Friend> getByFriendId(Integer friendsId, Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return friendRepo.getByFriendId(friendsId,pageReqeust);
	}


	@Override
	public Friend getByUserIdByFriendId(Integer userId, Integer friendsId) {
		// TODO Auto-generated method stub
		return friendRepo.getByUserIdByFriendId(userId,friendsId);
	}

	@Override
	public List<Friend> getByBlacklist(Integer userId) {
		// TODO Auto-generated method stub
		return friendRepo.getByBlacklist(userId);
	}

	@Override
	public void delect(Integer id) {
		friendRepo.delete(id);
		
	}




}
