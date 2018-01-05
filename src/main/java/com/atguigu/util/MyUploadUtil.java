package com.atguigu.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MyUploadUtil {

	public static List<String> upload_images(MultipartFile[] files) {
		List<String> list_image = new ArrayList<String>();

		for (int i = 0; i < files.length; i++) {

			if (!files[i].isEmpty()) {
				String image_name = System.currentTimeMillis() + files[i].getOriginalFilename();
				try {
					files[i].transferTo(new File(
							MyPropertyUtil.getMyPath("upload.properties", "windowspath_image") + "/" + image_name));
					list_image.add(image_name);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

		}

		return list_image;
	}

}
