package com.atguigu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.atguigu.bean.OBJECT_T_MALL_FLOW;
import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_FLOW;
import com.atguigu.bean.T_MALL_ORDER_INFO;
import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.exception.OverSaleException;
import com.atguigu.server.AddressServerInf;
import com.atguigu.service.OrderService;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.util.MyCartSum;
@Controller
@SessionAttributes("order")
public class OrderController {
	@Autowired
	private AddressServerInf addressServerInf;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private OrderService orderService;
	@RequestMapping("order_fail")
	public String order_fail(@ModelAttribute("order") OBJECT_T_MALL_ORDER order,BigDecimal sum) {
		return "sale_fail";
	}
	
	@RequestMapping("order_success")
	public String order_success(@ModelAttribute("order") OBJECT_T_MALL_ORDER order,BigDecimal sum) {
		return "sale_success";
	}
	@RequestMapping("order_pay")
	public String order_pay(@ModelAttribute("order") OBJECT_T_MALL_ORDER order, BigDecimal sum) {
		//支付改库存业务
			try {
				orderService.pay_order(order);;
			} catch (OverSaleException e) {
				return "redirect:/order_fail.do";
			}
		return "redirect:/order_success.do";
	}
	
	
	@RequestMapping("goto_cashier")
	public String goto_cashier(@ModelAttribute("order") OBJECT_T_MALL_ORDER order, BigDecimal sum) {

		return "sale_cashier";
	}
	
	
	@RequestMapping("save_order")
	public String save_order(Integer address_id, HttpSession session, @ModelAttribute("order") OBJECT_T_MALL_ORDER order,
			BigDecimal sum) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");

		//System.out.println("111"+addressServerInf.get_addresses_by_id(address_id));
		// 保存订单对象
		orderService.save_order(sum, addressServerInf.get_addresses_by_id(address_id), order);
		// 重新同步session
		
		session.setAttribute("list_cart_session", shoppingCartService.get_list_by_user_id(user));
		return "redirect:/goto_cashier.do";
	}
	
    //去结算+拆单
	@RequestMapping("goto_check_order")
	public String goto_check_order(HttpSession session,ModelMap map) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		//拆单业务
		OBJECT_T_MALL_ORDER order = new OBJECT_T_MALL_ORDER();
		order.setJdh(0);
		order.setYh_id(user.getId());
		order.setZje(MyCartSum.getMySum(list_cart));
		//1、获得库存地址信息
		//用set,set是无序不可重复的
		Set<String> list_kcdz=new HashSet<String>();
		for(int i=0;i<list_cart.size();i++) {
			//判断商品是否被选中
			if(list_cart.get(i).getShfxz().equals("1")) {
				//如果被选中
				list_kcdz.add(list_cart.get(i).getKcdz());
			}
		}
		//2、根据库存地址生成物流包裹
		List<OBJECT_T_MALL_FLOW> list_flow = new ArrayList<OBJECT_T_MALL_FLOW>();
		//遍历库存地址
		Iterator<String> iterator = list_kcdz.iterator();
		while(iterator.hasNext()) {
			//String kcdz = iterator.next();
			//生成新的物流信息
			String kcdz = iterator.next();
			OBJECT_T_MALL_FLOW flow = new OBJECT_T_MALL_FLOW();
			flow.setPsfsh("硅谷快递");
			flow.setMqdd("商品等待出库");
			flow.setYh_id(user.getId());
			//3、将选中的商品放入物流包裹
			List<T_MALL_ORDER_INFO> list_info=new ArrayList<T_MALL_ORDER_INFO>();
			for (int i = 0; i < list_cart.size(); i++) {
				if ("1".equals(list_cart.get(i).getShfxz()) && list_cart.get(i).getKcdz().equals(kcdz) ) {
					T_MALL_SHOPPINGCAR cart=list_cart.get(i);
					T_MALL_ORDER_INFO info = new T_MALL_ORDER_INFO();
					info.setGwch_id(cart.getId());
					info.setShp_tp(cart.getShp_tp());
					info.setSku_id(cart.getSku_id());
					info.setSku_jg(cart.getSku_jg());
					info.setSku_kcdz(kcdz);
					info.setSku_mch(cart.getSku_mch());
					info.setSku_shl(cart.getTjshl());
					list_info.add(info);
				}
			}
			flow.setList_info(list_info);
			list_flow.add(flow);
		}
		
		order.setList_flow(list_flow);
		map.put("order", order);
		map.put("list_address", addressServerInf.get_addresses_by_user_id(user));
		map.put("sum", MyCartSum.getMySum(list_cart));
		return "sale_check_order";
	}
	
}
