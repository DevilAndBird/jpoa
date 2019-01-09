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
	<%@ include file="../../system/admin/top.jsp"%>
	</head>
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
		<form action="customer/cusCouponList" method="post" name="Form" id="Form">
		    <table>
					<tr>
						<td>
							<span class="input-icon">
								<input id="InpOrderid" autocomplete="off" id="nav-search-input" type="text" name="orderid" value="${pd.orderid}" placeholder="请输入订单号" />
							</span>
						</td>
					   <td style="vertical-align:top;"> 
						 	<select id="seleType" name="type" data-placeholder="类型" style="vertical-align:top;width: 150px;">
						 		<option value="">全部</option>
						 	</select>
					    </td>
						<td>
							<span class="input-icon">
								<input id="cusid" name="cusid" type="hidden" value="${cusid}"/>
							</span>
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
						<th class="center">券码</th>
						<th class="center">渠道</th>
						<th class="center">开始时间</th>
						<th class="center">结束时间</th>
						<th class="center">金额</th>
						<th class="center">类型</th>
						<th class="center">满减金额</th>
						<th class="center">折扣</th>
						<th class="center">订单号</th>
						<th class="center">是否使用</th>
						<th class="center">使用时间</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty cusCouponList}">
						<c:forEach items="${cusCouponList}" var="cusCoupon" varStatus="vs">
							<tr>
								<td class="center">${vs.index+1}</td>
								<td class="center">${cusCoupon.code}</td>
								<td class="center">${cusCoupon.channelDesc}</td>
								<td class="center"><fmt:formatDate value="${cusCoupon.startdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="center"><fmt:formatDate value="${cusCoupon.invaliddate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="center">${cusCoupon.money}</td>
								<td class="center">${cusCoupon.typeDesc}</td>
								<td class="center">${cusCoupon.fullmoney}</td>
								<td class="center">${cusCoupon.discount}</td>
								<td class="center">${cusCoupon.orderid}</td>
								<td class="center">${cusCoupon.isusedDesc}</td>
								<td class="center"><fmt:formatDate value="${cusCoupon.usetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
  </div><!--/row-->
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
		<script type="text/javascript">
		$(top.hangge());
		
		function search(){
			top.jzts();
			$("#Form").submit();
		} 
		
		</script>
		
		<script type="text/javascript">
		$(function() {
			//下拉框
			 $(".chzn-select").chosen();  
			 /*$(".chzn-select-deselect").chosen({allow_single_deselect:true});*/
			
			// 加载下拉框
			initCouponType();
			
			//日期框
			$('.date-picker').datepicker();
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
			});
		});
		
		function initCouponType() {
			$.ajax({
				url : "./customer/seleBaseConfig",
				data : {
					"codeType":'couponType'
				},
				dataType : "json",
				type : "POST",
				success : function(text) {
					var json = eval(text);
					$.each(json, function (index, item) {  
		                 $("#seleType").append("<option value='"+json[index].code+"'>"+json[index].codename+"</option>");
		            });  
				},
				error : function() {}
			});	 
		}
		</script>
		
	</body>
</html>

