package com.atguigu.bean;

import java.util.List;
//T_MALL_FLOW物流信息表
public class OBJECT_T_MALL_FLOW extends T_MALL_FLOW {
	//订单信息表
	private List<T_MALL_ORDER_INFO> list_info;

	public List<T_MALL_ORDER_INFO> getList_info() {
		return list_info;
	}

	public void setList_info(List<T_MALL_ORDER_INFO> list_info) {
		this.list_info = list_info;
	}
}
