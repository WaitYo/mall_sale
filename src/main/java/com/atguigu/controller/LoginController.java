package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.mapper.LoginMapper;
import com.atguigu.server.UserServerInf;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.util.MyJsonUtil;

@Controller
public class LoginController {
	@Autowired
	LoginMapper loginMapper;
	@Autowired
	UserServerInf userServerInf;
	@Autowired
	private ShoppingCartService shoppingCartService;
	//登入
   @RequestMapping("login")
   public String login(String dataSource_type,@CookieValue(value="list_cart_cookie",required=false)String list_cart_cookie,HttpSession session,HttpServletResponse response,T_MALL_USER_ACCOUNT user) {
	   /* UserServerInf userServerInf=null;
	   JaxWsProxyFactoryBean jax = new JaxWsProxyFactoryBean();
	   jax.setAddress("http://localhost:8081/mall_user/user?wsdl");
	   jax.setServiceClass(UserServerInf.class);
	   userServerInf=(UserServerInf) jax.create();*/
	   //认证用户的登录信息
	   T_MALL_USER_ACCOUNT login = null;
	   //从远程调用ws的用户认证程序
	   try {
		   if(dataSource_type.equals("d1")) {
			   login = userServerInf.login(user);
		   }else if(dataSource_type.equals("d2")){
			   login = userServerInf.login_test(user); 
		   }
		
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.getMessage();
		return "redirect:/goto_login.do";
	}
	   if(login==null) {
		   return "redirect:/goto_login.do";
	   }else {
		   session.setAttribute("user", login);
		 //如果用户认证成功，向cookie中保存一些用户的个性信息
		   Cookie cookie=null;
		try {
			cookie = new Cookie("yh_nch",URLEncoder.encode(login.getYh_nch(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   //设置cookie的时间
		   cookie.setMaxAge(60*60*24);
		   response.addCookie(cookie);
		   
		   //合并购物车
		// 合并购物车
			List<T_MALL_SHOPPINGCAR> list_cart_db = shoppingCartService.get_list_by_user_id(login);
			List<T_MALL_SHOPPINGCAR> list_cart_cookie_cookie = MyJsonUtil.json_to_list(list_cart_cookie,
							T_MALL_SHOPPINGCAR.class);
			combine_cart(list_cart_db, list_cart_cookie_cookie, response, session);
		   return "redirect:/index.do";
	   }
	  
   }
  /* private void combine_cart(List<T_MALL_SHOPPINGCAR> list_cart_db, List<T_MALL_SHOPPINGCAR> list_cart_cookie,
			HttpServletResponse response, HttpSession session) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		if (list_cart_db == null || list_cart_db.size() == 0) {
			// 数据库中没有数据，插入购物车数据
			if (list_cart_cookie != null && list_cart_cookie.size() > 0) {

				for (int i = 0; i < list_cart_cookie.size(); i++) {
					shoppingCartService.add_cart(list_cart_cookie.get(i));
				}
			}

		} else {
			// 数据库中有数据
			if (list_cart_cookie != null && list_cart_cookie.size() > 0) {
				// cookie中有数据
				for (int i = 0; i < list_cart_cookie.size(); i++) {
					boolean b = if_new_cart(list_cart_db, list_cart_cookie.get(i));
					if (b) {
						list_cart_cookie.get(i).setYh_id(user.getId());
						shoppingCartService.add_cart(list_cart_cookie.get(i));
					} else {

						T_MALL_SHOPPINGCAR cart = new T_MALL_SHOPPINGCAR();
						for (int j = 0; j < list_cart_db.size(); j++) {
							if (list_cart_db.get(j).getSku_id() == list_cart_cookie.get(i).getSku_id()) {
								cart = list_cart_db.get(j);
							}
						}
						list_cart_cookie.get(i).setYh_id(cart.getSku_id());
						list_cart_cookie.get(i).setId(cart.getId());
						list_cart_cookie.get(i).setTjshl(cart.getTjshl() + list_cart_cookie.get(i).getTjshl());
						list_cart_cookie.get(i)
								.setHj(list_cart_cookie.get(i).getSku_jg() * list_cart_cookie.get(i).getTjshl());
						shoppingCartService.update_cart(list_cart_cookie.get(i));
					}
				}
			}
		}

		// 清空cookie中的购物车数据，同步session
		response.addCookie(new Cookie("list_cart_cookie", ""));
		session.setAttribute("list_cart_session", shoppingCartService.get_list_by_user_id(user));

	}*/
   private void combine_cart(List<T_MALL_SHOPPINGCAR> list_cart_db, List<T_MALL_SHOPPINGCAR> list_cart_cookie,
			HttpServletResponse response, HttpSession session) {
	   T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
	   //如果数据库中没有数据，插入购物车数据
    if(list_cart_db ==null||list_cart_db.size()==0) {
    	//如果cookie有数据就将cookie购物车存入数据库
    	if(list_cart_cookie!=null&&list_cart_cookie.size()>0) {
    		for(int i=0;i<list_cart_cookie.size();i++) {
    			shoppingCartService.add_cart(list_cart_cookie.get(i));
    		}
    	}
    }else {
    	//数据库中有数据
    if(list_cart_cookie!=null&&list_cart_cookie.size()>0) {
    		//cookie中有数据不重复
    	for(int i=0;i<list_cart_cookie.size();i++) {
    		boolean b=if_new_cart(list_cart_db,list_cart_cookie.get(i));
    		if(b) {
    			list_cart_cookie.get(i).setYh_id(user.getId());
    			shoppingCartService.add_cart(list_cart_cookie.get(i));
    		
    	}else {
    		//cookie中有数据重复是
    		T_MALL_SHOPPINGCAR cart = new T_MALL_SHOPPINGCAR();
    		for(int j=0;j<list_cart_cookie.size();j++) {
    			if(list_cart_db.get(j).getSku_id()==list_cart_cookie.get(j).getSku_id()) {
    				cart=list_cart_db.get(j);
    			}
    		}
    		list_cart_cookie.get(i).setYh_id(cart.getSku_id());
    		list_cart_cookie.get(i).setId(cart.getId());
    		list_cart_cookie.get(i).setTjshl(cart.getTjshl()+list_cart_cookie.get(i).getTjshl());
    		list_cart_cookie.get(i).setHj(list_cart_cookie.get(i).getSku_jg()*list_cart_cookie.get(i).getTjshl());
    		shoppingCartService.update_cart(list_cart_cookie.get(i));
    	}
    	}
    	}
    }
    //清空cookie中的购物车数据，同步session
    response.addCookie(new Cookie("list_cart_cookie",""));
    session.setAttribute("list_cart_session", shoppingCartService.get_list_by_user_id(user));
   }
	

   private boolean if_new_cart(List<T_MALL_SHOPPINGCAR> list_cart, T_MALL_SHOPPINGCAR cart) {
		boolean b = true;
		for (int i = 0; i < list_cart.size(); i++) {
			if (cart.getSku_id() == list_cart.get(i).getSku_id()) {
				b = false;
			}
		}
		return b;
	}

//去登入
   @RequestMapping("goto_login")
   public String goto_login() {
	   return "sale_login";
   }
   //退出
   @RequestMapping("loginout")
   public String loginout(HttpSession session) {
	   session.invalidate();
	   return "redirect:/goto_login.do";
   }
   
}
