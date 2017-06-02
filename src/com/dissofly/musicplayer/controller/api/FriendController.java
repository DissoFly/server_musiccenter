package com.dissofly.musicplayer.controller.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dissofly.musicplayer.entity.ClickLike;
import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.Friend;
import com.dissofly.musicplayer.entity.FriendRead;
import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.service.IClickLikeService;
import com.dissofly.musicplayer.service.IComnentService;
import com.dissofly.musicplayer.service.IFriendService;
import com.dissofly.musicplayer.service.ILogsService;
import com.dissofly.musicplayer.service.IPublicSongService;
import com.dissofly.musicplayer.service.IUserService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/api/friend", produces = "text/plain;charset=UTF-8")
public class FriendController {

	@Autowired
	IFriendService friendService;
	@Autowired
	IUserService userService;
	@Autowired
	IComnentService comnentService;
	@Autowired
	IClickLikeService likeService;
	@Autowired
	IPublicSongService publicSongService;
	@Autowired
	ILogsService logsService;

	final int PAGE_SIZE = 10;

	@RequestMapping(value = "/addFriend", method = RequestMethod.POST)
	public String addFriend(@RequestParam int userId, @RequestParam int friendId) {
		String isAddMessage = isAdd(userId, friendId);
		switch (isAddMessage) {
		case "FALSE_ISADD":
			Friend friend = new Friend();
			friend.setBlacklist(false);
			friend.onPrePersist();
			friend.setFollow(true);
			friend.setFriendUserId(friendId);
			friend.setUserId(userId);
			friendService.save(friend);
			logsService.save(userId, "Friend", "用户 "+userId+" 添加好友用户 "+friendId);
			return "SUCCESS_ADD";
		case "TRUE_ISADD":
			Friend friend2 = friendService.getByUserIdByFriendId(userId,
					friendId);
			friendService.delect(friend2.getFriendId());
			logsService.save(userId, "Friend", "用户 "+userId+" 取消添加好友用户 "+friendId);
			return "SUCCESS_DELECT";
		default:
			return isAddMessage;
		}

	}

	@RequestMapping(value = "/getFriendZone", method = RequestMethod.POST)
	public String getFriendZone(@RequestParam int userId,
			@RequestParam int friendId) {
		return getFriendZone(userId, friendId, 0);

	}

	@RequestMapping(value = "/getAddList", method = RequestMethod.POST)
	public String getAddList(@RequestParam int userId) {
		return getAddList(userId, 0);
	}

	@RequestMapping(value = "/getAddList/{page}", method = RequestMethod.POST)
	public String getAddList(@RequestParam int userId,
			@PathVariable Integer page) {

		List<Friend> friends = friendService.getByUserId(userId, page)
				.getContent();
		if (friends.size()==0) {
			return "GET_ALL";
		}
		for (int i = 0; i < friends.size(); i++) {
			int id=friends.get(i).getFriendUserId();
			friends.get(i).setFriendName(userService.findById(id).getAccount());
		}
		return new Gson().toJson(friends);
	}

	@RequestMapping(value = "/getBeAddList", method = RequestMethod.POST)
	public String getBeAddList(@RequestParam int userId) {
		return getBeAddList(userId, 0);
	}

	@RequestMapping(value = "/getBeAddList/{page}", method = RequestMethod.POST)
	public String getBeAddList(@RequestParam int userId,
			@PathVariable Integer page) {
		List<Friend> friends = friendService.getByFriendId(userId, page)
				.getContent();
		if (friends.size()==0) {
			return "GET_ALL";
		}
		for (int i = 0; i < friends.size(); i++) {
			int id=friends.get(i).getUserId();
			friends.get(i).setFriendName(userService.findById(id).getAccount());
		}
		return new Gson().toJson(friends);
	}
	

	@RequestMapping(value = "/getFriendZone/{page}", method = RequestMethod.POST)
	public String getFriendZone(@RequestParam int userId,
			@RequestParam int friendId, @PathVariable Integer page) {
		// 未登录时验证
		if (userId >= 0) {
			List<Friend> friendBlacklist = friendService
					.getByBlacklist(friendId);
			for (Friend friend : friendBlacklist) {
				if (friend.getUserId() == userId) {
					return "IN_BLACKLIST";
				}
			}
		}
		String userName = userService.findById(friendId).getAccount();
		List<Comment> comments = comnentService.findByUserId(friendId);
		List<ClickLike> clickLikes = likeService.findByUserId(friendId);
		if ((comments.size() + clickLikes.size()) < page * PAGE_SIZE) {
			return "GET_ALL";
		}
		List<FriendRead> friendReads = new ArrayList<>();
		for (Comment comment : comments) {
			FriendRead friendRead = new FriendRead();
			friendRead.setComment(comment.getUserId(), comment.getSongId(),
					comment.getText(), comment.getCreateDate(),
					comment.getEditDate());
			friendReads.add(friendRead);
		}
		for (ClickLike clickLike : clickLikes) {
			FriendRead friendRead = new FriendRead();
			if (clickLike.isSong()) {
				friendRead.setMusicLike(clickLike.getUserId(),
						clickLike.getBeLikeId(), clickLike.getCreateDate(),
						clickLike.getEditDate());
			} else if (clickLike.isComment()) {
				friendRead.setCommentLike(clickLike.getUserId(),
						clickLike.getBeLikeId(), clickLike.getBeLikeId() + "",
						clickLike.getCreateDate(), clickLike.getEditDate());
			} else {
				continue;
			}
			friendReads.add(friendRead);
		}
		Collections.sort(friendReads);
		List<FriendRead> friendReadsWithPage = new ArrayList<>();
		for (int i = PAGE_SIZE * (page); i < PAGE_SIZE * (page + 1)
				&& i < friendReads.size(); i++) {
			FriendRead friendRead = friendReads.get(i);
			PublicSong publicSong = publicSongService.getBySongId(friendRead
					.getSongId());
			if (friendRead.isComment()) {
				friendRead.setText("给 " + publicSong.getSongName() + " 评论:\n"
						+ friendRead.getText());
			} else if (friendRead.isCommentLike()) {
				int commentId = Integer.parseInt(friendRead.getText());
				String text = comnentService.findById(commentId).getText();
				friendRead.setText("给 " + publicSong.getSongName() + " 中的评论点赞:\n"
						+ text);
			} else if (friendRead.isMusicLike()) {
				friendRead.setText("给 " + publicSong.getSongName() + " 这首歌点赞。");
			}
			friendRead.setUserName(userName);
			friendReadsWithPage.add(friendRead);
		}
		return new Gson().toJson(friendReadsWithPage);
	}
	
