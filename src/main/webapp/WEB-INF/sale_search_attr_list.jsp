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
	function search_attr_up(attr_id, value_id, shxmch) {
		// 增加属性参数
		var a = " <span id='search_shxmch_"+attr_id+"' style='cursor:pointer'>";
		var b = "<input type='hidden' name='attr_param' value='{\"shxm_id\":"+attr_id+",\"shxzh_id\":"+value_id+"}' />";//14_15
		var c = "<a href='javascript:search_attr_down("+attr_id+");'>"+shxmch+"</a>";
		var d = "</span>";
		$("#search_param").append(a+b+c+d);
		$("#search_id_"+attr_id).hide();
		
		// 调用根据属性检索的业务
		attr_search();
	}
	
	function search_attr_down(attr_id) {
		// 减少属性参数
		$("#search_id_"+attr_id).show();
		$("#search_shxmch_"+attr_id).remove();
	}
	//去参数
	function attr_search(){
		class_2_id = ${class_2_id};

		var param = "class_2_id="+class_2_id;
		//选择属性是param的所有input域
		 $(":input[name='attr_param']").each(function(i,json){
			var obj = $.parseJSON(json.value);
			param = param + "&list_av["+i+"].shxm_id="
			+obj.shxm_id +"&list_av["+i+"].shxzh_id="
			+obj.shxzh_id;
		}); 
        // 遍历商品的信息//ajax
 		$.get("attr_search.do",param,function(data){
 			//刷新内嵌页
			$("#search_sku_list").html(data);
		});
 
	}

</script>
<title>硅谷商城</title>
</head>
<body>
	商品属性列表:<div id = "search_param"></div><br>
	<c:forEach items="${list_attr}" var="attr">
		<div id = "search_id_${attr.id}">
			${attr.shxm_mch}：
			<c:forEach items="${attr.list_value}" var="val">
				<a href="javascript:search_attr_up(${attr.id},${val.id},'${val.shxzh}${val.shxzh_mch}');">${val.shxzh}${val.shxzh_mch}</a>
			</c:forEach>
		</div>
	</c:forEach>
</body>
</html>