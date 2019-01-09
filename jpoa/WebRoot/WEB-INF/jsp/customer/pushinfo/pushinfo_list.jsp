<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/static/common/jsp/common.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<form action="pushInfo/listPushInfo.do" method="post" name="Form" id="Form">
					<table>
						<tr>
							<td>
								<span class="input-icon">
									<input autocomplete="off" type="text"  id="name" name="name" placeholder="请输入姓名" />
									<input name="id" id="id" type="hidden"/>
									<input name="orderType" id="orderType" type="hidden"/>
									
									<input name="scrlandmark" id="scrlandmark" type="hidden"/>
									<input name="destlandmark" id="destlandmark" type="hidden"/>
									
									<input name="srcaddress" id="srcaddress" type="hidden"/>
									<input name="destaddress" id="destaddress" type="hidden"/>
								</span>
							</td>
							<td> 
								<span class="input-icon"> 
									<input type="text" name="starttime" id="starttime" autocomplete="off" placeholder="请输入开始时间" class='ECalendar' />
									<input type='hidden' name='startdate' id='startdate'/>
								</span>
							</td>	
							
							<td>
								<span class="input-icon">
									<input type="text" name="endtime" id="endtime" autocomplete="off" placeholder="请输入结束时间" class='ECalendar' />
									<input type='hidden' name='enddate' id='enddate'/>
								</span>
							</td>
							<td style="vertical-align:top;">
								<button class="btn btn-mini btn-light" title="检 索">
									<i id="nav-search-icon" class="icon-search" onclick="search();">检索</i>
								</button>
								
								<button class="btn btn-mini btn-light" title="推送" id="pushinfo" >
									<i id="nav-search-icon" class="icon-search" onclick="pushinfo();">推送消息</i>
								</button>
								
							</td>
						</tr>
						<tr>
					     	<%-- <td>
								<span class="input-icon">
									<input autocomplete="off" type="text" name="name" id="name" placeholder="客户姓名" value="${pd.name}"/>
								</span>
							</td>
							<td>
								<span class="input-icon">
									<input autocomplete="off" type="text" name="mobile" id="mobile" placeholder="客户手机号码" value="${pd.mobile}"/>
								</span>
							</td> --%>
							<!-- <td style="vertical-align:top;">
								<button class="btn btn-mini btn-light" title="检 索">
									<i id="nav-search-icon" class="icon-search" onclick="search();">检索</i>
								</button>
								
								<button class="btn btn-mini btn-light" title="导出">
									<i id="nav-search-icon" class="icon-search" onclick="toExcel();">导出</i>
								</button>
								
								<button class="btn btn-mini btn-light" title="新增" id="saveOrderMain">
									<i id="nav-search-icon" class="icon-search"
										onclick="toExcel();">新增</i>
								</button>
						
								<button class="btn btn-mini btn-light" title="推送" id="pushinfo" >
									<i id="nav-search-icon" class="icon-search" onclick="pushinfo();">推送消息</i>
								</button>
								
							</td> -->
						</tr>
					</table>
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">序号</th>
								<th class="center">姓名</th>
								<th class="center">主题</th>
								<th class="center">通知详情</th>
								<th class="center">推送时间</th>
								
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty pushInfoList}">
									<c:forEach items="${pushInfoList}" var="pushInfo"
										varStatus="vs">
										<tr>
											<td class="center">${vs.index+1}</td>
											<td class="center">${pushInfo.name}</td>
											<td class="center">${pushInfo.theme}</td>										
											<td class="center">${pushInfo.pushcontent}</td>
											<td class="center">${pushInfo.addtime}</td>
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
					
					<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4CancelOrder" class="modal-title">取消订单</h4>
                     <h4 id="h4UpdateStatus" class="modal-title">订单状态修改</h4>
                 </div>
                 
                 <div class="modal-body">
                 	<div id="divCancelOrder">
	                   	<div class="row-fluid">
	                   		<label>是否取消订单?</label>
						</div>
					</div>
					<div id="divUpdateStatus">
	                   	<div class="row-fluid">
	                   		<select name="status_modal" id="seleStatus_modal" data-placeholder="订单状态" style="vertical-align:top;width: 170px;">
								<option value="-1" selected="selected">--选择订单状态--</option>
							</select>
						</div>
					</div>
	                 <div class="modal-footer">
	                     <input type='hidden' id="orderroleid" name="orderroleid" />
	                     <button id="btnCancelDMan" type="button" class="btn btn-primary">是</button>
	                     <input type="hidden">
	                      <button id="btnUpdateStatus" type="button" class="btn btn-primary">修改</button>
	                 </div>
                 </div><!-- /.modal-content -->
                 
        	 </div><!-- /.modal -->
		</div>
		</div>					
				</form>
			</div>
		</div>
	</div>
	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/order/ordermain/js/ordermain_list.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" >
		$("#pushinfo").click(function() {
		var url = "pushInfo/pushinfoPage.do";
		$("#Form").attr("action", url);
		$("#Form").submit();
		});
	</script>
<script type="text/javascript" >	
	/* 表单提交 */
/* 表单提交 */
function search() {
	
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	
	 $("#startdate").val(starttime +"");
	 $("#enddate").val(endtime +"");
	
	$("#Form").submit();
}
</script>	
</body>
</html>

