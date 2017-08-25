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

import com.dissofly.musicplayer.entity.MusicLrc;
import com.dissofly.musicplayer.service.IMusicLrcService;

@RestController
@RequestMapping(value ="/api/lrc",produces="text/plain;charset=UTF-8")
public class LrcController {
	@Autowired
	IMusicLrcService lrcService;
	final static String commonPath = "F:/musicCenter/common/";
	
	@RequestMapping(value = "/isHaveLrc/{song_id}")
	public String isHaveLrc(@PathVariable Integer song_id){
		MusicLrc musicLrc=lrcService.findBySongId(song_id);
		if(musicLrc!=null){
			return "HAVE_LRC:"+musicLrc.getLrcId();
		}else{
			return "HAVE_NOT_LRC";
		}
	}
	
	@RequestMapping(value = "/getLrcByLrcId/{lrc_id}")
	public void getLrcByLrcId(@PathVariable Integer lrc_id,HttpServletResponse response) {
		getLrc(lrc_id,response);
	}
	
	@RequestMapping(value = "/getLrcBySongId/{song_id}")
	public void getLrcBySongId(@PathVariable Integer song_id,HttpServletResponse response) {
		MusicLrc musicLrc=lrcService.findBySongId(song_id);
		if(musicLrc!=null){
			getLrc(musicLrc.getLrcId(),response);
		}
	}
	
	private void getLrc(Integer lrc_id,HttpServletResponse response){
		String path = commonPath + "lrc";
		File file=new File(
				path , lrc_id + ".lrc");
		if(file.exists()){
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
	}

}
