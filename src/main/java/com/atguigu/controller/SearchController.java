package com.atguigu.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bean.MODEL_T_MALL_SKU_ATTR_VALUE;
import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_SKU_CLASS;
import com.atguigu.bean.OBJECT_T_MALL_SKU_DETAIL;
import com.atguigu.bean.OBJECT_T_MALL_SKU_KEYWORDS;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.service.AttrService;
import com.atguigu.service.SearchServcie;
import com.atguigu.util.JedisPoolUtils;
import com.atguigu.util.MyCacheUtil;
import com.atguigu.util.MyHttpGetUtil;
import com.atguigu.util.MyJsonUtil;
import com.atguigu.util.MyPropertyUtil;

import redis.clients.jedis.Jedis;

@Controller
public class SearchController {
	@Autowired
	private SearchServcie searchServcie;
	@Autowired
	private AttrService attrService;
	
	@RequestMapping("search_keywords")
	public String search_keywords(String keywords,ModelMap map) {
		List<OBJECT_T_MALL_SKU_KEYWORDS> list_sku = new ArrayList<OBJECT_T_MALL_SKU_KEYWORDS>();
		//调用关键字检索项目
		try {
			String doGet = MyHttpGetUtil
      .doGet(MyPropertyUtil.getMyPath("keywords.properties", "sku_keywords") + keywords + ".do");
			MyJsonUtil.json_to_list(doGet, OBJECT_T_MALL_SKU_KEYWORDS.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//调用搜索服务项目
		map.put("list_sku", list_sku);
		map.put("keywords", keywords);
		return "sale_search_keywords_list";
	}
	
	
	
	@RequestMapping("add_cache")
	@ResponseBody
	public String add_cache() {

		List<Integer> list_attr_id = attrService.get_attr_id();

		for (int i = 0; i < list_attr_id.size(); i++) {
			Integer attr_id = list_attr_id.get(i);

			List<Integer> list_value_id = attrService.get_value_id(attr_id);

			for (int j = 0; j < list_value_id.size(); j++) {

				String key = "attr_" + 28 + "_" + attr_id + "_" + list_value_id.get(j);

				T_MALL_SKU_ATTR_VALUE t_MALL_SKU_ATTR_VALUE = new T_MALL_SKU_ATTR_VALUE();
				t_MALL_SKU_ATTR_VALUE.setShxm_id(attr_id);
				t_MALL_SKU_ATTR_VALUE.setShxzh_id(list_value_id.get(j));
				List<T_MALL_SKU_ATTR_VALUE> list_av = new ArrayList<T_MALL_SKU_ATTR_VALUE>();
				list_av.add(t_MALL_SKU_ATTR_VALUE);
				List<OBJECT_T_MALL_SKU_CLASS> list_sku = searchServcie.attr_search(28, list_av);

				MyCacheUtil.setList(key, list_sku);
			}
		}

		return "111";
	}
	// 查询商品信息
	@RequestMapping("goto_class_search")
	public String goto_class_search(String class_2_name, int class_2_id, ModelMap map) {
		// 调用商品按照分类查询的业务
		List<OBJECT_T_MALL_ATTR> list_attr = attrService.get_attr_list_by_class_2(class_2_id);
		List<OBJECT_T_MALL_SKU_CLASS> list_sku = new ArrayList<OBJECT_T_MALL_SKU_CLASS>();
		String key = "class_2_" + class_2_id;
		// redis

		/*
		 * Jedis jedis = JedisPoolUtils.getJedis(); Set<String> zrange =
		 * jedis.zrange("class_2_"+class_2_id, 0, -1); Iterator<String> iterator =
		 * zrange.iterator(); while(iterator.hasNext()) { String next = iterator.next();
		 * OBJECT_T_MALL_SKU_CLASS obj = MyJsonUtil.json_to_object(next,
		 * OBJECT_T_MALL_SKU_CLASS.class); list_sku.add(obj); }
		 */
		list_sku = MyCacheUtil.getList(key, OBJECT_T_MALL_SKU_CLASS.class);
		if (list_sku == null || list_sku.size() == 0) {
			// mysql
			list_sku = searchServcie.get_sku_by_class(class_2_id);
			// 将查询结果放入redis
			MyCacheUtil.setList(key, list_sku);
		}

		// List<OBJECT_T_MALL_SKU_CLASS>
		// list_sku=searchServcie.get_sku_by_class(class_2_id);
		// System.out.println(111);
		map.put("class_2_name", class_2_name);
		map.put("class_2_id", class_2_id);
		map.put("list_attr", list_attr);
		map.put("list_sku", list_sku);
		return "sale_search";
	}

	// 根据商品的属性查询内容列表
	@RequestMapping("attr_search")
	public String attr_search(int class_2_id, MODEL_T_MALL_SKU_ATTR_VALUE sav, ModelMap map) {
		List<OBJECT_T_MALL_SKU_CLASS> list_sku = new ArrayList<OBJECT_T_MALL_SKU_CLASS>();
		// 根据属性查询商品内容列表
		List<T_MALL_SKU_ATTR_VALUE> list_sku_av = sav.getList_av();

		// 需要进行交叉的key的数组
		String[] keys = new String[list_sku_av.size()];

		String dstkey = "dst_";

		for (int i = 0; i < list_sku_av.size(); i++) {
			String key = "attr_" + class_2_id + "_" + list_sku_av.get(i).getShxm_id() + "_"
					+ list_sku_av.get(i).getShxzh_id();
			keys[i] = key;

			dstkey = dstkey + key;
		}

		// 将属性id的key交叉起来
		Jedis jedis = JedisPoolUtils.getJedis();

		// 如果之前没有dstkey
		Boolean exists = jedis.exists(dstkey);
		if (!exists) {
			jedis.zinterstore(dstkey, keys);
		}

		list_sku = MyCacheUtil.getList(dstkey, OBJECT_T_MALL_SKU_CLASS.class);

		// mysql
		if (list_sku == null || list_sku.size() == 0) {
			list_sku = searchServcie.attr_search(class_2_id, sav.getList_av());
			// MyCacheUtil.setList(keys, list_sku.get(class_2_id));
		}
		map.put("list_sku", list_sku);

		return "sale_search_sku_list";
	};

	@RequestMapping("sku_detail")
	public String sku_detail(int sku_id, int spu_id, ModelMap map) {
		OBJECT_T_MALL_SKU_DETAIL obj_sku = searchServcie.get_sku_detail(spu_id, sku_id);
		List<T_MALL_SKU> list_sku = searchServcie.get_sku_list_by_spu_id(spu_id);
		map.put("obj_sku", obj_sku);
		map.put("list_sku", list_sku);
		return "sale_search_detail";
	}
}
