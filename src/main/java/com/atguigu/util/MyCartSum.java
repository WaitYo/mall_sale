package com.atguigu.util;

import java.math.BigDecimal;
import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;

//总金额
public class MyCartSum {
   public static BigDecimal getMySum(List<T_MALL_SHOPPINGCAR> list_cart) {
	   BigDecimal sum = new BigDecimal("0");
	   //判断购物车里的商品是否被选中
	   
	   for(int i=0;i<list_cart.size();i++) {
		   if(list_cart.get(i).getShfxz().equals("1"));
		   //如果被选中就添加到总金额中
		   sum=sum.add(new BigDecimal(list_cart.get(i).getHj()+""));
	   }
	   return sum;
   }
}
