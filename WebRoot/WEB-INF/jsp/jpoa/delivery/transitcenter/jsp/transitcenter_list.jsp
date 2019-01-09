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
			<form action="transitCenter/findTransitCenterlistPage" method="post" name="Form" id="Form">
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
							 	<select name="transitCenterid" id="seleTransitCenterid" data-placeholder="集散中心" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--集散中心--</option>
									<c:forEach items="${transitCenterList}" var="obj" varStatus="vs">
										<option value="${obj.id}" <c:if test="${obj.id==pd.transitCenterid}">selected</c:if>>${obj.name}</option>
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
							<th class="center">省份</th>
							<th class="center">城市</th>
							<th class="center">集散中心</th>
							<th class="center">地址</th>
							<th class="center">联系人</th>
							<th class="center">联系电话</th>
							<th class="center">状态</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty transitCenterlistPage}">
							<c:forEach items="${transitCenterlistPage}" var="transitCenter" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
									<td class="center">
										${transitCenter.provDesc}
										<input type="hidden" value="${transitCenter.provid}"/>
									</td>
									<td class="center">
										${transitCenter.cityDesc}
										<input type="hidden" value="${transitCenter.cityid}"/>
									</td>
									<td class="center">${transitCenter.name}</td>
									<td class="center">${transitCenter.address}</td>
									<td class="center">${transitCenter.linkman}</td>
									<td class="center">${transitCenter.linkphone}</td>
									<td class="center">
										${transitCenter.isvalidDesc}
										<input type="hidden" value="${transitCenter.isvalid}"/>
									</td>
									<td class="center">
										<button onclick="fillTransitCenter_Modal(this)" class="btn btn-link" data-toggle="modal" data-target="#myModal" value="${transitCenter.id}">编辑</button>
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
								<a onclick="resetTransitCenter()" class="btn btn-small btn-success" data-toggle="modal" data-target="#myModal" >新增集散中心</a>
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
                     <h4 id="h4TransitCenter" class="modal-title">编辑集散中心信息</h4>
                 </div>
                 <div class="modal-body">
					<div id ="divTransitCenter">
	                   	<div class="row-fluid">
							<select name="provid_modal" id="seleProvid_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name}</option>
								</c:forEach>
							</select>
							<select name="cityid_modal" id="seleCityid_modal" data-placeholder="城市" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
							</select>
						</div>
						<div class="row-fluid">
							<input type="text" name="name_modal" id="inpName_modal" placeholder="集散中心名称"/> 
						</div>
						<div class="row-fluid">
							<input type="text" name="address_modal" id="inpAddress_modal" placeholder="集散中心地址"/> 
						</div>
						<div class="row-fluid">
							<input type="text" name="linkMan_modal" id="inpLinkman_modal" placeholder="负责人"/> 
						</div>
						<div class="row-fluid">
							<input type="text" name="linkphone_modal" id="inpLinkphone_modal" placeholder="负责人电话"/> 
						</div>
						<div class="row-fluid">
							<select name="seleIsvalid_modal" id="seleIsvalid_modal" data-placeholder="是否可用" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--是否有效--</option>
							</select>
						</div>
	                 </div>
	             </div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="btnUpdateTransitCenter" type="button" class="btn btn-primary">更改</button>
                     <button id="btnSaveTransitCenter" type="button" class="btn btn-primary">新增</button>
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
		<script type="text/javascript" src="${ctx}/jpoa/delivery/transitcenter/js/transitcenter_list.js?r=<%=new Random().nextInt()%>"></script>
		
		<script type="text/javascript">
			$(function() {
				transitCenter_list.init();
			});
		</script>
	</body>
</html>

