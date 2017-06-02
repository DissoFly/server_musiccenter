package com.dissofly.musicplayer.controller.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dissofly.musicplayer.entity.MusicList;
import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.service.IMusicListService;
import com.dissofly.musicplayer.service.IPublicSongService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/musicList",produces="text/plain;charset=UTF-8")
public class MusicListController {
	
	final static String commonPath = "F:/musicCenter/common/";

	@Autowired
	IMusicListService listService;
	@Autowired
	IPublicSongService publicSongService;
	
	@RequestMapping(value = "/song_messages", method = RequestMethod.POST)
	public String songMessage(@RequestParam String musics){
		String s[] = musics.split(";");
		List<PublicSong>publicSongs=new ArrayList<>();
		try {
			for (int i = 0; i < s.length; i++){
				publicSongs.add(publicSongService.getBySongId(Integer.parseInt(s[i])));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "FAIL_WITH_NOT_FOUND";
		}
		if (publicSongs.size()>0){
			return new Gson().toJson(publicSongs);
		}else
			return "FAIL_WITH_NOT_FOUND";
	}
	
	@RequestMapping(value = "/save")
	public String save(@PathVariable Integer userId,
			@PathVariable Integer listName,
			@PathVariable String musicList){
		return null;
	}
	@RequestMapping(value = "/list/{list_id}")
	public String getList(@PathVariable Integer list_id){
		return new Gson().toJson(listService.findById(list_id));
	}
	@RequestMapping(value = "/allList")
	public String getAllList(){
		return getAllListByPage(0);
	}
	@RequestMapping(value = "/allList/{page}")
	public String getAllListByPage(@PathVariable Integer page){
		Page<MusicList> musicLists=listService.getAll(page);
		return new Gson().toJson(musicLists.getContent());
	}
	
	@RequestMapping(value = "/listsrc/{src_id}")
	public String add(@PathVariable Integer src_id,HttpServletResponse response) {
		String path = commonPath + "listsrc";
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
