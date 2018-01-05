package com.atguigu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.util.MyJsonUtil;


@Controller
public class CartController {
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	
	
	
	
	//是否选中
	@RequestMapping("change_status")
	public String change_status(HttpServletResponse response,HttpSession session,@CookieValue(value="list_cart_cookie",required=false)String list_cart_cookie,T_MALL_SHOPPINGCAR cart,ModelMap map) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		if(user==null) {
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		}else {
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}
		for(int i=0;i<list_cart.size();i++) {
			if(list_cart.get(i).getSku_id()==cart.getSku_id())
			list_cart.get(i).setShfxz(cart.getShfxz());
			if(user==null) {
				//更新cookie
				Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));
				cookie.setMaxAge(60*60*24*7);
				response.addCookie(cookie);
				
			}else {
			//更新db
				shoppingCartService.update_cart(list_cart.get(i));
			}
		}
		map.put("list_cart", list_cart);
		return "sale_cart_list_inner";
	}
	//购物车
	@RequestMapping("goto_cart_list")
	public String goto_cart_list(HttpSession session,@CookieValue(value="list_cart_cookie",required=false)String list_cart_cookie,ModelMap map) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		if(user==null) {
			list_cart=MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		}else {
			//用户已登入
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}
		map.put("list_cart", list_cart);
		return "sale_cart_list";
	}
	//迷你购物车
	@RequestMapping("miniCart")
	public String miniCart(HttpSession session,@CookieValue(value="list_cart_cookie",required=false)String list_cart_cookie,
			ModelMap map) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR>list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		if(user==null) {
			//用户未登入，cookie
			list_cart=MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		}else {
			//用户已登入，cookie
			list_cart= (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}
		map.put("list_cart", list_cart);
		return "sale_miniCart_list";
	}

	@RequestMapping("add_cart")
	public String add_cart(T_MALL_SHOPPINGCAR cart,HttpServletResponse response,
			HttpSession session,@CookieValue(value="list_cart_cookie",required=false)String list_cart_cookie,ModelMap map) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		//session中是否有用户
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		//1、判断用户是否登入
		//1）用户未登入且cookie为空的情况下
		if(cart.getYh_id()==0) {
			//判断字符串是否为空
			if(StringUtils.isBlank(list_cart_cookie)) {
				//List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
				list_cart.add(cart);
				/*//添加cookie
				Cookie cookie = new Cookie("list_cart_cookie",MyJsonUtil.list_to_json(list_cart));
				//设置过期时间
				cookie.setMaxAge(60*60*24);
				response.addCookie(cookie);*/
			}else {
				//cookie 不为空时
				//List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
				 list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
				//判断是否重复   集合中是否有cart对象
				 Boolean b=if_new_cart(list_cart,cart);
				 if(b) {
					 //添加
					 list_cart.add(cart);
					// List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
					 //添加
					/* Cookie cookie = new Cookie("list_cart_cookie",MyJsonUtil.list_to_json(list_cart));
						//设置过期时间
						cookie.setMaxAge(60*60*24);
						response.addCookie(cookie);*/
				 }else {
					 //List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
					 //更新
					/* Cookie cookie = new Cookie("list_cart_cookie",MyJsonUtil.list_to_json(list_cart));
						//设置过期时间
						cookie.setMaxAge(60*60*24);
						response.addCookie(cookie);*/
					 //更新
					 for(int i=0;i<list_cart.size();i++) {
						 if(cart.getSku_id()==list_cart.get(i).getSku_id()) {
							 //添加数量=原来数量和新增数量
							 list_cart.get(i).setTjshl(list_cart.get(i).getTjshl()+cart.getTjshl());
							 //合计=价格*数量
							 list_cart.get(i).setHj(list_cart.get(i).getSku_jg()*list_cart.get(i).getTjshl());
						 }
					 }
				 }
			}
			 Cookie cookie = new Cookie("list_cart_cookie",MyJsonUtil.list_to_json(list_cart));
				//设置过期时间
				cookie.setMaxAge(60*60*24);
				response.addCookie(cookie);
		}else {
			//用户已登入
			//session就是购物车集合
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
			//判断db是否为空
			if(list_cart == null||list_cart.size()==0) {
				//添加db
				shoppingCartService.add_cart(cart);
				//同步session
				list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
				list_cart.add(cart);
				session.setAttribute("list_cart_session", list_cart);
			}else {
				//判断集合中是否有重复的元素
				boolean b=if_new_cart(list_cart,cart);
				if(b) {
					//添加
					shoppingCartService.add_cart(cart);
					//同步session
					list_cart.add(cart);
				}else {
					//更新
					//同步
					for(int i=0;i<list_cart.size();i++) {
						if(list_cart.get(i).getSku_id()==cart.getSku_id()) {
							list_cart.get(i).setTjshl(list_cart.get(i).getTjshl()+1);
							list_cart.get(i).setHj(list_cart.get(i).getSku_jg()*list_cart.get(i).getTjshl());
							shoppingCartService.update_cart(list_cart.get(i));
						}
					}
				}
			}
		}
		return "redirect:/cart_success.do";
	}
	private Boolean if_new_cart(List<T_MALL_SHOPPINGCAR> list_cart, T_MALL_SHOPPINGCAR cart) {
		boolean b = true;
		//循环集合看看是否有该对象 ，如果有就返回false
		for(int i=0;i<list_cart.size();i++) {
			if(cart.getSku_id()==list_cart.get(i).getSku_id()) {
				b = false;
			}
		}
		return b;
	}
	@RequestMapping("cart_success")
	public String cart_success(ModelMap map) {
		return "sale_cart_success";
	}
	
	
	
	
}
