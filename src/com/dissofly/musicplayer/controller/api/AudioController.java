package com.dissofly.musicplayer.controller.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dissofly.musicplayer.entity.MusicLrc;
import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.service.ILogsService;
import com.dissofly.musicplayer.service.IMusicLrcService;
import com.dissofly.musicplayer.service.IPublicSongService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/api", produces = "text/plain;charset=UTF-8")
public class AudioController {
	final static String musicPath = "F:/musicCenter/common/music/";

	@Autowired
	IPublicSongService publicSongService;
	@Autowired
	IMusicLrcService lrcService;
	@Autowired
	ILogsService logsService;

	final static String commonPath = "F:/musicCenter/common/";

	@RequestMapping(value = "/song_message/{song_id}")
	public String songMessage(@PathVariable Integer song_id) {
		PublicSong publicSong = publicSongService.getBySongId(song_id);
		if (publicSong != null) {
			return new Gson().toJson(publicSong);
		} else
			return "FAIL_WITH_NOT_FOUND";
	}

	@RequestMapping(value = "/song_search/songName/{song_name}")
	public String searchName(@PathVariable String song_name,
			HttpServletRequest request) {
		String text = "";
		try {
			song_name = new String(song_name.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Gson().toJson(publicSongService.getByName(song_name));
	}

	@RequestMapping(value = "/song_search/artist/{artist}")
	public String searchArtist(@PathVariable String artist) {
		String text = "";
		try {
			artist = new String(artist.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Gson().toJson(publicSongService.getByArtist(artist));
	}

	@RequestMapping(value = "/song_search/album/{album}")
	public String searchAlbum(@PathVariable String album) {
		String text = "";
		try {
			album = new String(album.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Gson().toJson(publicSongService.getByAlbum(album));
	}

	// 返回音乐文件歌词
	@RequestMapping(value = "/online_song/lrc/{song_id}")
	public void songLrc(@PathVariable Integer song_id,
			HttpServletResponse response) {

		MusicLrc mLrc = lrcService.findBySongId(song_id);
		if (mLrc != null) {
			File file = new File(commonPath + "lrc", mLrc.getLrcId() + ".lrc");
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	// 返回音乐文件接口（断点传输）
	@RequestMapping(value = "/online_song/{song_id}")
	public void onlineSong(@PathVariable Integer song_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String s = musicPath + song_id + ".mp3";
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
}
