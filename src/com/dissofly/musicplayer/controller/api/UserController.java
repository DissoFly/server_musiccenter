package com.dissofly.musicplayer.controller.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dissofly.musicplayer.entity.AutoLoginSign;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IAutoLoginSignService;
import com.dissofly.musicplayer.service.ILogsService;
import com.dissofly.musicplayer.service.IUserService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping(value = "/api", produces = "text/plain;charset=UTF-8")
public class UserController {

	final int MAX_LOGIN_DAY = 30;
	private static Gson gson = new Gson();
	final static String personalPath = "F:/musicCenter/personal/";
	@Autowired
	IUserService userService;
	@Autowired
	IAutoLoginSignService autoLSService;
	@Autowired
	ILogsService logsService;


	@RequestMapping(value = "/getUserName", method = RequestMethod.POST)
	public String text2(@RequestParam int userId,
			HttpServletRequest request) {
		return "NAME:"+userService.findById(userId).getAccount();
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestParam String account,
			@RequestParam String passwordHash, @RequestParam String email,
			@RequestParam long phoneNumber,MultipartFile avatar,
			HttpServletRequest request) {
		if(userService.findByAccount(account)!=null){
			return "FAIL_WITH_SAME_ACCOUNT";
		}
		User user = new User(account, passwordHash, email, phoneNumber, false);
		user=userService.save(user);
		if(avatar!=null){
			try{
				String path = personalPath +"avatar/";
				FileUtils.copyInputStreamToFile(avatar.getInputStream(), new File(path,user.getUserId()+".png"));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		user=userService.save(user);
		logsService.save(user.getUserId(), "user", "用户注册成功");
		return "SUCCESS_IN_REGISTER";
	}
	
	@RequestMapping(value = "/forget_password", method = RequestMethod.POST)
	public String forget_password(@RequestParam String account,
			@RequestParam String passwordHash, 
			@RequestParam long phoneNumber,
			HttpServletRequest request) {
		User user=userService.findByAccount(account);
		if(user==null){
			return "FAIL_WITH_NO_ACCOUNT";
		}
		if(user.getPhoneNumber()!=phoneNumber){
			logsService.save(user.getUserId(), "user", "用户 忘记密码 修改密码失败：使用错误的手机号");
			return "FAIL_WITH_WRONG_PHONENUMBER";
		}
		user.setPasswordHsah(passwordHash);
		user=userService.save(user);
		logsService.save(user.getUserId(), "user", "用户 忘记密码 修改密码成功");
		return "SUCCESS";
	}

	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String account,
			@RequestParam String passwordHash, @RequestParam String meid,
			HttpServletRequest request) {
		User user = userService.findByAccount(account);
		if (user == null)
			return "FAIL_WITH_NO_ACCOUNT";
		else if (user.getIsFreeze())
			return "FAIL_WITH_FREEZE";
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
			logsService.save(user.getUserId(), "user", "用户登录成功");
			return "SUCCESS_IN_LOGIN";
		}
	}
	
	@RequestMapping(value = "/userChange")
	public String userChange(@RequestParam Integer userId,@RequestParam String email,
			@RequestParam long phoneNumber,MultipartFile avatar,
			HttpServletRequest request){
		User user = userService.findById(userId);
		if (user == null)
			return "FAIL_WITH_NO_ACCOUNT";
		if(avatar!=null){
			try{
				String path = personalPath +"avatar/";
				FileUtils.copyInputStreamToFile(avatar.getInputStream(), new File(path,user.getUserId()+".png"));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		userService.save(user);
		return "SUCCESS_IN_USERCHANGE";
	}
	
	
	@RequestMapping(value = "/avatar/{userId}")
	public String avatar2(@PathVariable Integer userId,HttpServletResponse response) {
		String path = personalPath +"avatar/";
		File file=new File(
				path + "/" + userId + ".png");
		if(file.exists()){
			response.setContentType("image/png");
			byte[] buffer=new byte[1024];
			FileInputStream fis=null;
			BufferedInputStream bis=null;
			try {
				fis=new FileInputStream(file);
				bis=new BufferedInputStream(fis);
				OutputStream os=response.getOutputStream();
				int i=bis.read(buffer);
				while(i!=-1){
					os.write(buffer,0,i);
					i=bis.read(buffer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	@RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
	public String autoLogin(@RequestParam String account,
			@RequestParam String meid) {
		User user = userService.findByAccount(account);
		AutoLoginSign loginSign = autoLSService.findByUserId(user.getUserId());
		if (loginSign == null){
			logsService.save(user.getUserId(), "user", "用户自动登录失败：FAIL_WITH_NO_SIGN");
			return "FAIL_WITH_NO_SIGN";
		}if (meid.equals(loginSign.getSign())) {
			long loginDays = (new Date().getDate() - loginSign.getCreateDate()
					.getDate()) / (1000 * 60 * 60 * 24);
			if (loginDays > MAX_LOGIN_DAY) {
				// 删除代码
				logsService.save(user.getUserId(), "user", "用户自动登录失败：FAIL_WITH_LOGIN_OUTTIME");
				return "FAIL_WITH_LOGIN_OUTTIME";
			} else {
				loginSign.onPreUpdate();
				autoLSService.save(loginSign);
				logsService.save(user.getUserId(), "user", "用户自动登录成功");
				return gson.toJson(user);
			}
		} else{
			logsService.save(user.getUserId(), "user", "用户自动登录失败：FAIL_WITH_DIFFERENT_SIGN");
			return "FAIL_WITH_DIFFERENT_SIGN";
		}
	}

}
