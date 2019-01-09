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
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">序号</th>
							<th class="center">反馈意见</th>
							<th class="center">反馈人</th>
							<th class="center">反馈人手机号</th>
							<th class="center">反馈时间</th>
							<th class="center">处理意见</th>
							<th class="center">处理人</th>
							<th class="center">处理人手机号</th>
							<th class="center">处理时间</th>
							<th class="center">操作</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty problemDetails}">
						<c:forEach items="${problemDetails}" var="problem_Details" varStatus="vs">
							<tr class='pro${problem_Details.id}'>
								<td class="center">${vs.index+1}</td>
								<td class="center">${problem_Details.feedbackdesc}</td>
								<td class="center">${problem_Details.feedbackusername}</td>
								<td class="center">${problem_Details.feedbackusermobile}</td>
								<td class="center">${problem_Details.feedtimeformat}</td>
								<td class="center processdesc">${problem_Details.processdesc}</td>
								<td class="center processname">${problem_Details.processname}</td>
								<td class="center processmobile">${problem_Details.processmobile}</td> 
								<td class="center processtimeformat">${problem_Details.processtimeformat}</td> 
								<td class="center processstatus">
									<p class="btn btn-mini btn-info processlist" onclick="process1(this)">处理列表</p>
									<input type='hidden' value='${problem_Details.id}' />
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
		</div>
	  </div>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 class="modal-title">处理意见</h4>
                 </div>
                 <div class="modal-body">
					<div id="divDriver">
						<div class="row-fluid">
							<input type="text" id="processdesc_modal" placeholder="处理意见"/> 
						</div>
					</div>
                 </div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id='btnProcess' onclick="process2(this)" type="button" class="btn btn-primary">处理</button>
                 </div>
             </div><!-- /.modal-content -->
         </div><!-- /.modal -->
</div>
	
	
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<script type="text/javascript" src="${ctx}/jpoa/order/problemorder/js/problemorder_details.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>

