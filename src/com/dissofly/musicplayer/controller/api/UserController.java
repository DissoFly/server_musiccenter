package com.dissofly.musicplayer.controller.api;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IUserService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
@RestController
@RequestMapping("/api")
public class UserController {
	
	private static Gson gson=new Gson(); 

	@Autowired
	IUserService userService;
	
	@RequestMapping(value="/")
	public String text(){
		return "connect success!";
	}
	
	
	@RequestMapping(value="/test", method=RequestMethod.POST)
	public String text2(@RequestParam("testString") String testString){
		System.out.println(testString);
		return "connect success!"+testString;
	}
	
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(
			@RequestParam String account,
			@RequestParam String passwordHash,
			@RequestParam String email,
			@RequestParam long phoneNumber,
			HttpServletRequest request
			){
		User user=new User(account,passwordHash,email,phoneNumber,false);
		userService.save(user);
		return "SUCCESS_IN_REGISTER";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			@RequestParam String account,
			@RequestParam String passwordHash,
			HttpServletRequest request
			){
		User user=userService.findByAccount(account);
		if(user==null)
			return "FAIL_WITH_NO_ACCOUNT";
		else if(user.getPasswordHsah().equals(passwordHash))
			return "SUCCESS_IN_LOGIN";
		else
			return "FAIL_WITH_WRONG_PASSWORD";
	}

	

}
