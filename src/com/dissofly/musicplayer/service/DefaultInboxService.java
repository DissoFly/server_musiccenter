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

import com.dissofly.musicplayer.entity.Inbox;
import com.dissofly.musicplayer.entity.InboxList;
import com.dissofly.musicplayer.repository.IInboxListRepository;
import com.dissofly.musicplayer.repository.IInboxRepository;
@Component
@Service
@Transactional
public class DefaultInboxService implements IInboxService {
	
	@Autowired
	IInboxRepository inboxRepo;
	@Autowired
	IInboxListRepository inboxListRepo;

	@Override
	public Inbox save(Inbox inbox) {
		return inboxRepo.save(inbox);
	}

	@Override
	public InboxList save(InboxList inboxList) {
		return inboxListRepo.save(inboxList);
	}

	@Override
	public void delectByInboxId(Integer inboxId) {
		inboxRepo.delete(inboxId);

	}

	@Override
	public void delectByInboxListId(Integer inboxListId ) {
		inboxListRepo.delete(inboxListId);

	}

	@Override
	public Page<Inbox> findByTwoId(Integer userId, Integer geterId, Integer page) {
		Sort sort = new Sort(Direction.ASC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return inboxRepo.findByTwoId(userId,geterId,pageReqeust);
	}

	@Override
	public Page<InboxList> findByUserId(Integer userId, Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return inboxListRepo.findByUserId(userId,pageReqeust);
	}

	@Override
	public List<InboxList> findTwoId(Integer userId, Integer friendId) {
		// TODO Auto-generated method stub
		return inboxListRepo.findTwoId(userId, friendId);
	}

	@Override
	public InboxList findOneByTwoId(Integer userId, Integer friendId) {
		// TODO Auto-generated method stub
		return inboxListRepo.findOneByTwoId(userId, friendId);
	}

}
