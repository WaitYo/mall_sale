package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;

public interface AttrService {

	

	List<OBJECT_T_MALL_ATTR> get_attr_list_by_class_2(int class_2_id);

	void save_attr(List<OBJECT_T_MALL_ATTR> list_attr, int class_2_id);
	List<Integer> get_attr_id();

	List<Integer> get_value_id(Integer attr_id);
}
