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
$(function (){
	$.getJSON("js/json/class_1.js",function(data){
		// 用js循环json的分类集合
		$(data).each(function(i,json){
			// 将分类集合的内容生成<option>对象加载到下拉列表中
			$("#class_1_select_select").append("<li onmouseover='get_class_2_select(this.value)' value="+json.id+"><a href=''>"+json.flmch1+"</a></li>");
		});
	});
});

function get_class_2_select(class_1_id){
	// $("#class_1_select option:selected").val();
	// $("#class_1_select").val();
	$.getJSON("js/json/class_2_"+class_1_id+".js",function(data){
		// 用js循环json的分类集合
		$("#class_2_select_select").empty();
		$(data).each(function(i,json){
			// 将分类集合的内容生成<option>对象加载到下拉列表中
			$("#class_2_select_select").append
			("<li value="+json.id+"><a href='goto_class_search.do?class_2_id="+json.id+"&class_2_name="+json.flmch2+"' target='_blank'>"+json.flmch2+"</a></li>");
		});
	});

}

</script>
<title>硅谷商城</title>
</head>
<body>
<div class="menu">
		<div class="nav">
			<div class="navs">
				<div class="left_nav">
					全部商品分类
					<div class="nav_mini">
						<ul name="flbh1" id="class_1_select_select">
							<li>
								<a href="">家用电器</a>
								<div class="two_nav" name="flbh2" id="class_2_select_select">
									
									
								</div>
							</li>
							<li><a href="">手机、数码、通信</a></li>
							<li><a href="">电脑、办公</a></li>
							<li><a href="">家具、家居、家装</a></li>
							<li><a href="">男装、女装、内衣</a></li>
							<li><a href="">个户化妆</a></li>
							<li><a href="">鞋靴</a></li>
							
						</ul>
					</div>
				</div>
				
			</div>
		</div>
	</div>









<!-- <ul style="float:left;margin-left:10px;" name="flbh1" id="class_1_select_select" >
</ul>
<ul style="float:left;" name="flbh2" id="class_2_select_select" >
</ul> -->
</body>
</html>