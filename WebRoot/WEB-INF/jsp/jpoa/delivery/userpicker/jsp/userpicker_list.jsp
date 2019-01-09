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
			<form action="userPicker/userPickerlistPage" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td style="vertical-align:top;"> 
							 	<select name="airportcode" data-placeholder="机场" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--机场--</option>
									<c:forEach items="${airprotInfoConfigList}" var="obj" varStatus="vs">
										<option value="${obj.id}" <c:if test="${obj.id==pd.airportcode}">selected</c:if>>${obj.airportname}</option>
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
							<th class="center">身份证</th>
							<th class="center">手机号</th>
							<th class="center">状态</th>
							<th class="center">加入时间</th>
							<th class="center">修改时间</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty userPickerlistPage}">
							<c:forEach items="${userPickerlistPage}" var="userPickerInfo" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
										<td class="center">${userPickerInfo.name}</td>
										<td class="center">${userPickerInfo.idno}</td>
										<td class="center">${userPickerInfo.mobile}</td>
										<td class="center">
											 ${userPickerInfo.isvalidDesc}
											 <input type="hidden" value="${userPickerInfo.isvalid}" />
										 </td>
										<td class="center"><fmt:formatDate value="${userPickerInfo.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center"><fmt:formatDate value="${userPickerInfo.modifytime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">
											<button onclick="fillUserPick_modal(this)" class="btn btn-link" data-toggle="modal" data-target="#myModal" title="编辑" class="btn btn-link" value="${userPickerInfo.id}">编辑</button>
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
				
				 	<!-- 按钮触发模态框 -->
     <!-- 模态框（Modal） -->
     <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4UserPicker" class="modal-title">编辑行李提取员</h4>
                 </div>
                 <div class="modal-body">
					<div class="row-fluid">
						<input type="text" name="name_modal" id="inpName_modal" placeholder="行李提取员名称"/> 
					</div>
					<div class="row-fluid">
						<input type="text" name="idno_modal" id="inpIdno_modal" placeholder="负责人"/> 
					</div>
					<div class="row-fluid">
						<input type="text" name="mobile_Modal" id="inpMobile_modal" placeholder="行李提取员手机号"/> 
					</div>
					<div class="row-fluid">
						<select name="isvalid_modal" id="seleIsvalid_modal" data-placeholder="是否可用" style="vertical-align:top;width: 120px;">
							<option value="-1" selected="selected">--是否有效--</option>
						</select>
					</div>
                 </div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="btnUpdateUserPicker" type="button" class="btn btn-primary">更改</button>
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
		<script type="text/javascript" src="${ctx}/jpoa/delivery/userpicker/js/userpicker_list.js?r=<%=new Random().nextInt()%>"></script>
		
		<script type="text/javascript">
			$(function() {
				userPicker_list.init();
			});
		</script>
	</body>
</html>

