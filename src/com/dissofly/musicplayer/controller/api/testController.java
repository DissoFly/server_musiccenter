package com.dissofly.musicplayer.controller.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.dissofly.musicplayer.entity.SplitSong;
//import com.dissofly.musicplayer.service.ISplitSongService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api")
public class testController {

	final static String musicPath = "F:/musicCenter/common/music/";
//	@Autowired
//	ISplitSongService splitSongService;
	
	
	/*
	 * -1:ID3V1 -2:ID3V2
	 */
	@RequestMapping(value = "/online_play/{song_id}/{spile_id}")
	public void downloadResource(@PathVariable Integer song_id,
			@PathVariable Integer spile_id, HttpServletResponse response) {
		// if(splitSongService.findBySongId(song_id)==null)
		// return "FAIL_WITH_NO_SPLIT";
		String path = musicPath + "buffer/" + song_id + "/" + song_id;
		File file;
		switch (spile_id) {
		case -1:
			file = new File(path + ".mp3ID3V1");
			break;
		case -2:
			file = new File(path + ".mp3ID3V2");
			break;
		default:
			file = new File(path + "-" + spile_id + ".mp3");
			break;
		}

		if (file.exists()) {
			// response.setContentType("audio/mp3");
			// response.addHeader("Content-Disposition",
			// "attachment; filename="+song_id+"-"+spile_id+".mp3");
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

//	@RequestMapping(value = "/online_play/{song_id}")
//	public String online_play_split(@PathVariable Integer song_id) {
//		String result = split(song_id);
//
//		if (result.equals("SUCCESS_IN_SPLIT")) {
//			SplitSong splitSong = splitSongService.findBySongId(song_id);
//			splitSong.onPreUpdate();
//			splitSongService.save(splitSong);
//			return new Gson().toJson(splitSong);
//		} else
//			return result;
//	}

//	public String split(Integer songId) {
//		String src = musicPath + songId + ".mp3";
//		if (splitSongService.findBySongId(songId) != null)
//			return "SUCCESS_IN_SPLIT";
//		if ("".equals(src) || src == null) {
//			System.out.println("分割失败");
//			return "FAIL_IN_SPLIT";
//		}
//
//		File file = new File(musicPath + "buffer/" + songId);
//		if (!file.exists() && !file.isDirectory()) {
//			System.out.println("//不存在");
//			file.mkdir();
//		} else {
//			System.out.println("//目录存在");
//		}
//
//		try {
//			if (getID3(songId).equals("fail"))
//				return "FAIL_IN_SPLIT";
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			return "FAIL_IN_SPLIT";
//		}
//		Boolean isSuccess = false;
//		String srcFile = musicPath + "buffer/" + songId + "/" + songId
//				+ ".mp3MEDIUM";// 源文件
//		List<Integer> list = initMP3Frame(srcFile);
//		try {
//			MP3File f = (MP3File) AudioFileIO.read(new File(src));
//			MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
//			int number = 0;
//			for (int i = 0; i < audioHeader.getTrackLength(); i = i + 3) {
//				CutingMp3(srcFile, list, i, songId, number);
//				number++;
//			}
//			SplitSong splitSong = new SplitSong();
//			splitSong.setSongId(songId);
//			splitSong.setQuantity(number);
//
//			splitSong.setAllLength(new File(srcFile).length());
//
//			splitSong.setTimeLength(audioHeader.getTrackLength());
//
//			splitSong.onPrePersist();
//			splitSongService.save(splitSong);
//			isSuccess = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		File files = new File(srcFile);
//		if (files.exists()) {
//			files.delete();
//		}
//		if (isSuccess)
//			return "SUCCESS_IN_SPLIT";
//		else
//			return "FAIL_IN_SPLIT";
//	}

	@RequestMapping(value = "/test_download2")
	public String test3() {
		// 文件数n(已知)
		// 总大小m(需测)
		// 时长k（需测）
		// 最后文件大小q=m-(n-1)*128
		// 最后文件时长=(k/m)*q
		// 正常文件时长=（k-最后文件时长）/(n-1)
		//
		// 需增加m,k
		// 待增加：MD5验证第一文件（防止因服务器文件变更而下载出错）
		String fileString = "F:/musicCenter/common/music/1.mp3";
		File file = new File(fileString);
		try {
			MP3File f = (MP3File) AudioFileIO.read(file);
			MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
			// return audioHeader.getTrackLength();
			System.out.println(audioHeader.getTrackLength());
			System.out.println(new File(
					"F:/musicCenter/common/music/buffer/1/1-0.mp3").length());
		} catch (Exception e) {
		}

		return "";

	}

	/**
	 * 分离出数据帧每一帧的大小并存在list数组里面 失败则返回空
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<Integer> initMP3Frame(String path) {
		File file = new File(path);
		List<Integer> list = new ArrayList<>();
		int framSize = 0;
		RandomAccessFile rad = null;
		try {
			rad = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (framSize < file.length()) {
			byte[] head = new byte[4];
			try {
				rad.seek(framSize);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				rad.read(head);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int bitRate = getBitRate((head[2] >> 4) & 0x0f) * 1000;
			int sampleRate = getsampleRate((head[2] >> 2) & 0x03);
			int paing = (head[2] >> 1) & 0x01;
			if (bitRate == 0 || sampleRate == 0)
				return null;
			int len = 144 * bitRate / sampleRate + paing;
			list.add(len);// 将数据帧的长度添加进来
			framSize += len;
		}
		return list;
	}

	private static int getBitRate(int i) {
		int a[] = { 0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224,
				256, 320, 0 };
		return a[i];
	}

	private static int getsampleRate(int i) {
		int a[] = { 44100, 48000, 32000, 0 };
		return a[i];
	}

	/**
	 * 返回切割后的MP3文件的路径 返回null则切割失败 开始时间和结束时间的整数部分都是秒，以秒为单位
	 * 
	 * 
	 * @param list
	 * @param startTime
	 * @param stopTime
	 * @return
	 * @throws IOException
	 */
	public static String CutingMp3(String path, List<Integer> list,
			double startTime, int songId, int spiltId) throws IOException {
		File file = new File(path);
		int start = (int) (startTime / 0.026);
		int stop = (int) ((startTime + 3) / 0.026);
		if ((start > stop) || (start < 0) || (stop < 0)
				|| (start >= list.size())) {
			return null;
		} else {
			long seekStart = 0;// 开始剪切的字节的位置
			for (int i = 0; i < start; i++) {
				seekStart += list.get(i);

			}
			long seekStop = 0;// 结束剪切的的字节的位置
			for (int i = 0; i < stop; i++) {
				if (i >= list.size())
					break;
				seekStop += list.get(i);

			}
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(seekStart);
			File file1 = new File(musicPath + "buffer/" + songId + "/" + songId
					+ "-" + spiltId + ".mp3");
			FileOutputStream out = new FileOutputStream(file1);
			byte[] bs = new byte[(int) (seekStop - seekStart)];
			raf.read(bs);
			out.write(bs);
			raf.close();
			out.close();
			return file1.getAbsolutePath();
		}

	}

	// /////////////////////////////////////////////////
	public String getID3(int songId) throws IOException {
		System.out.println("start get ID3");
		File file = new File(musicPath + songId + ".mp3");
		String path = musicPath + "buffer/" + songId + "/" + songId + ".mp3";
		File fileID3V2 = new File(path + "ID3V2");// 获取id3v2
		File fileBehind = new File(path + "Behind");// 分离id3v2后文件；
		File fileID3V1 = new File(path + "ID3V1");// 获取id3v1；
		File fileMdeium = new File(path + "MEDIUM");// 分离id3后文件；

		if (!getID3V2(file, fileID3V2, fileBehind).equals("fail"))
			return getID3V1(fileBehind, fileID3V1, fileMdeium);

		return "fail";
	}

	public String getID3V2(File file1, File file2, File file3) {
		try {
			RandomAccessFile rf1 = new RandomAccessFile(file1, "rw");
			FileOutputStream fos2 = new FileOutputStream(file2);
			FileOutputStream fos3 = new FileOutputStream(file3);
			byte ID3[] = new byte[3];
			rf1.read(ID3);
			String ID3str = new String(ID3);
			if (ID3str.equals("ID3")) {
				System.out.println(" get ID3");
				rf1.seek(6);
				byte[] ID3size = new byte[4];
				rf1.read(ID3size);
				int size1 = (ID3size[0] & 0x7f) << 21;
				int size2 = (ID3size[1] & 0x7f) << 14;
				int size3 = (ID3size[2] & 0x7f) << 7;
				int size4 = (ID3size[3] & 0x7f);
				int size = size1 + size2 + size3 + size4 + 10;
				rf1.seek(0);
				byte[] bs = new byte[1024 * 4];
				int lens = 0;
				int count = 0;
				while ((lens = rf1.read(bs)) != -1) {
					fos2.write(bs, 0, lens);
					count += lens;
					if (count > size) {
						break;
					}
				}
				fos2.close();

				rf1.seek(size);
				lens = 0;
				while ((lens = rf1.read(bs)) != -1) {
					fos3.write(bs, 0, lens);
				}
				fos3.close();
				rf1.close();
				return "true get IDV3V2";
			} else {// 否则完全复制文件
				System.out.println("not get ID3");
				int lens = 0;
				rf1.seek(0);
				byte[] bs = new byte[1024 * 4];
				while ((lens = rf1.read(bs)) != -1) {
					fos3.write(bs, 0, lens);
				}
				fos3.close();
				fos2.close();
				rf1.close();
				return "true get all";
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}

	}

	public String getID3V1(File file1, File file2, File file3) {
		try {
			RandomAccessFile rf1 = new RandomAccessFile(file1, "rw");
			FileOutputStream fos2 = new FileOutputStream(file2);
			FileOutputStream fos3 = new FileOutputStream(file3);

			byte TAG[] = new byte[3];
			rf1.seek(rf1.length() - 128);
			rf1.read(TAG);
			String tagstr = new String(TAG);
			if (tagstr.equals("TAG")) {
				rf1.seek(0);
				byte[] bs = new byte[(int) (rf1.length() - 128)];
				rf1.read(bs);
				fos3.write(bs);
				fos3.close();

				rf1.seek(rf1.length() - 128);
				byte[] bs1 = new byte[1024 * 4];
				int len = 0;
				while ((len = rf1.read(bs1)) != -1) {
					fos2.write(bs1, 0, len);
				}
				fos2.close();
				rf1.close();
			} else {// 否则完全复制内容至file2
				System.out.println("not get TAG");
				rf1.seek(0);
				byte[] bs = new byte[1024 * 4];
				int len = 0;
				while ((len = rf1.read(bs)) != -1) {
					fos3.write(bs, 0, len);
				}
				rf1.close();
				fos3.close();
				fos2.close();
			}
			if (file1.exists()) {
				file1.delete();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "success";
	}

	
	
}
