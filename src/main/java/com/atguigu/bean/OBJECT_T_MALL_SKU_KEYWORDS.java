package com.atguigu.bean;

import org.apache.solr.client.solrj.beans.Field;

public class OBJECT_T_MALL_SKU_KEYWORDS extends T_MALL_SKU {

	@Field("shp_tp")
	private String shp_tp;

	public String getShp_tp() {
		return shp_tp;
	}

	public void setShp_tp(String shp_tp) {
		this.shp_tp = shp_tp;
	}

}
