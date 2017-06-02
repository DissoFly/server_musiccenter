package com.dissofly.musicplayer.controller.servlet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dissofly.musicplayer.MD5;
import com.dissofly.musicplayer.domain.Validator;
import com.dissofly.musicplayer.entity.MusicLrc;
import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IUserService;
import com.google.gson.Gson;

@Controller
public class AdminController {

	public static final String SESSION_USER = "user";

	@Autowired
	IUserService userService;
	final static String personalPath = "F:/musicCenter/personal/";

	@RequestMapping(value = { "/login", "/main" })
	public String adminLogin(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			errors.add("你尚未登录！");
			request.setAttribute("errors", errors);
			return "Login";
		} else if (!user.getIsAdmin()) {
			errors.add("此用户没有管理员权限！");
			request.setAttribute("errors", errors);
			return "Login";
		} else
			return "Main";
	}
	
	@RequestMapping(value = "allUser")
	public String allUser(HttpServletRequest request) {
		return allUser(0, request);
	}

	@RequestMapping(value = "allUser/{page}")
	public String allUser(@PathVariable Integer page,
			HttpServletRequest request) {
		request.setAttribute("users",userService.findAll(page).getContent());
		request.setAttribute("userCount",userService.getAllCount());
		request.setAttribute("page",page);
		request.setAttribute("allPage",(userService.getAllCount()-1)/10);
		return "AllUser";
	}
	
	

	@RequestMapping(value = "/register")
	public String register() {
		return "Register";
	}

	@RequestMapping(value = "/register_admin", method = RequestMethod.POST)
	public String register(@RequestParam String account,
			@RequestParam String password, @RequestParam String email,
			@RequestParam String isAdmin, @RequestParam long phoneNumber,
			MultipartFile avatar, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute(SESSION_USER);
		if (u == null)
			return "Login";
		List<String> messages = new ArrayList<String>();

		if (account.equals("")) {
			messages.add("请输入用户名");
		} else if (userService.findByAccount(account) != null) {
			messages.add("已存在用户名");
		}
		if (password.equals("")) {
			messages.add("请输入密码");
		}
		if (email.equals("")) {
			messages.add("请输入email");
		}
		if (phoneNumber == 0) {
			messages.add("请输入手机号码");
		}
		if (messages.size() > 0) {
			request.setAttribute("messages", messages);
			return "Register";
		}

		User user;
		String passwordHash = MD5.getMD5(password);
		if (isAdmin.equals("true"))
			user = new User(account, passwordHash, email, phoneNumber, true);
		else {
			user = new User(account, passwordHash, email, phoneNumber, false);
		}
		user = userService.save(user);
		if (avatar != null) {
			try {
				String path = personalPath + "avatar/";
				FileUtils.copyInputStreamToFile(avatar.getInputStream(),
						new File(path, user.getUserId() + ".png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		userService.save(user);
		messages.add("注册成功");
		request.setAttribute("messages", messages);
		return "Register";
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
		if (errors.isEmpty()) {
			request.getSession().setAttribute(SESSION_USER, user);
			return "Main";
		} else {
			request.setAttribute("errors", errors);
			return "Login";
		}
	}

	@RequestMapping(value = "/freezeUser")
	public String freezeUser() {
		return "FreezeUser";
	}

	@RequestMapping(value = "/freeze_user", method = RequestMethod.POST)
	public String freezeUser(@RequestParam String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute(SESSION_USER);
		if (u == null)
			return "Login";
		List<String> messages = new ArrayList<String>();
		int userId = -1;
		try {
			userId = Integer.parseInt(id);
			User user = userService.findById(userId);
			if (user == null) {
				messages.add("错误：用户编号" + userId + "：不存在！");
			}
		} catch (NumberFormatException e) {
			messages.add("错误：请正确填写用户编号！");
		}

		if (messages.size() > 0) {
			request.setAttribute("messages", messages);
		} else {
			User user = userService.findById(userId);
			if (!user.getIsFreeze()) {
				user.setIsFreeze(true);
				userService.save(user);
				messages.add("冻结成功！");
				request.setAttribute("messages", messages);
			}else{
				messages.add("已冻结，无需继续冻结！");
				request.setAttribute("messages", messages);
			}

		}
		return "FreezeUser";
	}

	@RequestMapping(value = "/unfreezeUser")
	public String unfreezeUser() {
		return "UnfreezeUser";
	}

	@RequestMapping(value = "/unfreeze_user", method = RequestMethod.POST)
	public String unfreezeUser(@RequestParam String id,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute(SESSION_USER);
		if (u == null)
			return "Login";
		List<String> messages = new ArrayList<String>();
		int userId = -1;
		try {
			userId = Integer.parseInt(id);
			User user = userService.findById(userId);
			if (user == null) {
				messages.add("错误：用户编号" + userId + "：不存在！");
			}
		} catch (NumberFormatException e) {
			messages.add("错误：请正确填写用户编号！");
		}

		if (messages.size() > 0) {
			request.setAttribute("messages", messages);
		} else {
			User user = userService.findById(userId);
			if (user.getIsFreeze()) {
				user.setIsFreeze(false);
				userService.save(user);
				messages.add("解冻成功！");
				request.setAttribute("messages", messages);
			}else{
				messages.add("未冻结，无需解冻！");
				request.setAttribute("messages", messages);
			}
		}
		return "UnfreezeUser";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			errors.add("你尚未登录！");
			request.setAttribute("errors", errors);
		} else {
			errors.add("你已成功退出登录！");
			request.setAttribute("errors", errors);
			session.invalidate();
		}
		return "Login";
	}

}
