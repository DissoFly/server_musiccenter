package com.dissofly.musicplayer.controller.servlet;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dissofly.musicplayer.entity.MusicList;
import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IMusicListService;
import com.dissofly.musicplayer.service.IPublicSongService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
public class ListController {

	public static final String SESSION_USER = "user";

	@Autowired
	IMusicListService listService;
	@Autowired
	IPublicSongService publicSongService;

	final static String commonPath = "F:/musicCenter/common/";

	@RequestMapping(value = "musicList/add")
	public String add() {
		return "MusicListAdd";
	}

	@RequestMapping(value = "musicList/add_list", method = RequestMethod.POST)
	public String add_list(@RequestParam String listName,
			@RequestParam String listAbout, @RequestParam String musicList,
			MultipartFile srcPath, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null)
			return "Login";
		MusicList muList = new MusicList();
		muList = listService.save(muList);
		List<String> messages = new ArrayList<String>();
		if (listName.equals("") || listAbout.equals("") || musicList.equals(""))
			messages.add("错误：请填写完整信息！");

		if (srcPath != null) {
			try {
				String path = commonPath + "listsrc";

				try {
					ImageInputStream iis = ImageIO
							.createImageInputStream(srcPath.getInputStream());
					Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
					if (!iter.hasNext()) {// 文件不是图片
						messages.add("错误：非图片文件！");
					} else {
						imgSave(800, 800, srcPath,
								path + "/" + muList.getMusicListId() + ".jpg");
						// FileUtils
						// .copyInputStreamToFile(
						// srcPath.getInputStream(), new File(
						// path, muList.getMusicListId()
						// + ".png"));
						muList.setSrcPath(muList.getMusicListId() + "");
						iis.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					messages.add("错误：图片插入失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				messages.add("错误：图片插入失败！");
			}
		} else {
			messages.add("错误：请插入图片！");
		}
		String s[] = musicList.split(";");
		List<Integer> ints = new ArrayList<>();
		List<Integer> errorInt = new ArrayList<>();
		try {
			for (int i = 0; i < s.length; i++) {
				ints.add(Integer.parseInt(s[i]));
				PublicSong publicSong = publicSongService.getBySongId(ints
						.get(i));
				if (publicSong == null) {
					errorInt.add(Integer.parseInt(s[i]));
				}
			}
		} catch (NumberFormatException e) {
			messages.add("错误：请正确填写歌曲编号！");
		} catch (Exception e) {
			e.printStackTrace();
			messages.add("错误：未知错误！");
		}
		if (errorInt.size() > 0) {
			for (Integer integer : errorInt)
				messages.add("错误：歌曲编号" + integer + "：不存在！");
		} else {
			if (s.length <= 0) {
				messages.add("错误：不存在歌曲！");
			} else {
				musicList = "";
				for (int i = 0; i < ints.size(); i++) {
					musicList = musicList.concat(ints.get(i) + ";");
				}
			}
		}
		if (messages.size() > 0) {
			request.setAttribute("messages", messages);
			listService.delectById(muList.getMusicListId());
		} else {
			muList.onPreUpdate();
			muList.setListName(listName);
			muList.setListAbout(listAbout);
			muList.setMusics(musicList);
			muList.setUserId(user.getUserId());
			muList.setPush(true);
			listService.save(muList);
			messages.add("添加成功！");
			request.setAttribute("messages", messages);
		}
		return "MusicListAdd";
	}

	public void imgSave(int w, int h, MultipartFile srcPath, String path)
			throws IOException {
		Image img = ImageIO.read(srcPath.getInputStream()); // 构造Image对象
		int width = img.getWidth(null); // 得到源图宽
		int height = img.getHeight(null); // 得到源图长
		if (width / height > w / h) {
			h = (int) (height * w / width);
		} else {
			w = (int) (width * h / height);
		}
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		File destFile = new File(path);
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// 可以正常实现bmp、png、gif转jpg
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image); // JPEG编码
		out.close();
	}

}
