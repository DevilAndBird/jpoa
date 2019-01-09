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
	<div class="row-fluid">
		<!-- 检索  -->
		<form action="taskmaindriver/findTaskMainDriverlistPage" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td style="vertical-align:top;"> 
					 	<select name="provid" id="seleProv" data-placeholder="省份" style="vertical-align:top;width: 120px;">
							<option value="-1" selected="selected">--省份--</option>
							<c:forEach items="${provList}" var="obj" varStatus="vs">
								<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name}</option>
							</c:forEach>
					  	</select>
					</td>
					<td style="vertical-align:top;"> 
					 	<select name="cityid" id="seleCity" style="vertical-align:top;width: 120px;">
							<option value="-1" selected="selected">--城市--</option>
					  	</select>
					</td>
						<div class="row-fluid">
							<select name="driverid" id="seleDriverid"  style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--取派员--</option>
                                <c:forEach items="${userDeliveryManList}" var="obj" varStatus="vs"> -->
									<option value="${obj.id}">${obj.name}</option>
								</c:forEach>
						  	</select>
						</div>
					<td style="vertical-align:top;"> 
					 	<select name="status" id="seleStatus" data-placeholder="任务状态" style="vertical-align:top;width: 120px;">
							<option value="-1" selected="selected">--任务状态--</option>
					  	</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search()" title="检索"><i id="nav-search-icon" class="icon-search">检 索</i></button></td>
					<td style="vertical-align:top;">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL">
							<i id="nav-search-icon" class="icon-download-alt">导 出</i>
					    </a>
					</td>
				</tr>
			</table>
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class='center'>序号</th>
						<th class='center'>省份</th>
						<th class='center'>城市</th>
						<th class='center'>运输员</th>
						<th class='center'>手机号码</th>
						<th class='center'>路线</th>
						<th class='center'>开始时间</th>
						<th class='center'>到达时间</th>
						<th class='center'>任务类型</th>
						<th class='center'>状态</th>
						<th class='center'>操作</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty taskMainDriverlistPage}">
						<c:forEach items="${taskMainDriverlistPage}" var="obj" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									${vs.index+1}
									<input type="hidden" value="${obj.id}" />
								</td>
								<td style="display: none;">${obj.id}</td>
								<td  class='center'>
									${obj.provDesc}
								    <input type="hidden" value="${obj.provid}" />
								</td>
								<td  class='center'>
									${obj.cityDesc}
									<input type="hidden" value="${obj.cityid}" />
								</td>
								<td class='center'>${obj.name}</td>
								<td class='center'>${obj.mobile}</td>
								<td class='center'>${obj.srcaddr} — ${obj.destaddr}</td>
								<td class='center'>${obj.depdatetime}</td>
								<td class='center'>${obj.arrdatetime}</td>
								<td class='center'>
									${obj.typeDesc}
									<input type="hidden" value="${obj.type}"/>
								</td >
								<td class='center'>
									${obj.statusDesc}
									<input type="hidden" value="${obj.status}"/>
								</td>
								<td class='center' style="vertical-align:top;">
									<c:if test="${obj.status == 'NOTSTARTED'}">
									    <button title="暂停" class="btn btn-mini btn-info"  onclick="updateTask(this);" value="${obj.id}">暂停</button>
									</c:if>
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
						<td style="vertical-align:top;">
								<a onclick="resetTask()" class="btn btn-small btn-success" data-toggle="modal" data-target="#myModal" >新增任务</a>
						</td>
						<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
			</div>
			
<!-- 模态框（Modal） -->
     <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content" >
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 class="modal-title">新增任务</h4>
                 </div>
                 <div class="modal-body">
					<div id ="divTask">
	                   	<div class="row-fluid">
							<select id="seleProvid_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name}</option>
								</c:forEach>
							</select>
							<select id="seleCityid_modal" data-placeholder="城市" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
							</select>
						</div>
						<div class="row-fluid">
							<select id="seleDriverid_modal" data-placeholder="取派员" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--取派员--</option>
                                <c:forEach items="${userDeliveryManList}" var="obj" varStatus="vs"> -->
									<option value="${obj.userid}">${obj.name}</option>
								</c:forEach>
						  	</select>
						</div>
						<div class="row-fluid">
							<select id="seleType_modal" data-placeholder="任务状态" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--任务类型--</option>
						  	</select>
						</div>
						<div class="row-fluid">
							<select id="seleSrcaddr_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--请选择出发地--</option>
                                <c:forEach items="${transitCenterList}" var="obj" varStatus="vs"> -->
									<option value="${obj.id}">${obj.name}</option>
								</c:forEach>
						  	</select>
						  	<select id="seleDestaddr_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--请选择目的地--</option>
                                <c:forEach items="${transitCenterList}" var="obj" varStatus="vs"> -->
									<option value="${obj.id}">${obj.name}</option>
								</c:forEach>
						  	</select>
						</div>
<!-- 						<div class="row-fluid"> 班车角色放开 -->
<!-- 							<div class="calendarWarp"> -->
<!-- 								<input type="text" class='ECalendar' id="depdatetime_modal" placeholder="开始时间" /> -->
<!-- 							</div> -->
<!-- 							<div class="calendarWarp"> -->
<!-- 								<input type="text" class='ECalendar' id="arrdatetime_modal" placeholder="结束时间" /> -->
<!-- 							</div> -->
<!-- 						</div> -->
	                 </div>
	             </div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="btnSaveTask" type="button" class="btn btn-primary">新增</button>
                 </div>
             </div><!-- /.modal-content -->
         </div><!-- /.modal -->
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
		
		<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/delivery/taskmaindriver/js/taskmaindriver_list.js?r=<%=new Random().nextInt()%>"></script>
		
		<script type="text/javascript">
			$(function() {
				taskMainDriver_list.init();
			});
		</script>
		<script type="text/javascript">
			$(function() {
				$('.input-small').timepicker({
					format: 'HH:mm',
					minuteStep: 1,
					showSeconds: true,
					showMeridian: false
				});
			});
		</script>
		
	</body>
</html>

