package com.atguigu.util;

import java.io.IOException;
import java.util.Properties;

public class MyPropertyUtil {

	public static String getMyPath(String property, String key) {

		Properties properties = new Properties();

		try {
			properties.load(MyPropertyUtil.class.getClassLoader().getResourceAsStream(property));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String val = properties.getProperty(key);

		return val;
	}

}
