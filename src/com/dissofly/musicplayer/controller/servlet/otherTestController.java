package com.dissofly.musicplayer.controller.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dissofly.musicplayer.entity.User;

@Controller
public class otherTestController {

	@RequestMapping(value = "/text/thread")
	public void thread1(HttpServletRequest request) {
		TicketWindow tw=new TicketWindow();
		new Thread(tw, "窗口1").start();
		new Thread(tw, "窗口2").start();
		new Thread(tw, "窗口3").start();
		new Thread(tw, "窗口4").start();
	}
	
}

class TicketWindow implements Runnable{
	private int tickets=100;
	private int notickets=0;
	@Override
	public void run() {
		while(true){
			if(notickets>10){
				break;
			}
			if(tickets>0){
				Thread th=Thread.currentThread();
				String th_name=th.getName();
				System.out.println(th_name+"正在发售第 "+tickets-- +"张票 ");
			}else{
				Thread th=Thread.currentThread();
				String th_name=th.getName();
				System.out.println(th_name+"已经发售完毕");
				notickets++;
			}
		}
		
	}
}