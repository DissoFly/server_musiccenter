package com.dissofly.musicplayer.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dissofly.musicplayer.entity.Inbox;
import com.dissofly.musicplayer.entity.InboxList;
import com.dissofly.musicplayer.service.IInboxService;
import com.dissofly.musicplayer.service.ILogsService;
import com.dissofly.musicplayer.service.IUserService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/api/inbox", produces = "text/plain;charset=UTF-8")
public class InboxController {

	@Autowired
	IUserService userService;
	@Autowired
	IInboxService inboxService;
	@Autowired
	ILogsService logsService;
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam int userId, @RequestParam int friendId,
			@RequestParam String text) {
		// 验证黑名单
		Inbox inbox = new Inbox();
		inbox.setUserId(userId);
		inbox.setGeterId(friendId);
		inbox.setText(text);
		inbox.onPrePersist();
		inboxService.save(inbox);

		List<InboxList> inboxLists = inboxService.findTwoId(userId, friendId);
		InboxList userInboxList = new InboxList();
		InboxList friendInboxList = new InboxList();
		if (inboxLists == null || inboxLists.size() == 0) {
			userInboxList.setNewOne(userId, friendId, text, 0);
			inboxService.save(userInboxList);

			friendInboxList.setNewOne(friendId, userId, text, 1);
			inboxService.save(friendInboxList);
		} else if (inboxLists.size() == 1) {
			if (inboxLists.get(0).getUserId() == userId) {
				userInboxList = inboxLists.get(0);
				userInboxList.update(text);
				inboxService.save(userInboxList);

				friendInboxList.setNewOne(friendId, userId, text, 1);
				inboxService.save(friendInboxList);
			} else {
				friendInboxList = inboxLists.get(0);
				friendInboxList.update(text);
				inboxService.save(friendInboxList);

				userInboxList.setNewOne(userId, friendId, text, 0);
				inboxService.save(userInboxList);
			}
		} else if (inboxLists.size() == 2) {
			for (InboxList inboxList : inboxLists) {
				inboxList.update(text);
				if (inboxList.getUserId() == userId) {
					inboxList.setUnReadNumber(0);
				}
				inboxService.save(inboxList);
			}
		} else {
			return "WRONG";
		}
		logsService.save(userId, "Inbox", "用户 "+userId+" 给好友用户 "+friendId +"发送信息："+text);
		return "SUCCESS";
	}

	@RequestMapping(value = "/getInbox", method = RequestMethod.POST)
	public String getInbox(@RequestParam int userId, @RequestParam int friendId) {

		return getInbox(userId, friendId, 0);
	}

	@RequestMapping(value = "/getInbox/{page}", method = RequestMethod.POST)
	public String getInbox(@RequestParam int userId,
			@RequestParam int friendId, @PathVariable Integer page) {
		InboxList inboxList = inboxService.findOneByTwoId(userId, friendId);
		if (inboxList != null) {
			inboxList.setUnReadNumber(0);
			inboxService.save(inboxList);
		}
		return new Gson().toJson(inboxService.findByTwoId(userId, friendId,
				page).getContent());
	}

	@RequestMapping(value = "/getInboxList", method = RequestMethod.POST)
	public String getInboxList(@RequestParam int userId) {

		return getInboxList(userId, 0);
	}

	@RequestMapping(value = "/getInboxList/{page}", method = RequestMethod.POST)
	public String getInboxList(@RequestParam int userId,
			@PathVariable Integer page) {
		List<InboxList> inboxLists=inboxService.findByUserId(userId, page)
				.getContent();
		if (inboxLists.size()==0) {
			return "GET_ALL";
		}
		for (int i = 0; i < inboxLists.size(); i++) {
			int friendId=inboxLists.get(i).getFriendId();
			inboxLists.get(i).setFriendName(userService.findById(friendId).getAccount());
		}
		return new Gson().toJson(inboxLists);
	}

}
