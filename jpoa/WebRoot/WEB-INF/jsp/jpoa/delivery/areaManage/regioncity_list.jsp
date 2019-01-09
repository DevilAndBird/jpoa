<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<meta charset="utf-8" />
	<meta name="description" content="overview & stats" /> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	
    <link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="static/css/chosen.css"/>
	
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" /> 
	
	<link rel="stylesheet" href="static/css/datepicker.css" /> 
	
	<script type="text/javascript" src="static/js/myjs/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/js/myjs/jquery.tips.js"></script>
	</head>
<body>
	<div class="container-fluid" id="main-container">
	 <div id="page-content" class="clearfix">
	   <div class="row-fluid">
			<form action="regionManage/regioncityList" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td style="vertical-align:top;"> 
						 	<select name="provid" id="seleProv" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id }" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name }</option>
								</c:forEach>
						  	</select>
						</td>
						<td style="vertical-align:top;"> 
						 	<select name="cityid" id="seleCity" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
						  	</select>
						</td>
					    <td style="vertical-align:top;">
					    	<button class="btn btn-mini btn-light" title="检 索">
					    		<i id="nav-search-icon" class="icon-search">检 索</i>
					    	</button>
					    </td>
					    
					    <td style="vertical-align:top;">
					    	&nbsp;&nbsp;&nbsp;&nbsp;
					    	<button class="btn btn-mini btn-light" title="工作区域添加" onclick="addRegionCity()" >
						   	 	<i id="nav-search-icon" class="icon-download-alt">工作区域添加</i>
						    </button>
					    </td>
					    <!-- <td style="vertical-align:top;">
					   		<button class="btn btn-mini btn-light" title="工作区域编辑" onclick="editRegionCity()" >
						   	 	<i id="nav-search-icon" class="icon-download-alt">工作区域编辑</i>
						   	 </button>
					    </td> -->
					    <td style="vertical-align:top;">
					   		 <button class="btn btn-mini btn-light" title="工作区域查看" onclick="viewRegionCity()">
						   	 	<i id="nav-search-icon" class="icon-download-alt">工作区域查看</i>
						     </button>
					    </td>
					</tr>
				</table>
			
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">序号</th>
							<th class="center">省</th>
							<th class="center">市</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty regionCityList}">
							<c:forEach items="${regionCityList}" var="regionCity" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
									<td class="center">${regionCity.provDesc}</td>
									<td class="center">${regionCity.cityDesc}</td>
								    <td class="center">
								    	<button title="编辑工作区域" class="btn btn-link" onclick="editRegionCity(this);" value="${regionCity.id}" >编辑</button>
								    </td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="100" class="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
				
				<div class="page-header position-relative">
					<table style="width:100%;">
						<tr>
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	  </div>
	</div>
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
		<script type="text/javascript">
		    $(top.hangge());
			$(function() {
				//下拉框
				$(".chzn-select").chosen(); 
				/* 检索 */
				function search(){
					top.jzts();
					$("#Form").submit();
				}
				/* 省市联动 */
				$("#seleProv").change( function() {
					provCityLinkage($(this).val());
				});
			});
			
			/* 省市联动 */
			function provCityLinkage(param){
                $.ajax({
					url : "./city/findCityByProvid",
					data : {
						provid : param
					},
					dataType : "json",
					type : "POST",
					success : function(data) {
						$("#seleCity").empty();
						$("#seleCity").append("<option value='-1' selected='selected'>--城市--</option>");
						var json = eval(data);
						$.each(json, function (i, item) {
							$("#seleCity").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
			            });
					}
				});	 
			}
			
			/* 添加工作区域 */
			function addRegionCity() {
		    	 var url = "<%=basePath%>regionManage/addregioncity.do";
		    	 $("#Form").attr("action", url);
		    	 $("#Form").submit();
			}
			
			<%-- /* 编辑工作区域 */
			function editRegionCity() {
		    	 var url = "<%=basePath%>regionManage/demo.do";
		    	 $("#Form").attr("action", url);
		    	 $("#Form").submit();
			} --%>
			
			/* 查看工作区域 */
			function viewRegionCity() {
		    	 var url = "<%=basePath%>regionManage/viewBaiduMap.do";
		    	 $("#Form").attr("action", url);
		    	 $("#Form").submit();
			}
			
			/* 编辑市级区域 */
			function editRegionCity(param) {
				var id = $(param).val();
				var url = "<%=basePath%>regionManage/editRegionCity.do?id=" + id;
		    	$("#Form").attr("action", url);
		    	$("#Form").submit();
			}
			
		</script>
	</body>
</html>

