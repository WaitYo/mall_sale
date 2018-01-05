package com.atguigu.service;

import java.math.BigDecimal;

import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.exception.OverSaleException;

public interface OrderService {

	public void save_order(BigDecimal sum, T_MALL_ADDRESS address, OBJECT_T_MALL_ORDER order);

	public void pay_order(OBJECT_T_MALL_ORDER order) throws OverSaleException;

}
