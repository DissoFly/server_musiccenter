package com.dissofly.musicplayer.controller.servlet;

import java.awt.Image;
import java.awt.image.BufferedImage;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dissofly.musicplayer.entity.Information;
import com.dissofly.musicplayer.entity.User;
import com.dissofly.musicplayer.service.IInformationService;
import com.dissofly.musicplayer.service.IPublicSongService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
public class NewsController {

	@Autowired
	IInformationService inforService;

	public static final String SESSION_USER = "user";

	final static String commonPath = "F:/musicCenter/common/";

	@RequestMapping(value = "musicNewsAdd")
	public String musicNewsAdd() {
		return "MusicNewsAdd";
	}

	@RequestMapping(value = "music_news_add")
	public String musicNewsAdd(@RequestParam String text,@RequestParam String title,
			MultipartFile srcPath, HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null)
			return "Login";
		Information information = new Information();
		information = inforService.save(information);
		List<String> messages = new ArrayList<String>();
		if (text.equals("")) {
			messages.add("错误：请输入文字！");
		}
		if (title.equals("")) {
			messages.add("错误：请输入文字！");
		}
		if (srcPath != null) {
			String path = commonPath + "informationSrc";
			try {
				ImageInputStream iis = ImageIO.createImageInputStream(srcPath
						.getInputStream());
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				if (!iter.hasNext()) {// 文件不是图片
					messages.add("错误：非图片文件！");
				} else {
					imgSave(800, 800, srcPath,
							path + "/" + information.getInformationId()
									+ ".jpg");
					iis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				messages.add("错误：图片插入失败！");
			}
		} else {
			messages.add("错误：请插入图片！");
		}

		if (messages.size() > 0) {
			request.setAttribute("messages", messages);
			File file = new File(commonPath + "informationSrc/"
					+ information.getInformationId() + ".jpg");
			if (file.exists()) {
				file.delete();
			}
			inforService.delectById(information.getInformationId());
		} else {
			information.onPrePersist();
			information.setText(text);
			information.setTitle(title);
			information.setUserId(user.getUserId());
			inforService.save(information);
			messages.add("添加成功！");
			request.setAttribute("messages", messages);
		}

		return "MusicNewsAdd";
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
