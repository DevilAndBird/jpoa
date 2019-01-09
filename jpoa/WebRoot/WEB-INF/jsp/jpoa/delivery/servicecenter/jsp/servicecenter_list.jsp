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
			<form action="counterServiceCenter/serviceCenterlistPage" method="post" name="Form" id="Form">
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
						 	<select name="cityid" id="seleCity" data-placeholder="" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
						  	</select>
						</td>
						
						<td style="vertical-align:top;"> 
						 	<select name="type" id="seleServiceCenterType" data-placeholder="柜台服务中心类型" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--服务中心类型--</option>
						  	</select>
						</td>
						
						<td style="vertical-align:top;"> 
						 	<select name="servicecentercode" data-placeholder="机场" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--服务中心--</option>
								<c:forEach items="${serviceCenterList}" var="obj" varStatus="vs">
									<option value="${obj.servicecentercode}" <c:if test="${obj.servicecentercode==pd.servicecentercode}">selected</c:if>>${obj.servicecentername}</option>
								</c:forEach>
						  	</select>
						</td>
						
					    <td style="vertical-align:top;">
					    	<button class="btn btn-mini btn-light" onclick="search()" title="检 索">
					    		<i id="nav-search-icon" class="icon-search">检 索</i>
					    	</button>
					    </td>
					</tr>
				</table>
			
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">序号</th>
							<th class="center">服务中心类型</th>
							<th class="center">服务中心</th>
							<th class="center">省份</th>
							<th class="center">城市</th>
							<th class="center">位置</th>
							<th class="center">负责人</th>
							<th class="center">联系电话</th>
							<th class="center">状态</th>
							<th class="center">所属区域</th>
							<th class="center">加入时间</th>
							<th class="center">修改时间</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty serviceCenterlistPage}">
							<c:forEach items="${serviceCenterlistPage}" var="obj" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
									   <td class="center">
											${obj.typeDesc}
											<input type="hidden" value="${obj.type}"/>
										</td>
										<td class="center">
											${obj.servicecentername}
											<input type="hidden" value="${obj.servicecentername}"/>
											<input type="hidden" value="${obj.servicecentercode}"/>
										</td>
										<td class="center">
											${obj.providDesc}
											<input type="hidden" value="${obj.provid}"/>
										</td>
										<td class="center">
											${obj.cityidDesc}
											<input type="hidden" value="${obj.cityid}"/>
										</td>
										<td class="center">${obj.address}</td>
										<td class="center">${obj.linkman}</td>
										<td class="center">${obj.linkphone}</td>
										<td class="center">
											${obj.isvalidDesc}
											<input type="hidden" value="${obj.isvalid}"/>
										</td>
										<td class="center">
										${obj.regioname}
										<input type="hidden" value="${obj.regionid}"/>
										</td>
										<td class="center"><fmt:formatDate value="${obj.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center"><fmt:formatDate value="${obj.modifytime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">
											<button onclick="fillServiceCenter_modal(this)" class="btn btn-link" data-toggle="modal" data-target="#myModal" value="${obj.id}">编辑</button>
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
							<td style="vertical-align:top;">
								<a onclick="resetServiceCenter()" class="btn btn-small btn-success" data-toggle="modal" data-target="#myModal" >新增柜台服务中心</a>
							</td>
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
                     <h4 id="h4UpdateServiceCenter" class="modal-title">编辑柜台服务中心信息</h4>
                 </div>
                 <div class="modal-body">
                 		<div class="row-fluid">
	                 		<select id="seleProvid_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name }</option>
								</c:forEach>
						  	</select>
						  	<select id="seleCityid_modal" data-placeholder="" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
						  	</select>
						</div>
						<div class="row-fluid">
							<select id="seleServiceCenterType_modal" data-placeholder="柜台服务中心类型" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--服务中心--</option>
						  	</select>
					  	</div>
					  	<div class="row-fluid">
		                 	<div class="row-fluid">
								<select id="seleRegion_modal" data-placeholder="请选择所属区域" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--所属区域--</option>
								<c:forEach items="${regionDeliveryMan}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.regionid}">selected</c:if>>${obj.regionname }</option>
								</c:forEach>
						  	</select>
							</div>
						</div>
	                 	<div class="row-fluid">
		                 	<div class="row-fluid">
								<input type="text" id="inpServicecentername_modal" placeholder="柜台服务中心名称"/> 
							</div>
						</div>
						<div class="row-fluid">
		                 	<div class="row-fluid">
								<input type="text" id="inpServicecentercode_modal" placeholder="柜台服务中心编码"/> 
							</div>
						</div>
						<div class="row-fluid">
							<input type="text" id="inpAddress_modal" placeholder="柜台服务中心地址"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpLinkman_modal" placeholder="柜台服务中心负责人"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpLinkphone_modal" placeholder="柜台服务中心负责人电话"/> 
						</div>
						<div class="row-fluid">
							<select  id="seleIsvalid_modal" data-placeholder="是否可用" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--是否有效--</option>
							</select>
						</div>
					</div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="btnUpdateServiceCenter" type="button" class="btn btn-primary">更改</button>
                     <button id="btnSaveServiceCenter" type="button" class="btn btn-primary">新增</button>
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
		<script type="text/javascript" src="${ctx}/jpoa/delivery/servicecenter/js/servicecenter_list.js?r=<%=new Random().nextInt()%>"></script>
		
		<script type="text/javascript">
			$(function() {
				serviceCenter_list.init();
			});
		</script>
	</body>
</html>

