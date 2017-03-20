package com.dissofly.musicplayer.controller.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dissofly.musicplayer.UploadedFile;
import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.entity.UploadMessage;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.id3v2.MusicInfo;
import com.dissofly.musicplayer.id3v2.MusicMessage;
import com.dissofly.musicplayer.service.IPublicSongService;
import com.dissofly.musicplayer.service.IUploadMessageService;
import com.dissofly.musicplayer.service.IUserService;
import com.google.gson.Gson;

@Controller
public class MusicController {

	@Autowired
	IUploadMessageService uploadMessageService;
	@Autowired
	IPublicSongService publicSongService;
	@Autowired
	IUserService userService;

	public static final String SESSION_USER = "user";

	final static String commonPath = "F:/musicCenter/common/";

	@RequestMapping(value = {"allMusic","musicList/AllMusic"})
	public String allMusic(HttpServletRequest request) {
		return allMusic(0, request);
	}

	@RequestMapping(value = {"allMusic/{page}","musicList/AllMusic/{page}"})
	public String allMusic(@PathVariable Integer page,
			HttpServletRequest request) {
		request.setAttribute("publicSongs",publicSongService.getAll(page).getContent());
		request.setAttribute("songCount",publicSongService.getAllCount());
		request.setAttribute("page",page);
		request.setAttribute("allPage",publicSongService.getAllCount()/10);
		return "AllMusic";
	}

	@RequestMapping(value = "musicAdd")
	public String adminLogin(HttpServletRequest request) {
		return "MusicAdd";
	}

	@RequestMapping(value = "/file_upload")
	public void saveFile(HttpServletRequest request,
			@ModelAttribute UploadedFile uploadedFile,
			BindingResult bindingResult, Model model) {
		MultipartFile multipartFile = uploadedFile.getMultipartFile();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);

		String fileName = multipartFile.getOriginalFilename();

		UploadMessage uploadMessage = new UploadMessage();
		uploadMessage.setUserID(user.getUserId());
		uploadMessage.setFileName(fileName);
		uploadMessage.onPrePersist();
		uploadMessage = uploadMessageService.save(uploadMessage);
		System.out.println(uploadMessage.getFileID());
		try {
			File file = new File(commonPath + "upload",
					uploadMessage.getFileID() + ".mp3");
			// System.out.println(fileName);
			multipartFile.transferTo(file);
		} catch (IOException e) {
			uploadMessageService.deleteById(uploadMessage.getFileID());
			e.printStackTrace();
		}
	}

	// 试听
	@RequestMapping(value = "/listen/{file_id}")
	public void onlineSong(@PathVariable Integer file_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String s = commonPath + "upload/" + file_id + ".mp3";
		File f = new File(s);
		FileInputStream fis = new FileInputStream(f);
		response.reset();
		response.setHeader("Server", "musicCenter@dissofly.com");
		response.setHeader("Accept-Ranges", "bytes");
		long p = 0;
		long l = 0;
		l = f.length();
		if (request.getHeader("Range") != null) // 客户端请求的下载的文件块的开始字节
		{
			response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
			p = Long.parseLong(request.getHeader("Range")
					.replaceAll("bytes=", "").replaceAll("-", ""));
		}
		response.setHeader("Content-Length", new Long(l - p).toString());
		if (p != 0) {
			response.setHeader(
					"Content-Range",
					"bytes " + new Long(p).toString() + "-"
							+ new Long(l - 1).toString() + "/"
							+ new Long(l).toString());
		}
		response.setContentType("application/octet-stream");
		// response.setHeader("Content-Disposition", "attachment;filename="
		// + song_id + ".mp3");
		fis.skip(p);
		byte[] b = new byte[1024];
		int i;
		while ((i = fis.read(b)) != -1) {
			response.getOutputStream().write(b, 0, i);
		}
		fis.close();
	}

	@RequestMapping(value = "/musicConfirm")
	public String fileConfirm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null)
			return "Login";
		List<UploadMessage> uploadMessages = uploadMessageService.getAll();
		List<User> users = userService.getAll();
		request.setAttribute("uploadMessages", uploadMessages);
		request.setAttribute("users", users);
		List<MusicMessage> musicMessages = new ArrayList<>();

		for (UploadMessage uMessage : uploadMessages) {
			MusicMessage message = new MusicMessage();
			MusicInfo musicInfo = new MusicInfo();
			musicInfo.setPath(commonPath + "upload/" + uMessage.getFileID()
					+ ".mp3");
			int i = -1;
			try {
				i = musicInfo.parseMusic();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (i == 0) {
				// System.out.println(musicInfo.toString() + "\n\n");
			} else {
				System.out.println("Fail with:" + musicInfo.parseMusic());
			}
			message.setSongName(musicInfo.getTitle());
			message.setArtist(musicInfo.getPerformer());
			message.setAlbum(musicInfo.getAlbum());
			musicMessages.add(message);
		}
		request.setAttribute("musicMessages", musicMessages);

		return "MusicConfirm";

	}

	@RequestMapping(value = "/music_confirm")
	public String music_confirm(@RequestParam String songName,
			@RequestParam Integer fileId, @RequestParam String artist,
			@RequestParam String album, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null)
			return "Login";
		if (songName.equals("") || artist.equals("") || album.equals("")) {
			request.setAttribute("message", "确认失败：请输入完整信息");
			return fileConfirm(request);
		}
		PublicSong publicSong = new PublicSong();
		try {
			publicSong.setSongName(new String(songName.getBytes("ISO-8859-1"),
					"utf8"));
			publicSong
					.setAlbum(new String(album.getBytes("ISO-8859-1"), "utf8"));
			publicSong.setArtist(new String(artist.getBytes("ISO-8859-1"),
					"utf8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "确认失败：文字转码失败");
			return fileConfirm(request);
		}

		publicSong.onPrePersist();
		publicSong.setUserID(user.getUserId());
		publicSong = publicSongService.save(publicSong);
		System.out.println(new Gson().toJson(publicSong));

		// 文件转移
		boolean isCopy = copyFile(fileId, publicSong.getSongID());
		if (!isCopy) {
			publicSongService.delectById(publicSong.getSongID());
			request.setAttribute("message", "确认失败：复制文件失败");
			return fileConfirm(request);
		}

		// 删除记录
		uploadMessageService.deleteById(fileId);
		request.setAttribute("message", "确认成功");
		return fileConfirm(request);
	}

	private boolean copyFile(Integer fileId, Integer songID) {
		File fileFrom = new File(commonPath + "upload/", fileId + ".mp3");
		File fileTO = new File(commonPath + "music/", songID + ".mp3");

		int byteread = 0;
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(fileFrom);
			out = new FileOutputStream(fileTO);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fileFrom.isFile() && fileFrom.exists()) {
			fileFrom.delete();
		}
		return true;
	}
}
