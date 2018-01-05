package com.atguigu.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.OBJECT_T_MALL_FLOW;
import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_ORDER_INFO;
import com.atguigu.exception.OverSaleException;
import com.atguigu.mapper.OrderMapper;
import com.atguigu.service.OrderService;
import com.atguigu.util.MyDateUtil;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public void save_order(BigDecimal sum, T_MALL_ADDRESS address, OBJECT_T_MALL_ORDER order) {

		// 保存订单表，返回主键
		// shhr
		// zje
		// jdh
		// yh_id
		// dzh_id
		// dzh_mch
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("order", order);
		map.put("address", address);
		orderMapper.insert_order(map);

		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for (int i = 0; i < list_flow.size(); i++) {
			// 保存物流表 ,返回主键
			// psfsh
			// psshj
			// yh_id
			// dd_id
			// mqdd
			// mdd
			list_flow.get(i).setDd_id(order.getId());
			list_flow.get(i).setMdd(address.getYh_dz());
			Map<Object, Object> map1 = new HashMap<Object, Object>();
			map1.put("dd_id", order.getId());
			map1.put("flow", list_flow.get(i));
			orderMapper.insert_flow(map1);

			// 保存订单信息表
			// 物流id-flow_id
			List<T_MALL_ORDER_INFO> list_info = list_flow.get(i).getList_info();
			Map<Object, Object> map2 = new HashMap<Object, Object>();
			map2.put("flow_id", list_flow.get(i).getId());
			map2.put("list_info", list_info);
			map2.put("dd_id", order.getId());
			orderMapper.insert_infos(map2);

			// 把已经生成订单的商品信息从购物车中删除
			List<Integer> list_gwc_id = new ArrayList<Integer>();
			for (int j = 0; j < list_info.size(); j++) {
				int gwch_id = list_info.get(j).getGwch_id();
				list_gwc_id.add(gwch_id);
			}
			orderMapper.delete_shoppingCart(list_gwc_id);
		}

	}

	/*@Override
	public void order_pay(OBJECT_T_MALL_ORDER order) throws OverSaleException {
		//支付减库存业务
		
		//修改订单状态
		order.setYjsdshj(MyDateUtil.getMyTime(3));
		orderMapper.update_order(order);
		//生成一部分物流信息
		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for(int i=0;i<list_flow.size();i++) {
			OBJECT_T_MALL_FLOW flow = list_flow.get(i);
			flow.setPsshj(MyDateUtil.getMyTime(1));
			flow.setPsmsh("商品已出库");
			flow.setYwy("miss");
			flow.setLxfsh("147258369");
			orderMapper.update_flow(flow);
			
			
			
			List<T_MALL_ORDER_INFO> list_info = list_flow.get(i).getList_info();
			for(int j=0;j<list_info.size();j++) {
				T_MALL_ORDER_INFO info = list_info.get(i);
				//修改库存信息，查询库存数量
				Integer kc = get_kc(info.getId());
				if(kc>info.getSku_shl()) {
					orderMapper.update_kc(info);
				}else {
					throw new OverSaleException("over sale");
				}
			}
		}
	}

	private Integer get_kc(int sku_id) {
		Integer select_kc = orderMapper.select_kc_for_update(sku_id);
		return select_kc;
	}*/
	@Override//事物
	// @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	// Exception.class)
	public void pay_order(OBJECT_T_MALL_ORDER order) throws OverSaleException {
		// 支付减库存业务

		// 修改订单状态
		order.setYjsdshj(MyDateUtil.getMyTime(3));
		orderMapper.update_order(order);
		// 生成一部分物流信息
		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for (int i = 0; i < list_flow.size(); i++) {
			OBJECT_T_MALL_FLOW flow = list_flow.get(i);
			flow.setPsshj(MyDateUtil.getMyTime(1));
			flow.setPsmsh("商品已出库");
			flow.setYwy("老佟");
			flow.setLxfsh("123123123123");
			orderMapper.update_flow(flow);

			List<T_MALL_ORDER_INFO> list_info = list_flow.get(i).getList_info();

			for (int j = 0; j < list_info.size(); j++) {
				T_MALL_ORDER_INFO info = list_info.get(j);
				// 修改库存信息,查询库存数量
				long kc = get_kc(info.getSku_id());

				if (kc >= info.getSku_shl()) {
					// 库存数量大于购买数量，减少库存
					orderMapper.update_kc(info);
				} else {
					throw new OverSaleException("over sale");
				}

			}

		}

	}

	private long get_kc(int sku_id) {

		// select count(1) from t_mall_sku where id = sku_id and kc > 30

		long select_kc = orderMapper.select_kc_for_update(sku_id);
		return select_kc;
	}
}
