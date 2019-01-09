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
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="coupon/couponList.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="isvalid" id="isvalid" data-placeholder="是否有效" style="vertical-align:top;width: 120px;">
							<option value=""></option>
							<option value="-1" selected="selected">--是否有效--</option>
							<option value="有效" <c:if test="${pd.business=='Y'}">selected</c:if>>有效</option>
							<option value="无效" <c:if test="${pd.business=='N'}">selected</c:if>>无效</option>
					  	</select>
					</td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="type" id="type" data-placeholder="类型" style="vertical-align:top;width: 120px;">
							<option value=""></option>
							<option value="-1" selected="selected">--类型--</option>
							<option value="1" <c:if test="${pd.type=='directreduce'}">selected</c:if>>直减金额</option>
							<option value="2" <c:if test="${pd.type=='fullreduce'}">selected</c:if>>满减金额</option>
							<option value="3" <c:if test="${pd.type=='discount'}">selected</c:if>>折扣</option>
					  	</select>
					</td>
					<td>
						<input autocomplete="off" type="text" name="code" id="code" value="${pd.code }" placeholder="编码" style="width:134px;height:18.2px;" />
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检 索"><i id="nav-search-icon" class="icon-search">检 索</i></button></td>
					<td style="vertical-align:top;">&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt">导 出</i></a></td>
				</tr>
			</table>
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">序号</th>
						<th class="center">编号</th>
						<th class="center">生成时间</th>
						<th class="center">类型</th>
						<th class="center">是否有效</th>
						<th class="center">开始时间</th>
						<th class="center">有效时间</th>
						<c:if test="${pd.type=='' or pd.type==null or pd.type==-1 }">
							<th class="center">金额</th>
							<th class="center">满减金额</th>
							<th class="center">折扣</th>
						</c:if>
						<c:if test="${pd.type!='' and pd.type!=null and pd.type=='directreduce'}">
							<th class="center">金额</th>
						</c:if>
						<c:if test="${pd.type!='' and pd.type!=null and pd.type=='fullreduce'}">
							<th class="center">金额</th>
							<th class="center">满减金额</th>
						</c:if>
						<c:if test="${pd.type!='' and pd.type!=null and pd.type=='discount'}">
							<th class="center">折扣</th>
						</c:if>
						<th class="center">目标用户</th>
						<th class="center">创建人</th>
						<th class="center">总数量</th>
						<th class="center">使用数量</th>
						<th class="center">备注说明</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty couponInfoList }">
						<c:forEach items="${couponInfoList}" var="obj" varStatus="vs">
							<tr>
								<td class="center">${vs.index+1 }</td>
								<td class="center">${obj.code }</td>
								<td class="center"><fmt:formatDate value="${obj.addtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="center">
									<c:if test="${obj.type=='directreduce' }">
										直减
									</c:if>
									<c:if test="${obj.type=='fullreduce' }">
										满减
									</c:if>
									<c:if test="${obj.type=='discount' }">
										折扣
									</c:if>
								</td>
								<td class="center">
									<c:if test="${obj.isvalid=='Y' }">
										有效
									</c:if>
									<c:if test="${obj.isvalid=='N' }">
										无效
									</c:if>
								</td>
								<td class="center">${obj.startdate }</td>
								<td class="center">${obj.invaliddate }</td>
								<c:if test="${pd.type=='' or pd.type==null or pd.type==-1 }">
									<td class="center">${obj.money }</td>
									<td class="center">${obj.fullmoney }</td>
									<td class="center">${obj.discount }</td>
								</c:if>
								<c:if test="${pd.type!='' and pd.type!=null and pd.type=='directreduce'}">
									<td class="center">${obj.money }</td>
								</c:if>
								<c:if test="${pd.type!='' and pd.type!=null and pd.type=='fullreduce'}">
									<td class="center">${obj.money }</td>
									<td class="center">${obj.fullmoney }</td>
								</c:if>
								<c:if test="${pd.type!='' and pd.type!=null and pd.type=='discount'}">
									<td class="center">${obj.discount }</td>
								</c:if>
								<td class="center">
									<c:if test="${obj.channel==1 }">
										手工指定用户优惠券
									</c:if>
									<c:if test="${obj.channel==2 }">
										新用户优惠券
									</c:if>
									<c:if test="${obj.channel==3 }">
										微信注册渠道用户优惠券
									</c:if>
									<c:if test="${obj.channel==4 }">
										携程优惠券
									</c:if>
								</td>
								<td class="center">${obj.createdby }</td>
								<td class="center">${obj.leftnum }</td>
								<td class="center">${obj.usenum }</td>
								<td class="center">${obj.notes }</td>
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
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" id="createCoupon">优惠卷生成</a></td>
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
		
		
		//导出
		function toExcel(){
			var url = '<%=basePath%>coupon/outExcel.do';
			$("#Form").attr("action", url );
			$("#Form").submit();
		}
		
		//优惠券生成
		$("#createCoupon").click(function(){
			
			bootbox.confirm("确定要生成优惠卷吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>coupon/goCreate.do";
					$("#Form").attr("action", url );
					$("#Form").submit();
				}
			});
			
		}); 
		
		
		function search(){
			top.jzts();
			$("#Form").submit();
		} 
		
		</script>
		
		<script type="text/javascript">
		$(function() {
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
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
		</script>
		
	</body>
</html>