	@RequestMapping(value = "/getFriendNews", method = RequestMethod.POST)
	public String getFriendNews(@RequestParam int userId){
		return getFriendNews(userId,0);
	}
	
	
	
	@RequestMapping(value = "/getFriendNews/{page}", method = RequestMethod.POST)
	public String getFriendNews(@RequestParam int userId,@PathVariable Integer page){
		if (userId < 0) {
			return "PLEASE_LOGIN";
		}
		List<Friend>friends=friendService.getByUserId(userId);
		Friend user=new Friend();
		user.setFriendUserId(userId);
		friends.add(user);
		List<Comment> comments = new ArrayList<>();
		List<ClickLike> clickLikes = new ArrayList<>();
		for(Friend friend:friends){
			String userName = userService.findById(friend.getFriendUserId()).getAccount();
			if(friend.getFriendUserId()==userId){
				userName=userName+"（自己）";
			}
			List<Comment>comments2=comnentService.findByUserId(friend.getFriendUserId());
			for(int i=0;i<comments2.size();i++){
				comments2.get(i).setUserName(userName);
			}
			comments.addAll(comments2) ;
			List<ClickLike>clickLikes2=likeService.findByUserId(friend.getFriendUserId());
			for(int i=0;i<clickLikes2.size();i++){
				clickLikes2.get(i).setUserName(userName);
			}
			clickLikes.addAll(clickLikes2);
		}
		if ((comments.size() + clickLikes.size()) < (page * PAGE_SIZE)) {
			return "GET_ALL";
		}
		List<FriendRead> friendReads = new ArrayList<>();
		for (Comment comment : comments) {
			FriendRead friendRead = new FriendRead();
			friendRead.setComment(comment.getUserName(),comment.getUserId(), comment.getSongId(),
					comment.getText(), comment.getCreateDate(),
					comment.getEditDate());
			friendReads.add(friendRead);
		}

		for (ClickLike clickLike : clickLikes) {
			FriendRead friendRead = new FriendRead();
			if (clickLike.isSong()) {
				friendRead.setMusicLike(clickLike.getUserName(),clickLike.getUserId(),
						clickLike.getBeLikeId(), clickLike.getCreateDate(),
						clickLike.getEditDate());
			} else if (clickLike.isComment()) {
				friendRead.setCommentLike(clickLike.getUserName(),clickLike.getUserId(),
						clickLike.getBeLikeId(), clickLike.getBeLikeId() + "",
						clickLike.getCreateDate(), clickLike.getEditDate());
			} else {
				continue;
			}
			friendReads.add(friendRead);
		}
		Collections.sort(friendReads);
		List<FriendRead> friendReadsWithPage = new ArrayList<>();
		for (int i = PAGE_SIZE * (page); i < PAGE_SIZE * (page + 1)
				&& i < friendReads.size(); i++) {
			FriendRead friendRead = friendReads.get(i);
			PublicSong publicSong = publicSongService.getBySongId(friendRead
					.getSongId());
			System.out.println(new Gson().toJson(friendRead));
			if (friendRead.isComment()) {
				friendRead.setText("给 " + publicSong.getSongName() + " 评论:"
						+ friendRead.getText());
			} else if (friendRead.isCommentLike()) {
				int commentId = Integer.parseInt(friendRead.getText());
				String text = comnentService.findById(commentId).getText();
				friendRead.setText("给 " + publicSong.getSongName() + " 中的评论点赞:"
						+ text);
			} else if (friendRead.isMusicLike()) {
				friendRead.setText("给 " + publicSong.getSongName() + " 这首歌点赞。");
			}
			friendReadsWithPage.add(friendRead);
		}
		return new Gson().toJson(friendReadsWithPage);
	}
	
	
	

	@RequestMapping(value = "/isAdd/", method = RequestMethod.POST)
	public String isAdd(@RequestParam int userId, @RequestParam int friendId) {
		// 未登录时验证
		if (userId >= 0) {
			Friend friend = friendService.getByUserIdByFriendId(userId,
					friendId);
			if (friend == null) {
				return "FALSE_ISADD";
			} else {
				if (friend.isFollow()) {
					return "TRUE_ISADD";
				} else if (friend.isBlacklist()) {
					return "IN_BLACKLIST";
				} else {
					return "WRONG";
				}
			}
		} else {
			return "PLEASE_LOGIN";
		}
	}

}
