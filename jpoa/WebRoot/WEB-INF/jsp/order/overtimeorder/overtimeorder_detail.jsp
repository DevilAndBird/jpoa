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
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>
<link rel="stylesheet" type="text/css" href="static/js/reset.css" />
<link rel="stylesheet" type="text/css" href="static/js/style.css" />
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">序号</th>
								<th class="center">城市</th>
								<th class="center">快递员</th>
								<th class="center">手机号码</th>
								<th class="center">订单编号</th>
								<th class="center">客户名</th>
								<th class="center">客户手机号码</th>
								<th class="center">支付时间</th>
								<th class="center">超时分钟数</th>
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty overTimeDetailList}">
									<c:forEach items="${overTimeDetailList}" var="detiallist"
										varStatus="vs">
										<tr>
											<td class="center">${vs.index+1}</td>
											<td class="center">${detiallist.cityname}</td>
											<td class="center">${detiallist.name}</td>
											<td class="center">${detiallist.mobile}</td>
											<td class="center">${detiallist.orderno}</td>
											<td class="center">${detiallist.cusname}</td>
											<td class="center">${detiallist.cusmobile}</td>
											<td class="center">${detiallist.paytime}</td>
											<td class="center">${detiallist.differmin}</td>
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
		
		function selectDetail(userid){
			bootbox.confirm("确定要跳转到明细页面?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>orderinfo/listOrderDetail.do&userid="+userid;
					$("#Form").attr("action", url);
					$("#Form").submit();
				}
			});
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

