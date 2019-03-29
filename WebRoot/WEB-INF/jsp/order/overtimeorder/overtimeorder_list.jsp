<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<body><!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%>
	<link rel="stylesheet" type="text/css" href="static/js/reset.css" />
	<link rel="stylesheet" type="text/css" href="static/js/style.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/js/bootstrap-datetimepicker.min.css" />
</head>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<form action="overtimeorder/listOverTimeOrder.do" method="post"
					name="Form" id="Form" class="form-inline" role="form">
					<table>
						<input id="userid" name="userid" type="hidden" />
						<tr>
							<td><input autocomplete="off" type="text" name="name"
								id="name" placeholder="快递员" /></td>
							<td><input type="text" name="startdate" class='ECalendar'
								id="startdate" placeholder="开始时间" /></td>
							<td><input type="text" name="enddate" class='ECalendar'
								id="enddate" placeholder="结束时间" /></td>
							<td style="vertical-align:top;"><a
								class="btn btn-mini btn-light" data-toggle="modal"
								data-target="#orderDetail"><i id="nav-search-icon"
									class="icon-search">新增</i></a></td>
							<td style="vertical-align:top;"><button
									class="btn btn-mini btn-light" onclick="search();" title="查询">
									<i id="nav-search-icon" class="icon-search">检 索</i>
								</button></td>
							<td style="vertical-align:top;"><button
									class="btn btn-mini btn-light" onclick="toExcel();" title="导出">
									<i id="nav-search-icon" class="icon-download-alt">导 出</i>
								</button></td>
						</tr>
					</table>
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">序号</th>
								<th class="center">城市</th>
								<th class="center">快递员</th>
								<th class="center">手机号码</th>
								<th class="center">超时订单数</th>
								<th class="center">归属集散中心</th>
								<th class="center">查看</th>
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty overTimeOrderList}">
									<c:forEach items="${overTimeOrderList}" var="orderlist"
										varStatus="vs">
										<tr>
											<td class="center">${vs.index+1}</td>
											<td class="center">${orderlist.cityname}</td>
											<td class="center">${orderlist.name}</td>
											<td class="center">${orderlist.mobile}</td>
											<td class="center">${orderlist.num}</td>
											<td class="center">${orderlist.transitname}</td>
											<td class="center"><a class='btn btn-mini btn-info'
												onclick="selectDetail(${orderlist.userid});">查看列表</a></td>
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
								<td style="vertical-align:top;"><div class="pagination"
										style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
					</div>
					<div class="modal fade" id="orderDetail" tabindex="-1"
						role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog  modal-sm">
							<div class="modal-content">
								<div class="modal-header">
									<h4 class="modal-title" id="myModalLabel">新增页面</h4>
								</div>
								<div class="modal-body">
									<table>
										<tr>
											<td><input autocomplete="off" type="text" name="orderno"
												id="orderno" placeholder="快递员编码" /></td>
											<td><input id="arrivetime" name="arrivetime"
												class="datetimepicker" type="text" placeholder="到达时间"></td>
										</tr>
									</table>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal" onclick="saveBtn();">保存</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">关闭</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/Ecalendar.jquery.min.js"></script>
	<script type="text/javascript" 	src="static/js/bootstrap-datetimepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">
		$(top.hangge());

		function search() {
			top.jzts();
			$("#Form").submit();
		}
		
		selectDetail = function (userid){  
			$("#userid").val(userid);
			var url = "<%=basePath%>overtimeorder/listOverTimeDetail.do";
		   $("#Form").attr("action", url);
		 	$("#Form").submit();
	    }  
		
		saveBtn = function (){  
			var url = "<%=basePath%>overtimeorder/saveOverTimeOrder.do";
		   $("#Form").attr("action", url);
		 	$("#Form").submit();
	    }  
		
		toExcel = function (){  
			var url = '<%=basePath%>overtimeorder/outExcel.do';
			$("#Form").attr("action", url );
			$("#Form").submit();
	    }  
		
		
	</script>

	<script type="text/javascript">
		$(function() {
			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			$("#startdate").ECalendar({
				type : "time",
				offset : [ 0, 2 ]
			});
			$("#enddate").ECalendar({
				type : "time",
				offset : [ 0, 2 ]
			});
     
			$(".datetimepicker").datetimepicker();
			
			//复选框
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});

		});
		
	</script>

</body>
</html>

