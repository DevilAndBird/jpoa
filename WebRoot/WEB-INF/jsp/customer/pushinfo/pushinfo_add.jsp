<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="top.jsp"%> 
	<%@ include file="common.jsp"%>
	</head> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="pushInfo/pushinfoPage.do" method="post" name="userForm" id="userForm">
			<table>
						<!-- 主题 -->
						<tr >
						<td>
					        <h1><span class="label label-default">消息主题</span></h1>
					     </td></tr>
					     	  <tr>
					      <td><span class="input-icon"> <input	autocomplete="off" type="text" name="theme" id="theme" placeholder="请输入推送消息主题" required="required"/>
								</span></td>
					    </tr>
					
						<!-- 推送 -->
						<tr >
						<td>
					        <h1><span class="label label-default">消息内容</span></h1>
					     </td></tr>
					     	  <tr>
					      <td><span class="input-icon"> <input	autocomplete="off" type="text" style="width:400px" name="notice" id="notice" placeholder="请输入推送消息内容" required="required"/>
								</span></td>
					    </tr>
			<%-- 
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="USERNAME" value="${pd.USERNAME }" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="最近登录开始"/></td>
					<td><input class="span10 date-picker" name="lastLoginEnd" name="lastLoginEnd"  value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="最近登录结束"/></td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择职位" style="vertical-align:top;width: 120px;">
						<option value=""></option>
						<option value="">全部</option>
						<c:forEach items="${roleList}" var="role">
							<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
						</c:forEach>
					  	</select>
					</td>
					<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="window.location.href='<%=basePath%>/user/listtabUsers.do';" title="切换模式"><i id="nav-search-icon" class="icon-exchange"></i></a></td>
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					<c:if test="${QX.edit == 1 }"><td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td></c:if>
					</c:if>
				</tr>
			</table>
			<!-- 检索  -->
		 --%>
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						
						<th class="center">序号</th>
						<th class="center">姓名</th>
						<th class="center">人员类型</th>
						<th class="center">最后登录时间</th>
						
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty pushInfoUserList}">
						<c:forEach items="${pushInfoUserList}" var="pushInfoUser" varStatus="vs">
									
							<tr>
								<td class='center' style="width: 30px;">
									<input type='checkbox' name='ids' value="${pushInfoUser.id}" /><span class="lbl"></span>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								
								<td class="center">${pushInfoUser.name}</td>
								<td class="center">${pushInfoUser.typename}</td>										
								<td class="center">${pushInfoUser.lastlogintime}</td>
								
								
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
					<a class="btn btn-small btn-success" onclick="addpushinfo();">推送所选人员</a>
					<a class="btn btn-small btn-success" onclick="addpushinfoall();">推送全部人员</a>
					<a class="btn btn-small btn-success" onclick="addpushinfoallqu();">推送取派员</a>
					<a class="btn btn-small btn-success" onclick="addpushinfoallji();">推送集散中心</a>
					<a class="btn btn-small btn-success" onclick="addpushinfoallgui();">推送机场柜台</a>
				</td>
				
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
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
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		//String[]
		//for();
		//0
		
		
/* 推送所选人员 */
function addpushinfo(){
	 var check_val = "";
	 $(" [name='ids']:checked").each(function(index, element) {
	 	  check_val = check_val + $(this).val() + ",";
     });
	
	var theme = $("#theme").val();
	var notice = $("#notice").val();

	$.ajax({
		url : "pushInfo/pushinfoadd",
		data : {
			list : check_val,
			notice  : notice,
			theme : theme
		},
		dataType : "text",
		type : "POST",
		traditional :true,
		success : function(data) {
			 layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					location.href ="pushInfo/pushinfoPage";
				}
			}); 
			
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
}		
/* 推送全部人员 */
function addpushinfoall(){
	 /* var check_val = "";
	 $(" [name='ids']:checked").each(function(index, element) {
	 	  check_val = check_val + $(this).val() + ",";
     });
	alert(check_val); */
	
	var theme = $("#theme").val();
	var notice = $("#notice").val();

	$.ajax({
		url : "pushInfo/pushinfoaddallqu",
		data : {
			notice  : notice,
			theme : theme
		},
		dataType : "json",
		type : "POST",
		traditional :true,
		success : function(data) {
			 layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					location.href ="pushInfo/pushinfoPage";
				}
			}); 
			
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
}		
/* 推送全部人员 */
function addpushinfoallqu(){
	 /* var check_val = "";
	 $(" [name='ids']:checked").each(function(index, element) {
	 	  check_val = check_val + $(this).val() + ",";
     });
	alert(check_val); */
	
	var theme = $("#theme").val();
	var notice = $("#notice").val();

	$.ajax({
		url : "pushInfo/pushinfoaddallqu",
		data : {
			notice  : notice,
			theme : theme
		},
		dataType : "json",
		type : "POST",
		traditional :true,
		success : function(data) {
			 layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					location.href ="pushInfo/pushinfoPage";
				}
			}); 
			
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
}		
/* 推送全部人员 */
function addpushinfoallji(){
	 /* var check_val = "";
	 $(" [name='ids']:checked").each(function(index, element) {
	 	  check_val = check_val + $(this).val() + ",";
     });
	alert(check_val); */
	
	var theme = $("#theme").val();
	var notice = $("#notice").val();

	$.ajax({
		url : "pushInfo/pushinfoaddallji",
		data : {
			notice  : notice,
			theme : theme
		},
		dataType : "json",
		type : "POST",
		traditional :true,
		success : function(data) {
			 layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					location.href ="pushInfo/pushinfoPage";
				}
			}); 
			
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
}		
/* 推送全部人员 */
function addpushinfoallgui(){
	 /* var check_val = "";
	 $(" [name='ids']:checked").each(function(index, element) {
	 	  check_val = check_val + $(this).val() + ",";
     });
	alert(check_val); */
	
	var theme = $("#theme").val();
	var notice = $("#notice").val();

	$.ajax({
		url : "pushInfo/pushinfoaddallgui",
		data : {
			notice  : notice,
			theme : theme
		},
		dataType : "json",
		type : "POST",
		traditional :true,
		success : function(data) {
			 layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					location.href ="pushInfo/pushinfoPage";
				}
			}); 
			
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
}		
</script>


		
<script type="text/javascript">		
		$(function() {
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
</script>
		
	</body>
</html>

