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
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function b(){}
</script>
<title>硅谷商城</title>
</head>
<body>


<hr>
<jsp:include page="sale_search_area.jsp"></jsp:include>
<%-- <jsp:include page="sale_header.jsp"></jsp:include> --%>
<h1>商城首页</h1>
	商品内容列表<br>
	<c:forEach items="${list_sku}" var="sku">
		<div style="border:red 1px solid;height:300px;
		width:280px;margin-left:10px;margin-top:10px;float:left;">
			<img src="upload/image/${sku.spu.shp_tp}" width="280px" height="150px"><br>
			<a href="sku_detail.do?sku_id=${sku.id }&spu_id=${sku.shp_id}">${sku.sku_mch}</a>
			<br>
			${sku.spu.shp_mch}<br>
			${sku.jg}<br>
			${sku.sku_xl}<br>
			${sku.kc}<br>
		</div>
	</c:forEach>
	
	
	
	
	<%-- <div class="Sscreen">
		<div class="title">
			平板电视 商品筛选 共1205个商品
		</div>
		<c:forEach items="${list_sku}" var="sku">
		<div class="list">
			<span>品牌：</span>
			<a href="">${sku.spu.shp_mch}</a>
			
		</div>
		<!-- <div class="list">
			<span>价格：</span>
			<a href="">0-1399</a>
			<a href="">0-1399</a>
			<a href="">0-1399</a>
			<a href="">0-1399</a>
			<a href="">0-1399</a>
			<a href="">0-1399</a>
			<a href="">0-1399</a>
			<a href="">0-1399</a>
			<a href="">0-1399</a>
		</div> -->
		<div class="list">
			<span class="list_span" id="list_beas">${sku.sku_xl}</span>
			<span class="list_span">${sku.jg}</span>
			<span class="list_span">评论数</span>
			<span class="list_span">上架时间</span>
		</div>
		<div class="Sbox">
		<div class="list">
			<div class="img"><img src="upload/image/${sku.spu.shp_tp}" alt=""></div>
			<div class="price">¥${sku.jg}</div>
			<div class="title">${sku.spu.shp_mch}</div>
		</div>
		</div>
		</c:forEach>
	</div>
	
	
	
	
	 --%>
	
	
	
	
	
	
	
</body>
</html>