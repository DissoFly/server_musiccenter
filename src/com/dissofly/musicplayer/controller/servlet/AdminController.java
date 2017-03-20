package com.dissofly.musicplayer.controller.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dissofly.musicplayer.MD5;
import com.dissofly.musicplayer.domain.Validator;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IUserService;


@Controller
public class AdminController {
	
	public static final String SESSION_USER="user";

	@Autowired
	IUserService userService;

	@RequestMapping(value = {"/login","/main"})
	public String adminLogin(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute(SESSION_USER);
		if(user==null){
			errors.add("你尚未登录！");
			request.setAttribute("errors", errors);
			return "Login";
		}else if(!user.getIsAdmin()){
			errors.add("此用户没有管理员权限！");
			request.setAttribute("errors", errors);
			return "Login";
		}else
			return "Main";
	}

	@RequestMapping(value = "/logining")
	public String logining(@RequestParam String account,
			@RequestParam String password, HttpServletRequest request) {
		
		List<String> errors = new ArrayList<String>();
		if (account.equals(""))
			errors.add("请输入用户名");
		if (password.equals(""))
			errors.add("请输入密码");
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			return "Login";
		}

		User user = userService.findByAccount(account);
		if (user == null)
			errors.add("不存在此用户名");
		else if (!user.getPasswordHsah().equals(MD5.getMD5(password))) {
			errors.add("密码错误");
		} else if (!user.getIsAdmin())
			errors.add("此用户没有管理员权限");
		if (errors.isEmpty()){
			request.getSession().setAttribute(SESSION_USER, user);
			return "Main";
		}else {
			request.setAttribute("errors", errors);
			return "Login";
		}
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null){
			errors.add("你尚未登录！");
			request.setAttribute("errors", errors);
		}else {
			errors.add("你已成功退出登录！");
			request.setAttribute("errors", errors);
			session.invalidate();
		}
		return "Login";
	}

}
