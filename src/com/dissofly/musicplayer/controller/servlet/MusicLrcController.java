package com.dissofly.musicplayer.controller.servlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dissofly.musicplayer.entity.MusicLrc;
import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IMusicLrcService;
import com.dissofly.musicplayer.service.IPublicSongService;

@Controller
public class MusicLrcController {
	@Autowired
	IMusicLrcService lrcService;
	@Autowired
	IPublicSongService publicSongService;

	public static final String SESSION_USER = "user";

	final static String commonPath = "F:/musicCenter/common/";

	@RequestMapping(value = "musicLrcAdd")
	public String musicLrcAdd() {
		return "MusicLrcAdd";
	}

	@RequestMapping(value = "music_lrc_add")
	public String musicLrcAdd(@RequestParam String songId,
			MultipartFile srcPath, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null)
			return "Login";
		MusicLrc musicLrc = new MusicLrc();
		musicLrc = lrcService.save(musicLrc);
		List<String> messages = new ArrayList<String>();
		int songID = -1;
		try {
			songID = Integer.parseInt(songId);
			PublicSong publicSong = publicSongService.getBySongId(songID);
			if (publicSong == null) {
				messages.add("错误：歌曲编号" + songID + "：不存在！");
			} else {
				MusicLrc lrc = lrcService.findBySongId(songID);
				if (lrc != null) {
					messages.add("错误：已存在歌词！");
				}
			}
		} catch (NumberFormatException e) {
			messages.add("错误：请正确填写歌曲编号！");
		}
		if (messages.size() > 0) {
			request.setAttribute("messages", messages);
			lrcService.delectById(musicLrc.getLrcId());
			return "MusicLrcAdd";
		}
		if (srcPath != null) {
			try {
				FileUtils.copyInputStreamToFile(srcPath.getInputStream(),
						new File(commonPath + "lrc", musicLrc.getLrcId()
								+ ".lrc"));
			} catch (Exception e) {
				e.printStackTrace();
				messages.add("错误：文件上传失败！");
			}
		} else {
			messages.add("错误：请上传文件！");
		}

		if (messages.size() > 0) {
			request.setAttribute("messages", messages);
			lrcService.delectById(musicLrc.getLrcId());
		} else {
			musicLrc.onPrePersist();
			musicLrc.setSongId(songID);
			musicLrc.setUserId(user.getUserId());
			lrcService.save(musicLrc);
			messages.add("添加成功！");
			request.setAttribute("messages", messages);
		}

		return "MusicLrcAdd";
	}
}
