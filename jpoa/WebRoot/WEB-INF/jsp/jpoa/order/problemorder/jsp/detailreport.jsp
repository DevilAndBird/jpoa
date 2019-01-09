<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
	     <%@ include file="/static/common/jsp/common.jsp"%>
	</head>
<body>
	<div class="container-fluid" id="main-container">
	 <div id="page-content" class="clearfix">
	   <div class="row-fluid">
			<form action="reportforms/detailReportForms" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td>
							<span class="input-icon">
								<input type="text" name="orderno" id="orderno"	 autocomplete="off" placeholder="请输入订单号"  value="${pd.orderno}"/>
							</span>
						</td>
						<td>
							<span class="input-icon">
								 <input type="text" name="starttime" id="starttime"autocomplete="off" placeholder="请输入开始时间" class='ECalendar' value="${pd.starttime}"/>
							</span>
						</td>
						<td>
							<span class="input-icon">
								<input type="text" name="endtime" id="endtime"   autocomplete="off"  placeholder="请输入结束时间"  class='ECalendar' value="${pd.endtime}"/>
							</span>
						</td>
					    <td style="vertical-align:top;">
					    	<button class="btn btn-mini btn-light" title="检 索">
					    			<button style='height: 30px;' class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search">检 索</i></button>
					    	</button>
					    	<a class="btn btn-small btn-success" data-toggle="modal" data-target="#myModal" >下载</a>
					    </td>
					</tr>
				</table>
			
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">序号</th>
								<th class="center">订单号</th>
								<th class="center">执行人姓名</th>
								<th class="center">联系方式</th>
								<th class="center">操作岗位</th>
								<th class="center">操作完成时间</th>
								<th class="center">操作内容</th>
								<th class="center">车牌号</th>
								<th class="center">订单状态</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty reportFormsList}">
							<c:forEach items="${reportFormsList}" var="reportForms" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
											<td class="center">${reportForms.orderno}</td>
											<td class="center">${reportForms.operator_name}</td>
											<td class="center">${reportForms.operator_mobile}</td>
											<td class="center">${reportForms.operator_post}</td>
											<td class="center">${reportForms.operator_time}</td>
											<td class="center">${reportForms.operator_content}</td>
											<td class="center">${reportForms.platenumber}</td>
											<td class="center">${reportForms.order_status}</td>
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
							<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4DeliveryMan" class="modal-title">确认下载？</h4>
                 </div>
                 <div class="modal-body">
                 </div>
                 <div class="modal-footer">
                     <button onclick="btnDownloadInvoice()" type="button" class="btn btn-primary">下载</button>
                 </div>
             </div><!-- /.modal-content -->
         </div><!-- /.modal -->
		</div>
			</form>
		</div>
	  </div>
	</div>
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		 <script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
		 <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		 <script type="text/javascript" src="${ctx}/jpoa/reportforms/totalreport/js/detailreport.js?r=<%=new Random().nextInt() %>"></script>
		 
		 <script type="text/javascript">
			$(function() {
				detailreport.init();
			});
			
		</script>
		
	</body>
</html>

