﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cys" uri="/selectlib"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%> 
	</head> 
	</head> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">				
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="auditInfoControl/auditlistPage" method="post" name="sheetForm" id="sheetForm">
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
				<tr>
					<td class="center" colspan="7"><h4>待审核表单</h4></td>
				</tr>
					<tr>
						<th>表单名称</th>
						<th>类型</th>
						<th>状态</th>
						<th>备注</th>
						<th>创建者</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>						
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty wiList}">
						<c:forEach items="${wiList}" var="user" varStatus="vs">								
							<tr>
								<td>${user.sheetname }</td>
								<td>${user.sheettypename }</td>
							    <td>${user.statusname }</td>
								<td>${user.notes }</td>
								<td>${user.username }</td>
								<td>${user.createdtime }</td>
									<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
									             <a class='btn btn-mini btn-danger' href="<%=request.getContextPath()%>/auditInfoControl/ailistbyworkid?workid=${user.id}">查看<i class='icon-trash'></i></a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			
			<div class="page-header position-relative">
							<table style="width:100%;">
								<tr>
									<td style="vertical-align:top;"><div class="pagination"	style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
			</div>
		</form>
	</div>
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
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
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
	</body>
</html>

