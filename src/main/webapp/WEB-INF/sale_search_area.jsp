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

</script>
<title>硅谷商城</title>
</head>
<body>

<div class="search">
		<div class="logo"><img src="images/logo.jpg" alt=""></div>
		<div class="search_on">
			<div class="se">
			<form action="search_keywords.do" method="post">
				<input type="text" name="search" class="lf">
				<input type="submit" class="clik" value="搜索">
				</form>
			</div>
			<div class="se">
				<a href="">取暖神奇</a>
				<a href="">1元秒杀</a>
				<a href="">吹风机</a>
				<a href="">玉兰油</a>
			</div>
		</div>
	
		<jsp:include page="sale_miniCart.jsp"></jsp:include>
	</div>



<%-- 搜索区域<input type="text" size="50" />
<input type="button"  value="搜索"/>
<jsp:include page="sale_miniCart.jsp"></jsp:include> --%>
</body>
</html>