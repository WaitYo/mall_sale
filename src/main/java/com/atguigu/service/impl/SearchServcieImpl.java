package com.atguigu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.OBJECT_T_MALL_SKU_CLASS;
import com.atguigu.bean.OBJECT_T_MALL_SKU_DETAIL;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.mapper.SearchMapper;
import com.atguigu.service.SearchServcie;
@Service
public class SearchServcieImpl implements SearchServcie {

	@Autowired
	private SearchMapper searchMapper;
	
	@Override
	public List<OBJECT_T_MALL_SKU_CLASS> get_sku_by_class(int class_2_id) {
		// TODO Auto-generated method stub
		return searchMapper.select_sku_by_class(class_2_id);
	}

	@Override
	public List<OBJECT_T_MALL_SKU_CLASS> attr_search(int class_2_id, List<T_MALL_SKU_ATTR_VALUE> list_av) {
		/*List<OBJECT_T_MALL_SKU_CLASS> list_sku = new ArrayList<OBJECT_T_MALL_SKU_CLASS>();
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		map.put("class_2_id", class_2_id);
		//根据条件拼接属性交叉
		if(list_av!=null&&list_av.size()>0){
			StringBuffer sql = new StringBuffer();
			sql.append("and sku.id in");
			sql.append("(select sku0.sku_id from");
			//根据属性进行拼接
			for(int i=0;i<list_av.size();i++) {
				T_MALL_SKU_ATTR_VALUE av = list_av.get(i);
				//查询库存属性值表
				sql.append("(select sku_id from t_mall_sku_attr_value where shxm_id ="
						+av.getShxm_id()+"and shxzh_id="+av.getShxzh_id()+") sku"+i+"");
				if(list_av.size()>1&&i<(list_av.size()-1)) {
					sql.append(",");
				}
				if(list_av.size()>1) {
					sql.append("where");
				}
				for(int j=0;j<list_av.size();j++) {
					if(list_av.size()>1&&j<(list_av.size()-1)) {
						sql.append("sku"+j+".sku_id=sku"+(j+1)+".sku_id");
						if(list_av.size()>2&&j<(list_av.size()-2)) {
							sql.append("and");
						}
					}
				}
				
			}
			sql.append(")");
			map.put("sql", sql.toString());
			
		}
		return 	searchMapper.select_sku_by_attr(map);
		
	}*/
		List<OBJECT_T_MALL_SKU_CLASS> list_sku = new ArrayList<OBJECT_T_MALL_SKU_CLASS>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("class_2_id", class_2_id);

		// 根据条件拼接属性交叉 的sql
		if (list_av != null && list_av.size() > 0) {
			StringBuffer sql = new StringBuffer();
			sql.append(" and sku.id in ");
			sql.append(" (select sku0.sku_id from ");

			for (int i = 0; i < list_av.size(); i++) {
				T_MALL_SKU_ATTR_VALUE av = list_av.get(i);
				sql.append(" (select sku_id from t_mall_sku_attr_value where shxm_id =" + av.getShxm_id()+ " and shxzh_id = " + av.getShxzh_id() + ") sku" + i + " ");
				if (list_av.size() > 1 && i < (list_av.size() - 1)) {
					sql.append(" , ");
				}
			}
			if (list_av.size() > 1) {
				sql.append(" where ");
			}

			for (int i = 0; i < list_av.size(); i++) {
				if (list_av.size() > 1 && i < (list_av.size() - 1)) {
					sql.append(" sku" + i + ".sku_id=sku" + (i + 1) + ".sku_id ");
					if (list_av.size() > 2 && i < (list_av.size() - 2)) {
						sql.append(" and ");
					}
				}
			}

			sql.append(" ) ");

			map.put("sql", sql.toString());
		}

		list_sku = searchMapper.select_sku_by_attr(map);
		return list_sku;
	}

	@Override
	public OBJECT_T_MALL_SKU_DETAIL get_sku_detail(int spu_id, int sku_id) {
		
		return searchMapper.select_sku_detail(spu_id,sku_id);
	}

	@Override
	public List<T_MALL_SKU> get_sku_list_by_spu_id(int spu_id) {
		// TODO Auto-generated method stub
		return searchMapper.select_sku_list_by_spu_id(spu_id);
	}

}
