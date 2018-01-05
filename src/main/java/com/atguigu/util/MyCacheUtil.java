package com.atguigu.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.atguigu.bean.OBJECT_T_MALL_SKU_CLASS;

import redis.clients.jedis.Jedis;

public class MyCacheUtil {

	public static <T> List<T> getList(String key, Class<T> t) {
		List<T> list = new ArrayList<T>();
		try {
			Jedis jedis = JedisPoolUtils.getJedis();
			   Set<String> zrange = jedis.zrange(key, 0, -1);
			   Iterator<String> iterator = zrange.iterator();
			   while(iterator.hasNext()) {
				   String next = iterator.next();
				   T obj = MyJsonUtil.json_to_object(next, t);
				   list.add(obj);
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return list;
	}

	public static <T> void setList(String key, List<T> list) {
		try {
			Jedis jedis = JedisPoolUtils.getJedis();
			for(int i=0;i<list.size();i++) {
				jedis.zadd(key, i,MyJsonUtil.object_to_json(list.get(i)));
			}
		} catch (Exception e) {
			
		}
		
	}

	public static void interstore(String dstkey, String[] keys) {
		//将属性id的key交叉起来
		   Jedis jedis = JedisPoolUtils.getJedis();
		   //如果之前没有dstkey
		   Boolean exists = jedis.exists(dstkey);
		   if(!exists) {
			   jedis.zinterstore(dstkey, keys);
		   }
		
	}

}
