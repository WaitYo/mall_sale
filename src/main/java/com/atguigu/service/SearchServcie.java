package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.OBJECT_T_MALL_SKU_CLASS;
import com.atguigu.bean.OBJECT_T_MALL_SKU_DETAIL;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;

public interface SearchServcie {

	List<OBJECT_T_MALL_SKU_CLASS> get_sku_by_class(int class_2_id);

	List<OBJECT_T_MALL_SKU_CLASS> attr_search(int class_2_id, List<T_MALL_SKU_ATTR_VALUE> list_av);

	OBJECT_T_MALL_SKU_DETAIL get_sku_detail(int spu_id, int sku_id);

	List<T_MALL_SKU> get_sku_list_by_spu_id(int spu_id);

}
