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
				<form action="businesshours/businessHourslistPage" method="post" name="Form" id="Form" >
					<table>
						<tr>
							<td style="vertical-align:top;"> 
							 	<select name="provid" id="seleProv" data-placeholder="省份" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--省份--</option>
									<c:forEach items="${provList}" var="obj" varStatus="vs">
										<option value="${obj.id }" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name }</option>
									</c:forEach>
							  	</select>
							</td>
							<td style="vertical-align:top;">
								<input type='hidden' id='hidden_cityid' value='${pd.cityid}'> 
							 	<select name="cityid" id="seleCity" data-placeholder="城市" style="vertical-align:top;width: 120px;" >
									<option value="-1">--城市--</option>
							  	</select>
							</td>
							<td style="vertical-align:top;">
								<button style="height: 30px;" class="btn btn-mini btn-light" title="检 索">
									<i id="nav-search-icon" class="icon-search" onclick="search();">检索</i>
								</button>
							</td>
						</tr>
					</table>
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">序号</th>
								<th class="center">城市</th>
								<th class="center">寄件类型</th>
								<th class="center">寄件开始时间</th>
								<th class="center">寄件结束时间</th>
								<th class="center">送件类型</th>
								<th class="center">送件开始时间</th>
								<th class="center">送件结束时间</th>
								<th class="center">是否过夜</th>
								<th class="center">操作</th>
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty businesshourslist}">
									<c:forEach items="${businesshourslist}" var="obj" varStatus="vs">
										<tr>
										    <input type='hidden' id='hiddenid' value ='${obj.id}'>
											<td class="center">${vs.index+1}</td>
											<td class="center cityid">${obj.cityid}</td>
											<td class="center sendtypename">${obj.sendtypename}</td>
											<td class="center sendstarttime">${obj.sendstarttime}</td>
											<td class="center sendendtime">${obj.sendendtime}</td>
											<td class="center tasktypename">${obj.tasktypename}</td>
											<td class="center taskstarttime">${obj.taskstarttime}</td>
											<td class="center taskendtime">${obj.taskendtime}</td>
											<td class="center isovernightname">${obj.isovernightname}</td> 
											<td class="center">
												<a title="编辑" class='btn btn-mini btn-info' onclick="edit_yes(this);" ><i class='icon-edit'>编辑</i></a>
												<a title="刷新" class='btn btn-mini btn-info' onclick="cancel_yes(this);" ><i class='icon-edit'>删除</i></a>
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
					<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td >
									<a title="新增" class="btn btn-small btn-success" onclick="save_yes(this);" >新增</a>
								</td>
								<td style="vertical-align:top;">
									<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
								</td>
							</tr>
						</table>
					</div>
					
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 class="modal-title">营业时间</h4>
                     <input type='hidden' id='hiddenupdateid'>
      			</div>
                 <div class="modal-body">
					<div id="divsave">
	                   	<div class="row-fluid" id="provandcity">
						 	<select id="seleProvid_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
						  	</select>
						 	<select id="seleCityid_modal" data-placeholder="城市" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
						  	</select>
						</div>
							<div class="row-fluid">
	                   		<label>寄件类型</label>
	                   		    <span class="input-icon">
								<select  id="sendtype" class="classify">
									<option value="请选择">请选择</option>
								</select>
							 </span>
						</div>
						<div class="row-fluid">
						<div style="float:left;">						
	                   		<label>寄件开始时间</label>
	                   		 <span class="input-icon">
								<input type="text" name="sendstarttime" id="sendstarttime" autocomplete="off" placeholder="请输入寄件开始时间" />
							</span>
						</div>
						<div style="float:left;">						
	                   		<label>寄件结束时间</label>
	                   		 <span class="input-icon">
							 <input type="text" name="sendendtime" id="sendendtime" autocomplete="off" placeholder="寄件结束时间" />
							</span>
						</div>
						</div>
						<div class="row-fluid">
	                   		<label>收件类型</label>
	                   		  <span class="input-icon">
								<select id="tasktype" class="classify">
									<option value="请选择">请选择</option>
								</select>
							 </span>
						</div>
						<div class="row-fluid">
						<div style="float:left;">						
	                   		<label>寄件开始时间</label>
	                   		 <span class="input-icon">
								<input type="text" name="taskstarttime" id="taskstarttime" autocomplete="off" placeholder="请输入寄件开始时间" />
							</span>
						</div>
						<div style="float:left;">						
	                   		<label>寄件结束时间</label>
	                   		 <span class="input-icon">
							 <input type="text" name="taskendtime" id="taskendtime" autocomplete="off" placeholder="寄件结束时间" />
							</span>
						</div>
						</div>
						<div>
	                   		<label>是否隔夜</label>
	                   		 <span class="input-icon">
							 	<select id="isovernight" class="classify">
									<option value="请选择">请选择</option>
								</select>
							</span>
						</div>
					</div>
                 </div>
                 <div class="modal-footer">
                      <button id="modifyconfig" type="button" class="btn btn-primary">确认修改</button>
                      <button id="saveconfig" type="button" class="btn btn-primary">确认保存</button>
                 </div>
        	 </div>
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
	
	
	<script type="text/javascript" src="${ctx}/jpoa/common/js/baseoperation.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/config/businesshours/js/business_hours.js?r=<%=new Random().nextInt() %>"></script>
</body>
</html>

