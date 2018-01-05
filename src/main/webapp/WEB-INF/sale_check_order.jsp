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
function select_address(address_id,shjr){
	$("#check_order_div").html("收货地址：<input type='hidden' value='"+address_id+"' name='address_id'>"+shjr);
}
</script>
<title>硅谷商城</title>
</head>
<body>
<jsp:include page="sale_search_area.jsp"></jsp:include>
<div class="message">
			<div class="msg_title">
				收货人信息
			</div>
			<c:forEach items="${list_address}" var="address">

				<div class="msg_addr">
					<span class="msg_left">
						 ${address.shjr} ${address.lxfsh}
					</span>
					<span class="msg_right">
						
							<input type="radio" 
							onclick="select_address(${address.id},'${address.shjr}${address.yh_dz}')" 
							name="a" />${address.yh_dz}
						
					</span>
				</div>
			</c:forEach>
			<span class="addrs">查看更多地址信息</span>
			<div class="msg_line"></div>
		
			<div class="msg_title">
				送货清单
			</div>
			
			<c:forEach items="${order.list_flow}" var="flow" varStatus="index">
					<div style="border:red 1px solid;margin-top:10px;">
						配送方式${flow.psfsh}${flow.mqdd}：${index.count}<br>
						<c:forEach items="${flow.list_info}" var="info">
									<img src="upload/image/${info.shp_tp}" width="100px" width="100px"/>
									${info.sku_mch} 
									￥${info.sku_jg}
									<br>
						</c:forEach>	
					</div>
			</c:forEach>
				
			<div class="msg_line"></div>
			<div class="msg_sub">
				<form action="save_order.do" method="post">
					<input type="hidden" value="${order.zje}" name="sum"/>
					<div class="msg_sub_tit">
						应付金额：
						<b>￥${order.zje}</b>
					</div>
					<div class="msg_sub_adds"  id="check_order_div">
						寄送至 ： 北京市 昌平区 北七家镇 尚硅谷IT教育    &nbsp;某某某（收）  185****1222
					</div>
					<button class="msg_btn" style="cursor:pointer;">提交订单</button>
				</form>
			</div>
		</div>
	
</body>
</html>