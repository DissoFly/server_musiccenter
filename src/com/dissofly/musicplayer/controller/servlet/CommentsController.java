package com.dissofly.musicplayer.controller.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IComnentService;
import com.google.gson.Gson;

@Controller
public class CommentsController {
	public static final String SESSION_USER = "user";

	@Autowired
	IComnentService comnentService;


	
	@RequestMapping(value = "allComment")
	public String allMusic(HttpServletRequest request) {
		return allMusic(0, request);
	}

	@RequestMapping(value = "allComment/{page}")
	public String allMusic(@PathVariable Integer page,
			HttpServletRequest request) {
		System.out.println(new Gson().toJson(comnentService.findAll(page).getContent()));
		request.setAttribute("comments",comnentService.findAll(page).getContent());
		request.setAttribute("commentCount",comnentService.getAllCount());
		request.setAttribute("page",page);
		request.setAttribute("allPage",(comnentService.getAllCount()-1)/10);
		return "AllComment";
	}
	
	@RequestMapping(value = "comment")
	public String comment() {
		return "Comment";
	}

	@RequestMapping(value = "comment_unlook")
	public String commentUnlook(@RequestParam String ids,@RequestParam String reason,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null)
			return "Login";
		List<String> messages = new ArrayList<String>();
		if(reason.equals("")){
			messages.add("错误：请填写屏蔽理由！");
		}
		String s[] = ids.split(";");
		List<Integer> ints = new ArrayList<>();
		List<Integer> errorInt = new ArrayList<>();
		try {
			for (int i = 0; i < s.length; i++) {
				ints.add(Integer.parseInt(s[i]));
				Comment comment = comnentService.findById(ints
						.get(i));
				if (comment == null) {
					errorInt.add(Integer.parseInt(s[i]));
				}
			}
		} catch (NumberFormatException e) {
			messages.add("错误：请正确填写编号！");
		} catch (Exception e) {
			e.printStackTrace();
			messages.add("错误：未知错误！");
		}
		if (errorInt.size() > 0) {
			for (Integer integer : errorInt)
				messages.add("错误：评论编号" + integer + "：不存在！");
		} else {
			if (s.length <= 0) {
				messages.add("错误：不存在评论！");
			} 
		}
		
		if (messages.size() > 0) {
			request.setAttribute("messages", messages);
		} else {
			for(Integer i:ints){
				Comment comment = comnentService.findById(i);
				comment.onPreUpdate();
				comment.setUnlook(true);
				comment.setUnlookReason(reason);
				comnentService.save(comment);
			}
			messages.add("操作成功！");
		}
		request.setAttribute("messages", messages);
		return "Comment";
	}

}
