package com.dissofly.musicplayer.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dissofly.musicplayer.MD5;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IUserService;

public class Validator {

	@Autowired
	static
	IUserService userService;

	public static List<String> userValidater(String account, String password) {
		List<String> errors = new ArrayList<String>();
		if (account.equals(""))
			errors.add("请输入用户名");
		if (password.equals(""))
			errors.add("请输入密码");
		if (!errors.isEmpty())
			return errors;
		
		User user = userService.findByAccount(account);
		if (user == null)
			errors.add("不存在此用户名");
		else if (user.getPasswordHsah().equals(MD5.getMD5(password))) {
			errors.add("密码错误");
		}else if(!user.getIsAdmin())
			errors.add("此用户没有管理员权限");
		return errors;

	}
}
