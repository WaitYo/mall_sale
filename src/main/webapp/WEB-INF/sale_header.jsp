<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	/* $(function(){
		var cookie=document.cookie;
		$("#xxx").html(cookie);
	}); */
	$(function(){
		var yh_nch = get_cookie_value("yh_nch");
		$("#xxx").html(yh_nch);
	});
	function get_cookie_value(key){
		//浏览器对cookie字符串的处理
		var value="";
		var cookie=document.cookie;
		//把所有的空格换成字符串
		cookie=cookie.replace(/\s/,"");
		//按照分号分割
		cookie_array=cookie.split(";");
		for(i=0;i<cookie_array.length;i++){
			//alert(cookie_array[i]);
			var cookie=cookie_array[i].aplit("=");
			if(cookie[0]==key){
				value=cookie[1];
			}
		}
		//解码decodeURLcomponent
		var return_value=decodeURLcomponent(value);
		return return_value;
	}
</script>
<title>硅谷商城</title>
</head>
<body>
<div class="top">
		<div class="top_text">
			<c:if test="${empty user }">
			<a href="javascript:;">
	<span id="XXX" color="red">XXX</span>
	</a>
	<a href="goto_login.do">请登录</a> 
	<a href="javascript:">注册</a>
	</c:if>

	<c:if test="${not empty user}">
		<a href="javascript:;">欢迎${user.yh_nch }</a>
		<a href="javascript:;">我的订单</a>
		
		<a href="loginout.do">退出登录</a>
	</c:if>
		</div>
	</div>

	导航栏
	
</body>
</html>