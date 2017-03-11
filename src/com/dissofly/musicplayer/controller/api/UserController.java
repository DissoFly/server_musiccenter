package com.dissofly.musicplayer.controller.api;

import java.io.File;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dissofly.musicplayer.entity.AutoLoginSign;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IAutoLoginSignService;
import com.dissofly.musicplayer.service.IUserService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class UserController {

	final int MAX_LOGIN_DAY = 30;
	private static Gson gson = new Gson();

	@Autowired
	IUserService userService;
	@Autowired
	IAutoLoginSignService autoLSService;

	@RequestMapping(value = "/")
	public String text() {
		return "connect success!";
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String text2(@RequestParam String testString,
			HttpServletRequest request) {
		System.out.println(testString);
		return "connect success!" + testString;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestParam String account,
			@RequestParam String passwordHash, @RequestParam String email,
			@RequestParam long phoneNumber,MultipartFile avatar,
			HttpServletRequest request) {
		User user = new User(account, passwordHash, email, phoneNumber, false);
		if(avatar!=null){
			try{
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
				FileUtils.copyInputStreamToFile(avatar.getInputStream(), new File(realPath,account+".png"));
				user.setAvatar("upload/"+account+".png");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		userService.save(user);
		return "SUCCESS_IN_REGISTER";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String account,
			@RequestParam String passwordHash, @RequestParam String meid,
			
			HttpServletRequest request) {
		User user = userService.findByAccount(account);
		if (user == null)
			return "FAIL_WITH_NO_ACCOUNT";
		else if (!user.getPasswordHsah().equals(passwordHash))
			return "FAIL_WITH_WRONG_PASSWORD";
		else {
			AutoLoginSign loginSign = autoLSService.findByUserId(user
					.getUserId());
			if (loginSign == null) {
				loginSign = new AutoLoginSign();
				loginSign.setSign(meid);
				loginSign.setUserId(user.getUserId());
				loginSign.onPrePersist();
				autoLSService.save(loginSign);
			} else {
				loginSign.setSign(meid);
				loginSign.onPrePersist();
				autoLSService.save(loginSign);
			}
			return "SUCCESS_IN_LOGIN";
		}
	}

	@RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
	public String autoLogin(@RequestParam String account,
			@RequestParam String meid) {
		User user = userService.findByAccount(account);
		AutoLoginSign loginSign = autoLSService.findByUserId(user.getUserId());
		if (loginSign == null)
			return "FAIL_WITH_NO_SIGN";
		if (meid.equals(loginSign.getSign())) {
			long loginDays = (new Date().getDate() - loginSign.getCreateDate()
					.getDate()) / (1000 * 60 * 60 * 24);
			if (loginDays > MAX_LOGIN_DAY) {
				// 删除代码
				return "FAIL_WITH_LOGIN_OUTTIME";
			} else {
				loginSign.onPreUpdate();
				autoLSService.save(loginSign);
				return gson.toJson(user);
			}
		} else
			return "FAIL_WITH_DIFFERENT_SIGN";
	}

}
