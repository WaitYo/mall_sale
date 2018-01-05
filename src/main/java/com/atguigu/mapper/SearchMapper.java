package com.atguigu.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.bean.OBJECT_T_MALL_SKU_CLASS;
import com.atguigu.bean.OBJECT_T_MALL_SKU_DETAIL;
import com.atguigu.bean.T_MALL_SKU;

public interface SearchMapper {

	List<OBJECT_T_MALL_SKU_CLASS> select_sku_by_class(int class_2_id);

	List<OBJECT_T_MALL_SKU_CLASS> select_sku_by_attr(HashMap<Object, Object> map);

	OBJECT_T_MALL_SKU_DETAIL select_sku_detail(@Param("spu_id") int spu_id,@Param("sku_id") int sku_id);

	List<T_MALL_SKU> select_sku_list_by_spu_id(int spu_id);

}
