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
	<%@ include file="../../system/admin/top.jsp"%>
	</head>
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="invitation/list.do" method="post" name="Form" id="Form">
			<input type="hidden" name="modelId" id="modelId" />
			<table>
				<tr>
					<td>
						<input autocomplete="off" type="text" name="invitecode" id="invitecode" value="${pd.invitecode }" placeholder="编码" style="width:134px;height:18.2px;" />
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检 索"><i id="nav-search-icon" class="icon-search">检 索</i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">序号</th>
						<th class="center">邀请码</th>
						<th class="center">姓名</th>
						<th class="center">添加时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty invitationCodeList }">
						<c:forEach items="${invitationCodeList}" var="obj" varStatus="vs">
							<tr>
								<td class="center">${vs.index+1 }</td>
								<td class="center">${obj.invitecode }</td>
								<td class="center">${obj.invitename }</td>
								<td class="center"><fmt:formatDate value="${obj.addtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td style="display: none;">${obj.cusid }</td>
								<td style="display: none;">${obj.id }</td>
								<td class="center" style="width: 120px;">
									<a title="编辑" class='btn btn-mini btn-info edit'><i class='icon-edit'></i></a>
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
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" id="addInvitation">邀请码新增</a></td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		
<!-- model层 start -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="width:538px;height:308px;display: none;">
	<div class="widget-box" style="width:540px;margin-left: -1px;margin-top: -2px;">
		<div class="widget-header">
			<h4 id="h4"></h4>
			<span class="widget-toolbar">
				<span id="close" class="close" data-dismiss="modal" aria-label="Close"><i class="icon-remove"></i></span>
			</span>
		</div>
		<div class="widget-body">
		 <div class="widget-main">
		 	<div class="row-fluid">
				<label for="form-field-12">客户ID</label>
				<input type="text" name="modelCusid" id="modelCusid" value="" placeholder="123"/>
			</div>
			<hr />
		 	<div class="row-fluid">
				<label for="form-field-12">邀请码</label>
				<input type="text" name="modelInvitecode" id="modelInvitecode" value="" placeholder="H001"/>
			</div>
			<hr />
			<div class="row-fluid">
				<label for="form-field-12">邀请人姓名</label>
				<input type="text" name="modelInvitename" id="modelInvitename" value="" placeholder="木桐"/> 
			</div>
			<hr />
			<div class="row-fluid" id="saveDiv">
				<button type="button" class="btn btn-small btn-primary" id="saveBtn" style="float :right;"></button>
			</div>
			<div class="row-fluid" id="updateDiv">
				<button type="button" class="btn btn-small btn-primary" id="updateBtn" style="float :right;"></button>
			</div>
		 </div>
		</div>
	</div>
</div>
<!-- model层 end -->

		
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

		function search(){
			top.jzts();
			$("#Form").submit();
		} 
		
		//添加省份信息
		$("#addInvitation").click(function(){
			$("#h4").text("邀请码新增");
			$("#saveBtn").text("新  增");
			$("#modelInvitecode").val("");
			$("#modelInvitename").val("");
			$("#modelCusid").val("");
			$("#saveDiv").show();
			$("#updateDiv").hide();
		    $('#myModal').modal();
		});
		$("#saveBtn").click(function(){
			var modelCusid = $("#modelCusid").val().trim();
			var modelInvitecode = $("#modelInvitecode").val().trim();
			var modelInvitename = $("#modelInvitename").val().trim();
			if( !modelCusid || !modelInvitecode || !modelInvitename ){
				toastr.warning('请输入数据');
				return ;
			}
			var url = "<%=basePath%>invitation/addInvitation.do";
			$("#Form").attr("action", url );
			$("#Form").submit();
		});
		
		
		//邀请码修改
		$(".edit").click(function(){
			var s=$(this).parent().parent().find("td");
			$("#modelInvitecode").val(s.eq(1).text());
			$("#modelInvitename").val(s.eq(2).text());
			$("#modelCusid").val(s.eq(4).text());
			$("#modelId").val(s.eq(5).text());
			
			$("#h4").text("邀请码修改");
			$("#updateBtn").text("修  改");
			$("#saveDiv").hide();
			$("#updateDiv").show();
		    $('#myModal').modal();
		});
		$("#updateBtn").click(function(){
			var modelInvitecode = $("#modelInvitecode").val().trim();
			var modelInvitename = $("#modelInvitename").val().trim();
			var modelCusid = $("#modelCusid").val().trim();
			var modelId = $("#modelId").val().trim();
			if( !modelCusid || !modelInvitecode || !modelInvitename ){
				toastr.warning('请输入数据');
				return ;
			}
			$.ajax({
				url:"invitation/updateInvitation.do",
				dataType:"json",   //返回格式为json
				data:{
					"modelInvitecode":modelInvitecode,
					"modelInvitename":modelInvitename,
					"modelCusid":modelCusid,
					"modelId":modelId,
				},
				success:function(flag){
			       if(flag==1){
			    	   toastr.error('邀请码已经存在');
			    	   return ;
			       }else if(flag==2){
			    	   location.href="./invitation/list.do";
				   }
			    }
				
			});
		});
		
		</script>
		
		<script type="text/javascript">
		$(function() {
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
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

