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
function change_status(checked,sku_id){
	var shfxz="0";
	if(checked){
		shfxz="1";
	}
	$.post("change_status.do",{shfxz:shfxz,sku_id:sku_id},function(data){
		$("#cart_list_inner").html(data);
	})
}
</script>
<title>硅谷商城</title>
</head>
<body>
<div id="cart_list_inner">
<jsp:include page="sale_cart_list_inner.jsp"></jsp:include>
<form action="goto_check_order.do" method="post">
<input type="submit" value="结算"/>

</form>
</div>
</body>
</html>