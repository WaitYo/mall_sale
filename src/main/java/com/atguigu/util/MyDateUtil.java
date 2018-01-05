package com.atguigu.util;

import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {

	public static Date getMyTime(int i) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, i);
		Date time = c.getTime();
		return time;
	}
}
