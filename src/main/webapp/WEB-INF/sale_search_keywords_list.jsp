<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function b(){}
</script>
<title>硅谷商城</title>
</head>
<body>
	商品内容搜索：${keywords}<br>
	<c:forEach items="${list_sku}" var="sku">
		<div style="border:red 1px solid;height:300px;width:280px;margin-left:10px;margin-top:10px;float:left;">
			<img src="upload/image/${sku.shp_tp}" width="280px" height="150px"><br>
			<a href="sku_detail.do?sku_id=${sku.id}&spu_id=${sku.shp_id}">${sku.sku_mch}</a><br>
			${sku.sku_mch}<br>
			${sku.jg}<br>
			${sku.sku_xl}<br>
			${sku.kc}<br>
		</div>
	</c:forEach>
</body>
</html>