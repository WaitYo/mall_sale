package com.atguigu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.mapper.CartMapper;
import com.atguigu.service.ShoppingCartService;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
@Autowired
private CartMapper cartMapper;

	@Override
	public void add_cart(T_MALL_SHOPPINGCAR cart) {
		cartMapper.insert_cart(cart);

	}

	@Override
	public void update_cart(T_MALL_SHOPPINGCAR t_MALL_SHOPPINGCAR) {
		cartMapper.update_cart(t_MALL_SHOPPINGCAR);

	}

	@Override
	public List<T_MALL_SHOPPINGCAR> get_list_by_user_id(T_MALL_USER_ACCOUNT login) {
		// TODO Auto-generated method stub
		return cartMapper.select_list_by_user_id(login);
	}

}
