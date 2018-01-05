package com.atguigu.util;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class MyPasswordCallback implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		// 令牌信息加密过程
		WSPasswordCallback ws = (WSPasswordCallback) callbacks[0];

		String identifier = ws.getIdentifier();

		String password = ws.getPassword();

		ws.setPassword("123");

	}

}
