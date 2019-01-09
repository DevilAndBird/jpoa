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
	<meta charset="utf-8" />
	<meta name="description" content="overview & stats" /> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	
    <link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="static/css/chosen.css"/>
	
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" /> 
	
	<link rel="stylesheet" href="static/css/datepicker.css" /> 
	
	<script type="text/javascript" src="static/js/myjs/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/js/myjs/jquery.tips.js"></script>
	
	<!-- 提示框 -->
	<link rel="stylesheet" href="static/css/toastr.css" />
	</head>
<body>
	<div class="container-fluid" id="main-container">
	 <div id="page-content" class="clearfix">
	   <div class="row-fluid">
			<form action="userPickerInfo/userPickerInfoListPage" method="post" name="Form" id="Form">
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
							 	<select name="airportcode" data-placeholder="机场" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--机场--</option>
									<c:forEach items="${airprotInfoConfigList}" var="obj" varStatus="vs">
										<option value="${obj.id}" <c:if test="${obj.id==pd.airportcode}">selected</c:if>>${obj.airportname}</option>
									</c:forEach>
							  	</select>
						</td>
						
					    <td style="vertical-align:top;">
					    	<button class="btn btn-mini btn-light" title="检 索">
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
							<th class="center">加入时间</th>
							<th class="center">状态</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty userPickerInfoListPage}">
							<c:forEach items="${userPickerInfoListPage}" var="userPickerInfo" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
										<td class="center">${userPickerInfo.name}</td>
										<td class="center">${userPickerInfo.idno}</td>
										<td class="center">${userPickerInfo.mobile}</td>
										<td class="center"><fmt:formatDate value="${userPickerInfo.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">
											 ${userPickerInfo.isvalidDesc}
											 <input type="hidden" value="${userPickerInfo.isvalid}" />
										 </td>
										<td class="center">
											<button onclick="fillUserPickModal(this)" class="btn btn-link" data-toggle="modal" data-target="#myModal" title="编辑" class="btn btn-link" value="${userPickerInfo.id}">编辑</button>
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
							<td style="vertical-align:top;"><a class="btn btn-small btn-success" id="transitCenterAdd">新增服务中心</a></td>
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
                     <h4 class="modal-title" id="myModalLabel"></h4>
                 </div>
                 <div class="modal-body">
					<div class="row-fluid">
						<input type="text" name="nameModal" id="nameModal" placeholder="行李提取员名称"/> 
					</div>
					<div class="row-fluid">
						<input type="text" name="idnoModal" id="idnoModal" placeholder="负责人"/> 
					</div>
					<div class="row-fluid">
						<input type="text" name="mobileModal" id="mobileModal" placeholder="行李提取员手机号"/> 
					</div>
					<div class="row-fluid">
						<select name="userPickerIsvalid" id="userPickerIsvalid" data-placeholder="是否可用" style="vertical-align:top;width: 120px;">
							<option value="-1" selected="selected">--是否有效--</option>
						</select>
					</div>
                 </div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="updateBtn" type="button" class="btn btn-primary">更改</button>
                     <button id="saveBtn" type="button" class="btn btn-primary">新增</button>
                 </div>
             </div><!-- /.modal-content -->
         </div><!-- /.modal -->
				
			</form>
		</div>
	  </div>
	</div>
		
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
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
		<!-- 提示框 -->
		<script type="text/javascript" src="static/js/toastr.js"></script>
		
		<script type="text/javascript" src="static/js/jquery-ui.min.js"></script>
		<script type="text/javascript">
		    $(top.hangge());
			$(function() {
				//下拉框
				$(".chzn-select").chosen();
				/* 检索 */
				function search(){
					top.jzts();
					$("#Form").submit();
				}
				/* 加载模态框是否是否可用状态 */
				loadIsValid();
				
				/* 提示框 */
				toastr.options = {  
				        closeButton: false,  //是否显示关闭按钮（提示框右上角关闭按钮）
				        debug: false,  //是否为调试；
				        progressBar: true,  //是否显示进度条（设置关闭的超时时间进度条）
				        positionClass: "toast-top-center",  //消息框在页面显示的位置
				        onclick: null,  //点击消息框自定义事件
				        showDuration: "300",  //显示动作时间
				        hideDuration: "1000",  //隐藏动作时间
				        timeOut: "2000",  //自动关闭超时时间
				        extendedTimeOut: "1000",  
				        showEasing: "swing",  
				        hideEasing: "linear",  
				        showMethod: "fadeIn",  //显示的方式
				        hideMethod: "fadeOut"  //隐藏的方式
				};
				
			});
			
			$("#myModal").draggable();
			
			function loadIsValid() {
				$.ajax({
					url : "./userPickerInfo/seleBaseConfig",
					data : {
						codeType : 'ISVALID'
					},
					dataType : "json",
					type : "POST",
					success : function(data) {
						$("#userPickerIsvalid").empty();
						$("#userPickerIsvalid").append("<option value='-1' selected='selected'>--是否有效--</option>");
						var json = eval(data);
						$.each(json, function (i, item) {
							$("#userPickerIsvalid").append("<option value='"+json[i].code+"'>"+json[i].codename+"</option>");
			            });
					}
				});	
			}
			
			/* 查询框省市联动 */
			$("#seleProv").change( function() {
				provCityLinkage($(this).val());
			});
			function provCityLinkage(param){
                $.ajax({
					url : "./city/findCityByProvid",
					data : {
						provid : param
					},
					dataType : "json",
					type : "POST",
					success : function(data) {
						$("#seleCity").empty();
						$("#seleCity").append("<option value='-1' selected='selected'>--城市--</option>");
						var json = eval(data);
						$.each(json, function (i, item) {
							$("#seleCity").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
			            });
					}
				});	 
			}
			
			/* 集散中心点击修改时回填充 */
			function fillUserPickModal(param)  {
				 $("h4").text("行李提取员编辑");
				 var s =$(param).parent().parent().find("td");
				 var userPickerName = s.eq(1).text();
				 $("#nameModal").val(userPickerName);
				 
				 var userPickerIdno = s.eq(2).text();
				 $("#idnoModal").val(userPickerIdno);
				 
				 var userPickerMoile = s.eq(3).text();
				 $("#mobileModal").val(userPickerMoile);
				 
				 var userPickerIsvalid = s.eq(5).find("input").val();
				 $("#userPickerIsvalid").find("option[value='" + userPickerIsvalid +"']").attr("selected",true);
				 
				 $("#updateBtn").show();
				 $("#saveBtn").hide();
				 
				 $("#updateBtn").val($(param).val());
			};
			
			/* 编辑集散中心信息 */
			$("#updateBtn").click(function() {
				var userPickerId = $(this).val();
				var userPickerName = $("#nameModal").val();
				var userPickerIdno = $("#idnoModal").val();
				var userPickerMobile =  $("#mobileModal").val();
				var userPickerIsvalid = $("#userPickerIsvalid").attr("selected",true).val();
				
				if(userPickerIsvalid == -1) {
					toastr.warning('请输入数据');
					return ;
				}
				if( !userPickerId || !userPickerName || !userPickerIdno || !userPickerMobile){
					toastr.warning('请输入数据');
					return ;
				}
				
				$.ajax({
					url : "./userPickerInfo/updateUserPickerInfo",
					data : {
						userPickerId : userPickerId,
						userPickerName : userPickerName,
						userPickerIdno : userPickerIdno,
						userPickerMobile : userPickerMobile,
						userPickerIsvalid : userPickerIsvalid
					},
					dataType : "json",
					type : "POST",
					success : function(data) {
						/* 这里需要增加提示框 */
						location.href="./userPickerInfo/userPickerInfoListPage";
					}
				});
				
			});
		</script>
	</body>
</html>

