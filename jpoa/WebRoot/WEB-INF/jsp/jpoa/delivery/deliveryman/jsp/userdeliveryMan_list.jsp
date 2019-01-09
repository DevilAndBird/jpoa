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
			<form action="userDeliveryMan/deliveryManlistPage" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td style="vertical-align:top;"> 
						 	<select name="provid" id="seleProv" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name }</option>
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
							<th class="center">姓名</th>
							<th class="center">身份证号</th>
							<th class="center">手机号码</th>
							<th class="center">所属区域</th>
							<th class="center">城市</th>
							<th class="center">状态</th>
							<th class="center">加入时间</th>
							<th class="center">最后修改时间</th>
							<th class="center">在岗/离岗</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty deliveryManlistPage}">
							<c:forEach items="${deliveryManlistPage}" var="userDeliveryMan" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
										<td class="center">${userDeliveryMan.name}</td>
										<td class="center">${userDeliveryMan.idno}</td>
										<td class="center">${userDeliveryMan.mobile}</td>
										<td class="center">
											${userDeliveryMan.regionDesc}
											<input type="hidden" value="${userDeliveryMan.regionid}"/>
										</td>
										<td class="center">
											${userDeliveryMan.cityDesc}
											<input type="hidden" value="${userDeliveryMan.provid}"/>
											<input type="hidden" value="${userDeliveryMan.cityid}"/>
										</td>
										<td class="center">
											${userDeliveryMan.isvalidDesc}
											<input type="hidden" value="${userDeliveryMan.isvalid}"/>
										</td>
										<td class="center"><fmt:formatDate value="${userDeliveryMan.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center"><fmt:formatDate value="${userDeliveryMan.modifytime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">
                                           	<c:choose>
						                     <c:when test="${userDeliveryMan.attendance == 'REST'}">
						                         <button onclick="updateAttendance(${userDeliveryMan.id},'WORK')" class="btn btn-link" title="在岗"  >在岗</button>
						                     </c:when>
						                     <c:otherwise>
						                         <button onclick="updateAttendance(${userDeliveryMan.id},'REST')" class="btn btn-link" title="离岗"  >离岗</button>
						                     </c:otherwise>
						                     </c:choose>
										</td>
										<td class="center">
											<button onclick="filldeliveryMan_Modal(this)" title="编辑" class="btn btn-link"  data-toggle="modal" data-target="#myModal" value="${userDeliveryMan.id}">编辑</button>
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
                     <h4 id="h4DeliveryMan" class="modal-title">更新取派员信息</h4>
                    <!--  <h4 id="saveUserDeliveryManH4" class="modal-title">第二步：新增取派员信息</h4>
                     <h4 id="saveAppUserH4" class="modal-title">第一步：新增登陆用户信息</h4> -->
                 </div>
                 <div class="modal-body">
                   <div id="divDeliverMan">
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
							<input type="text" name="name_modal" id="inpName_modal" placeholder="快递员姓名"/> 
						</div>
						<div class="row-fluid">
							<input type="text" name="idno_modal" id="inpIdno_modal" placeholder="快递员身份证"/> 
						</div>
						<div class="row-fluid">
							<input type="text" name="mobile_modal" id="inpMobile_modal" placeholder="快递员手机号"/> 
						</div>
						<div class="row-fluid">
							<select name="regionDelivery_modal" id="regionDelivery_modal" data-placeholder="营业区域" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--营业区域--</option>
								<c:forEach items="${regionDeliveryList}" var="obj" varStatus="vs">
									<option value="${obj.id}">${obj.regionname}</option>
								</c:forEach>
						  	</select>
						</div>
						<div class="row-fluid">
							<select name="isvalid_modal" id="seleIsvalid_modal" data-placeholder="是否可用" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--是否有效--</option>
							</select>
						</div>
						</div>
                   </div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="btnUpdateDeliveryMan" type="button" class="btn btn-primary">更改</button>
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
		<script type="text/javascript" src="${ctx}/jpoa/delivery/deliveryman/js/userdeliveryMan_list.js?r=<%=new Random().nextInt() %>"></script>
		
		<script type="text/javascript">
			$(function() {
				userDeliveryMan_list.init();
			});
		</script>
	</body>
</html>

