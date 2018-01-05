package com.atguigu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.mapper.AttrMapper;
import com.atguigu.service.AttrService;
@Service
public class AttrServiceImpl implements AttrService {

	@Autowired
	private AttrMapper attrMapper;
	
	

	@Override
	public List<OBJECT_T_MALL_ATTR> get_attr_list_by_class_2(int class_2_id) {
		// TODO Auto-generated method stub
		return attrMapper.select_attr_list_by_class_2(class_2_id);
	}



	@Override
	public void save_attr(List<OBJECT_T_MALL_ATTR> list_attr, int class_2_id) {
		//循环属性集合，插入属性，返回主键
		for(int i=0;i<list_attr.size();i++) {
			//逐条插入
			list_attr.get(i).setFlbh2(class_2_id);
			attrMapper.insert_attr(class_2_id,list_attr.get(i));
			//批量插入属性值
			attrMapper.insert_values(list_attr.get(i).getId(),list_attr.get(i).getList_value());
		}
		
	}


	@Override
	public List<Integer> get_attr_id() {
		List<Integer> list_attr_id = attrMapper.select_attr_id();
		return list_attr_id;
	}

	@Override
	public List<Integer> get_value_id(Integer attr_id) {
		List<Integer> list_value_id = attrMapper.select_value_id(attr_id);
		return list_value_id;
	}
	

}
