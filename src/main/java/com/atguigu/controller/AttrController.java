package com.atguigu.controller;




import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.bean.T_MALL_ORDER;
import com.atguigu.bean.T_MALL_VALUE;
import com.atguigu.bean.MODEL_T_MALL_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.service.AttrService;




@Controller
public class AttrController {
	
  @Autowired
  private AttrService attrService;
  
	
	@RequestMapping("goto_attr")
	public String goto_attr() {
		return "manager_attr";
	}
	@RequestMapping("get_attr_list")
	public String get_attr_list(int class_2_id,Map<String,Object>map) {
		List<OBJECT_T_MALL_ATTR>list_attr = attrService.get_attr_list_by_class_2(class_2_id);
		map.put("list_attr", list_attr);
		return "manager_attr_list";
	}
	@RequestMapping("attr_get_attr_json")
	@ResponseBody
	public List<OBJECT_T_MALL_ATTR> attr_get_attr_json(int class_2_id,Map<String,Object>map) {
		List<OBJECT_T_MALL_ATTR>list_attr = attrService.get_attr_list_by_class_2(class_2_id);
		map.put("list_attr", list_attr);
		return list_attr;
	}
	
	
	
	
	
	@RequestMapping("save_attr")
	public ModelAndView Save_attr(MODEL_T_MALL_ATTR list_attr,String class_2_name,int class_2_id) {
		//保存属性值集合的业务
		attrService.save_attr(list_attr.getList_attr(),class_2_id);
		ModelAndView mv = new ModelAndView("redirect:/goto_attr_add.do");
		mv.addObject("class_2_name",class_2_name);
		mv.addObject("class_2_id",class_2_id);
		return mv;
		
	}
	
	@RequestMapping("goto_attr_add")
	public String goto_attr_add(String class_2_name, int class_2_id, Map<String,Object> map) {
		map.put("class_2_id", class_2_id);
		map.put("class_2_name", class_2_name);
		return "manager_attr_add";
	}
	
	
	
	
}
