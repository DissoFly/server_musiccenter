package com.dissofly.musicplayer.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dissofly.musicplayer.entity.ClickLike;
import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.service.IClickLikeService;
import com.dissofly.musicplayer.service.IComnentService;

@RestController
@RequestMapping(value = "/api/like", produces = "text/plain;charset=UTF-8")
public class LikeController {

	@Autowired
	IClickLikeService likeService;
	@Autowired
	IComnentService comnentService;

	@RequestMapping(value = "/setCommentLike", method = RequestMethod.POST)
	public String setCommnetLike(
			@RequestParam int userId,
			@RequestParam int commentId) {
		Comment comment = comnentService.findById(commentId);
		ClickLike clickLike = likeService.findCommentByUserById(userId,
				commentId);

		String likes = comment.getLikeIds();
		String stringReturn;
		if (clickLike == null) {
			clickLike= new ClickLike();
			clickLike.setUserId(userId);
			clickLike.setComment(true);
			clickLike.setSong(false);
			clickLike.setBeLikeId(commentId);
			clickLike = likeService.save(clickLike);
			likes = likes + ";" + clickLike.getLikeId();
			stringReturn="SUCCESS_ADD";

		} else {
			likeService.delectById(clickLike.getLikeId());
			likes = likes.replace(";" + clickLike.getLikeId(), "");
			stringReturn="SUCCESS_DELECT";
		}
		comment.setLikeIds(likes);
		comnentService.save(comment);
		return stringReturn;
	}
	
	@RequestMapping(value = "/setMusicLike", method = RequestMethod.POST)
	public String setMusicLike(
			@RequestParam int userId,
			@RequestParam int musicId) {
		ClickLike clickLike = likeService.findMusicByUserById(userId, musicId);

		String stringReturn;
		if (clickLike == null) {
			clickLike = new ClickLike();
			clickLike.setUserId(userId);
			clickLike.setComment(false);
			clickLike.setSong(true);
			clickLike.setBeLikeId(musicId);
			clickLike = likeService.save(clickLike);
			stringReturn="SUCCESS_ADD";

		} else {
			likeService.delectById(clickLike.getLikeId());
			stringReturn="SUCCESS_DELECT";
		}
		return stringReturn;
	}
	
	@RequestMapping(value = "/getMusicLikeNumber", method = RequestMethod.POST)
	public String getMusicLikeNumber(
			@RequestParam int userId,
			@RequestParam int musicId) {
		List<ClickLike> clickLikes = likeService.findMusicById(musicId);
		String haveUser="FALSE";
		for(ClickLike clickLike:clickLikes){
			if(clickLike.getUserId()==userId){
				haveUser="TRUE";
				break;
			}
		}
		return clickLikes.size()+haveUser;
	}

}
