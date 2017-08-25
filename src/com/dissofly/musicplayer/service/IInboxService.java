package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dissofly.musicplayer.entity.Inbox;
import com.dissofly.musicplayer.entity.InboxList;

public interface IInboxService {
	
	Inbox save(Inbox inbox);
	InboxList save(InboxList inboxList);
	void delectByInboxId(Integer inboxId);
	void delectByInboxListId(Integer inboxListId);
	InboxList findOneByTwoId(Integer userId, Integer friendId);
	Page<Inbox>findByTwoId(Integer userId,Integer geterId,Integer page);
	Page<InboxList>findByUserId(Integer userId,Integer page);
	List<InboxList> findTwoId(Integer userId, Integer friendId);

}
