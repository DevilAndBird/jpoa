<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	</head> 
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
		<!-- 检索  -->
		<form action="orderUrgeRecord/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<input autocomplete="off" type="text" name="cusid" id="cusid" value="${pd.dmanname }" placeholder="快递员姓名" style="width:174px;"/>
					</td>
					<td>
						<input class="span10 date-picker" autocomplete="off" type="text" name="beginDate" id="beginDate" value="${pd.beginDate }" data-date-format="yyyy-mm-dd" placeholder="下单时间" style="width:174px;"/>
					</td>
					<td>
						- <input class="span10 date-picker" autocomplete="off" type="text" name="endDate" id="endDate" value="${pd.endDate }" data-date-format="yyyy-mm-dd" placeholder="下单时间" style="width:174px;"/>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search">检 索</i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>客户名</th>
						<th>订单编号</th>
						<th>下单时间</th>
						<th>客户手机号码</th>
						<th>快递员</th>
						<th>快递员号码</th>
						<th>催单备注</th>
						<th>催单时间</th>
						<th>是否处理</th>
						<th>处理人</th>
						<th>处理时间</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty orderUrgeRecordList}">
						<c:forEach items="${orderUrgeRecordList}" var="obj" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${obj.cusname }</td>
								<td>${obj.orderno }</td>
								<td><fmt:formatDate value="${obj.ordertime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${obj.mobile }</td>
								<td>${obj.dmanname }</td>
								<td>${obj.dmanmobile }</td>
								<td>${obj.urgenotes }</td>
								<td><fmt:formatDate value="${obj.addtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
									<c:if test="${obj.status==1 }">
										<b style="color: red;">待处理</b>
									</c:if>
									<c:if test="${obj.status==2 }">
										<b style="color: blue;">已处理</b>
									</c:if>
								</td>
								<td>${obj.processusername }</td>
								<td><fmt:formatDate value="${obj.processtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
						<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
			toastr.options = {  
			        closeButton: false,  //是否显示关闭按钮（提示框右上角关闭按钮）
			        debug: false,  //是否为调试；
			        progressBar: true,  //是否显示进度条（设置关闭的超时时间进度条）
			        positionClass: "toast-top-center",  //消息框在页面显示的位置
			        onclick: null,  //点击消息框自定义事件
			        showDuration: "300",  //显示动作时间
			        hideDuration: "1000",  //隐藏动作时间
			        timeOut: "2000",  //自动关闭超时时间
			        extendedTimeOut: "1000",  
			        showEasing: "swing",  
			        hideEasing: "linear",  
			        showMethod: "fadeIn",  //显示的方式
			        hideMethod: "fadeOut"  //隐藏的方式
			};
			
		});
		
		</script>
		
	</body>
</html>

