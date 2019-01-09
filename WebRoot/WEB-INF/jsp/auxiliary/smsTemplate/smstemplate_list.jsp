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
	<%@ include file="../../system/admin/top.jsp"%> 
	<style type="text/css">
		input[type=checkbox].ace-switch-11+.lbl::before{content:"开启\a0\a0\a0\a0\a0\a0\a0\a0禁用"}
	</style>
	
	</head> 
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="smstemplate/listSmsTemplate.do" method="post" name="Form" id="Form">
			<input type="hidden" name="id" id="id"/>
			<input type="hidden" name="statusV" id="statusV"/>
			<table>
				<tr>
					<td>
						<input autocomplete="off" type="text" name="smsname" id="smsname" value="${pd.smsname }" placeholder="短信名称" />
					</td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="status" id="status" data-placeholder="状态" style="vertical-align:top;width: 120px;">
							<option value=""></option>
							<option value="-1" selected="selected">--状态--</option>
							<option value="开启" <c:if test="${pd.business=='开启'}">selected</c:if>>开启</option>
							<option value="禁用" <c:if test="${pd.business=='禁用'}">selected</c:if>>禁用</option>
					  	</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>序号</th>
						<th>编码</th>
						<th>状态</th>
						<th>自定义模板</th>
						<th>模板说明</th>
						<th>模板名称</th>
						<th>创建时间</th>
						<th>修改时间</th>
						<th>修改人</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty smsTemplateList}">
						<c:forEach items="${smsTemplateList}" var="obj" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${obj.smscode }</td>
								<td>
									<c:if test="${obj.status==1 }">
										<label><input name="switch-field-1" class="ace-switch ace-switch-11" onclick="chgstatus(${obj.id},${obj.status })" type="checkbox" checked="checked"/><span class="lbl"></span></label>
									</c:if>
									<c:if test="${obj.status==2 }">
										<label><input name="switch-field-1" class="ace-switch ace-switch-11" onclick="chgstatus(${obj.id},${obj.status })" type="checkbox" /><span class="lbl"></span></label>
									</c:if>
									
								</td>
								<td>${obj.template }</td>
								<td>${obj.explain }</td>
								<td>${obj.smsname }</td>
								<td>${obj.addtime }</td>
								<td>${obj.updatetime }</td>
								<td>${obj.updateby }</td>
								<td class="center" style="width: 120px;">
									<a title="编辑" class='btn btn-mini btn-info' onclick="edit(${obj.id});" ><i class='icon-edit'></i></a>
									<a title="删除" class="btn btn-mini btn-danger" onclick="delTemplate(${obj.id});" ><i class='icon-trash'></i></a>
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
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" id="templateAdd">新增模版</a></td>
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
			$("#Form").submit();
		}
		
		//修改状态
		function chgstatus(id,status){
			if(status==1){
				$("#statusV").val("2");
			}else{
				$("#statusV").val("1");
			}
			$("#id").val(id);
			var url = "<%=basePath%>smstemplate/chgStatus.do";
			$("#Form").attr("action", url );
			$("#Form").submit();
			
		}
		
		//添加配置信息
		$("#templateAdd").click(function(){
			
			bootbox.confirm("确定要添加短信模板吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>smstemplate/goback.do";
					$("#Form").attr("action", url );
					$("#Form").submit();
				}
			});
			
		}); 
		
		//修改配置信息
		function edit(id){
			$("#id").val(id);
			bootbox.confirm("确定要修改短信模板吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>smstemplate/goback.do";
					$("#Form").attr("action", url );
					$("#Form").submit();
				}
			});
		}
		
		//删除
		function delTemplate(id){
			bootbox.confirm("确定要删除此模板吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>smstemplate/delTemplate.do?id="+id;
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
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
		
		</script>
		
	</body>
</html>

