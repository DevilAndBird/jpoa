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
	
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
	<link rel="stylesheet" href="static/css/bootstrap.min.css"  />
	<link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css" />
	
	</head>
<body>
	<div class="container-fluid" id="main-container">
	 <div id="page-content" class="clearfix">
	   <div class="row-fluid">
			<form action="customer/cusOrderList" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td style="vertical-align:top;">
							<input type="text" id="datetimepicker">
						</td>
						<td style="vertical-align:top;">
							<input class="date-picker" placeholder="请输入结束时间" id="inpEndTime" name="endTime" type="text" value="${pd.endTime}"/>
						</td>
						<td>
							<span class="input-icon">
								<input autocomplete="off" id="nav-search-input" type="text" name="orderno" value="${pd.orderno}" placeholder="请输入订单号" />
							</span>
						</td>
					   <td style="vertical-align:top;"> 
						 	<select id="seleStatus" name="status" data-placeholder="状态" style="vertical-align:top;width: 150px;">
						 		<option value="">全部</option>
						 	</select>
					    </td>
					    <td style="vertical-align:top;">
							<input id="cusidhidParam" name="cusid" type="hidden" value="${pd.cusid}"/>
						</td>
						<td style="vertical-align:top;">
							<button class="btn btn-mini btn-light" onclick="search();"  title="检索">
							<i id="nav-search-icon" class="icon-search"></i></button>
						</td>
					</tr>
				</table>
			
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">序号</th>
							<th class="center">订单号</th>
							<th class="center">类型</th>
							<th class="center">状态</th>
							<th class="center">金额</th>
							<th class="center">优惠券</th>
							<th class="center">实收</th>
							<th class="center">寄件人姓名</th>
							<th class="center">寄件人电话</th>
							<th class="center">寄件人地址</th>
							<th class="center">收件人地址</th>
							<th class="center">生成日期</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty cusOrderList}">
							<c:forEach items="${cusOrderList}" var="cusOrder" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
									<td class="center">${cusOrder.orderno}</td>
									<td class="center">${cusOrder.typeDesc}</td>
								    <td class="center">${cusOrder.statusDesc}</td>
									<td class="center">${cusOrder.totalmoney}</td>
									<td class="center">${cusOrder.cutmoney}</td>
									<td class="center">${cusOrder.totalmoney - cusOrder.cutmoney}</td>
									<td class="center">${cusOrder.sendername}</td>
									<td class="center">${cusOrder.senderphone}</td>
									<td class="center">${cusOrder.srcprovname}  ${cusOrder.srccityname}  ${cusOrder.srcdistrictname}  ${cusOrder.srcaddress}</td>
									<td class="center">${cusOrder.destprovname}  ${cusOrder.destcityname}  ${cusOrder.destdistrictname}  ${cusOrder.destaddress}</td>
									<td class="center"><fmt:formatDate value="${cusOrder.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
		<script type="text/javascript" src="static/js/bootstrap-datetimepicker.min.js"></script><!-- 日期时间框 -->
		
		<script type="text/javascript">
		    $(top.hangge());
		    
			$(function() {
				// 加载下拉框订单状态
				initOrderStatus();
				
				//日期框
				 $("#datetimepicker").datetimepicker({  
			        format : 'yyyy-mm-dd hh:ii:ssZ p',  
			        todayBtn : true,  
			        autoclose : true  
			     });  
			});
			
			
			/* 检索 */
			function search(){
				top.jzts();
				$("#Form").submit();
			}
			
			/* 加载下拉框订单状态 */ 
			function initOrderStatus() {
				$.ajax({
					url : "./customer/seleBaseConfig",
					data : {
						"codeType":'orderStatus'
					},
					dataType : "json",
					type : "POST",
					success : function(text) {
						var json = eval(text);
						$.each(json, function (index, item) {  
			                 $("#seleStatus").append("<option value='"+json[index].code+"'>"+json[index].codename+"</option>");
			            });  
					},
					error : function() {}
				});	 
			}
			
		</script>
	</body>
</html>

