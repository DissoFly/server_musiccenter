package com.dissofly.musicplayer.controller.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dissofly.musicplayer.service.IInformationService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/api/news", produces = "text/plain;charset=UTF-8")
public class NewController {
	final static String commonPath = "F:/musicCenter/common/";
	@Autowired
	IInformationService inforService;
	
	@RequestMapping(value = "/getOne/{id}")
	public String getOne(@PathVariable Integer id){
		return new Gson().toJson(inforService.getById(id));
	}

	@RequestMapping(value = "/getAll")
	public String getAll(){
		return getAll(0);
	}
	
	@RequestMapping(value = "/getAll/{page}")
	public String getAll(@PathVariable Integer page){
		return new Gson().toJson(inforService.getAll(page).getContent());
	}

	
	@RequestMapping(value = "/news_src/{src_id}")
	public String add(@PathVariable Integer src_id,HttpServletResponse response) {
		String path = commonPath + "informationSrc";
		File file=new File(
				path + "/" + src_id + ".jpg");
		if(file.exists()){
			response.setContentType("image/jpg");
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
	
}
