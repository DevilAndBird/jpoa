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
	</head> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form method="post" name="Form" id="Form">
			<hr style=" borber:1px blue solid;"  width="100%"/>
			<!-- 检索  -->
			<div id="zhongxin">
				<div id="model" style="margin-left: 40%;width:1570px;">
					 <div class="span4" id="span4">
						<div class="widget-box">
							<div class="widget-header">
								<h4>短信模板</h4>
								<span class="widget-toolbar">
									<span id="close"><i class="icon-remove"></i></span>
								</span>
							</div>
							<div class="widget-body">
							 <div class="widget-main">
							 	<div class="row-fluid">
							 		<input type="hidden" name="id" id="id" value="${smsTemplate.id }" />
									<label for="form-field-8">编码</label>
									<input type="text" name="smscode" id="smscode" onchange="hasCode()" value="${smsTemplate.smscode }" placeholder="S001"/> 
								</div>
								<hr />
								<div class="row-fluid">
									<label for="form-field-12">模板名称</label>
									<input type="text" name="smsname" id="smsname" value="${smsTemplate.smsname }" placeholder="航变通知模板"/> 
								</div>
								<hr />
								<div class="row-fluid">
									<label for="form-field-10">自定义模板</label>
									<textarea class="span12" name="template" id="template" placeholder="【\${header}】您预订的\${trainStartDate}发车的\${fromStationName}站-\${toStationName}站\${trainCode}次列车，\${psnCount}张购票成功，取票订单号\${psnSeatOrderInfos}订单已成功出  票，具体请咨询客服电话400-9900118。祝您旅途愉快！"
									 style="height: 120px;">${smsTemplate.template }</textarea>
								</div>
								<hr />
								<div class="row-fluid">
									<label for="form-field-11">模板说明</label>
									<textarea class="span12" name="explain" id="explain" placeholder="ex:【{分期乐}】您预订的{09月03日10:50}发车的{义乌}站-{上海虹桥}站{G1384}次列车，* {1}张购票成功，取票订单号{EC72195948申屠海三二等座04车厢11A号}。* 订单已成功出票，具体请咨询客服电话400-9900118。祝您旅途愉快！" style="height: 120px;">${smsTemplate.explain }</textarea>
								</div>
								<hr />
								<div class="row-fluid">
									<button type="button" class="btn btn-small btn-primary" id="commitBtn" style="float :right;">&nbsp;新&nbsp;增&nbsp;</button>
									<button type="button" class="btn btn-small btn-primary" id="editBtn" style="float :right;">&nbsp;修&nbsp;改&nbsp;</button>
								</div>
							 </div>
							</div>
						</div>
					 </div>
				</div>
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
		$(function() {
			var id = $("#id").val();
			if( "" != id ){
				$("#editBtn").show();
				$("#commitBtn").hide();
				$("#smscode").prop("disabled",true);
			}else{
				$("#commitBtn").show();
				$("#editBtn").hide();
			}
		});
		
		//核对smscode
		function hasCode(){
			var smscode = document.getElementById("smscode").value;
			$.ajax({
			    url:"./smstemplate/checkCode",    //请求的url地址
			    dataType:"json",   //返回格式为json
			    data:{
			    	"smscode":smscode,
			    },    //参数值
			    type:"POST",   //请求方式
			    success:function(flag){
			       if(flag==1){
			    	   document.getElementById("smscode").value="";
			    	   toastr.error('模板编号已经存在');
			    	   return ;
			       }else if(flag==2){
			    	   return ;
				   }
			    }
			});
		}
		
		
		//返回上一层并刷新
		$("#close").click(function(){
			$("#Form").attr("action", "./smstemplate/backPage.do" );
			$("#Form").submit();		
		});
		
		//短信新增
		$("#commitBtn").click(function(){
			var smscode = $("#smscode").val().trim();
			var smsname = $("#smsname").val().trim();
			var template = $("#template").val().trim();
			var explain = $("#explain").val().trim();
			if( !smscode || !smsname || !template || !explain){
				toastr.warning('数据不能为空');
				return ;
			}
			
			bootbox.confirm("确定新增此短信模板吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>smstemplate/saveSms.do";
					$("#Form").attr("action", url );
					$("#Form").submit();
				}
			});
		});
		
		//修改短信模板
		$("#editBtn").click(function(){
			var id = $("#id").val();
			var smscode = $("#smscode").val().trim();
			var smsname = $("#smsname").val().trim();
			var template = $("#template").val().trim();
			var explain = $("#explain").val().trim();
			if( !smscode || !smsname || !template || !explain){
				toastr.warning('数据不能为空');
				return ;
			}
			bootbox.confirm("确定修改此短信模板吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>smstemplate/updateSms.do";
					$("#Form").attr("action", url );
					$("#Form").submit();
				}
			});	
		});
		
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
			        closeButton: true,  //是否显示关闭按钮（提示框右上角关闭按钮）
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

