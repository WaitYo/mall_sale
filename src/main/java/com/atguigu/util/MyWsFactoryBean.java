package com.atguigu.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.springframework.beans.factory.FactoryBean;

public class MyWsFactoryBean<T> implements FactoryBean<T> {

	

	private String url;
	private Class<T> t;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}
	public static <T> T getMyWs(String url,Class<T> t) {
		JaxWsProxyFactoryBean jax = new JaxWsProxyFactoryBean();
		jax.setAddress(url);
		jax.setServiceClass(t);
		if (t.getSimpleName().equals("TestServerInf")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("action", "UsernameToken");
			map.put("passwordType", "PasswordText");
			map.put("passwordCallbackClass", MyPasswordCallback.class.getName());
			map.put("user", "username");
			WSS4JOutInterceptor wss4jOutInterceptor = new WSS4JOutInterceptor(map);
			jax.getOutInterceptors().add(wss4jOutInterceptor);
		}
		T ws=(T)jax.create();
		return ws;
	}

	@Override
	public T getObject() throws Exception {
		// TODO Auto-generated method stub
		return getMyWs(this.url,this.t);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return this.t;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

}
