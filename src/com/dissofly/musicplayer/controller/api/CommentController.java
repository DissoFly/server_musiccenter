package com.dissofly.musicplayer.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dissofly.musicplayer.entity.ClickLike;
import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IClickLikeService;
import com.dissofly.musicplayer.service.IComnentService;
import com.dissofly.musicplayer.service.IUserService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/comment",produces="text/plain;charset=UTF-8")
public class CommentController {
	
	@Autowired
	IComnentService comnentService;
	@Autowired
	IUserService userService;
	@Autowired
	IClickLikeService likeService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam int songId,
			@RequestParam String text,
			@RequestParam int userId){
		
		Comment comment=new Comment();
		comment.setSongId(songId);
		comment.setText(text);
		List<Comment> comments=comnentService.getAllBySongId(songId);
		int floor=1;
		for(Comment commentItem:comments){
			if(floor<=commentItem.getFloor()){
				floor=commentItem.getFloor()+1;
			}
		}
		comment.setFloor(floor);
		comment.setUserId(userId);
		comment.setUnlook(false);
		comment.setLikeIds("");
		comment.onPrePersist();
		comnentService.save(comment);
		return "SUCCESS_SAVE";
	}
	@RequestMapping(value = "/getBySongId/{song_id}")
	public String getBySongId(@PathVariable Integer song_id){
		return getBySongIdByPage(song_id,0);
	}
	
	@RequestMapping(value = "/getBySongId/{song_id}/{page}")
	public String getBySongIdByPage(@PathVariable Integer song_id,
			@PathVariable Integer page){
		List<Comment> comments=comnentService.findBySongId(song_id, page).getContent();
		for(int i=0;i<comments.size();i++){
			int userId=comments.get(i).getUserId();
			User user=userService.findById(userId);
			String name=user.getAccount();
			comments.get(i).setUserName(name);
			ClickLike clickLike = likeService.findCommentByUserById(userId,
					comments.get(i).getCommentId());
			if(clickLike == null){
				comments.get(i).setUserLike(false);
			}else{
				comments.get(i).setUserLike(true);
			}
		}
		return new Gson().toJson(comments);
	}

}
