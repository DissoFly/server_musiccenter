package com.dissofly.musicplayer.controller.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dissofly.musicplayer.entity.Logs;
import com.dissofly.musicplayer.service.IComnentService;
import com.dissofly.musicplayer.service.ILogsService;
import com.google.gson.Gson;

@Controller
public class LogsController {

	@Autowired
	ILogsService logsService;
	

	@RequestMapping(value = "allLogs/{page}")
	public String allLogs(@PathVariable Integer page,
			HttpServletRequest request) {
		request.setAttribute("logses",logsService.findAll(page).getContent());
		request.setAttribute("logsCount",logsService.getAllCount());
		request.setAttribute("page",page);
		request.setAttribute("allPage",(logsService.getAllCount()-1)/10);
		return "AllLogs";
	}
}
