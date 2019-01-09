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
	<style> 
		#model{ margin:0 500px; width:1600px;} 
		/* css注释：为了观察效果设置宽度 边框 高度等样式 */ 
	</style> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form method="post" name="Form" id="Form">
			<input type="hidden" name="money" id="money" /> 
			<input type="hidden" name="type" id="type" /> 
			<!-- 检索  -->
			<div id="zhongxin">
				<div id="model">
					 <div class="span4" id="span4">
						<div class="widget-box">
							<div class="widget-header">
								<h4>优惠卷配置</h4>
								<span class="widget-toolbar">
									<span id="close"><i class="icon-remove"></i></span>
								</span>
							</div>
							<div class="widget-body">
							 <div class="widget-main">
							 	<div class="row-fluid">
									<label for="form-field-8">编码</label>
									<input type="text" name="code" id="code" onchange="hasCode()" placeholder="001"/> 
								</div>
								<hr />
								<div class="row-fluid">
									
									<div class="controls" >
									<label class="control-label">类型</label>
										<label style="float:left">
											<input name="form-field-radio" type="radio" name="radio" id="radio1" value="radio1"/>
											<span class="lbl">
												<div style="margin-top: -18.5px;margin-left: 17px" >直减金额</div>
											</span>
										</label>
										<label style="float:left;">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input name="form-field-radio" type="radio" name="radio" id="radio2" value="radio2"/>
											<span class="lbl">
												<div style="margin-top: -18.5px;margin-left: 17px" >满减金额</div>
											</span>
											<!-- <span class="lbl"><b> 满减金额</b></span> -->
										</label>
										<label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input name="form-field-radio" type="radio" name="radio" id="radio3" value="radio3"/>
											<span class="lbl">
												<div style="margin-top: -18.5px;margin-left: 17px" >折扣</div>
											</span>
											<!-- <span class="lbl"><b> 折扣</b></span> -->
										</label>
									</div>
									<div id="divtype1" style="margin-top: 15px;">
										<input type="text" class="span4" name="money1" id="money1" placeholder="直减 :50"/>
									</div> 
									<div id="divtype2" style="display:none;margin-top: 15px;">
										<input type="text" class="span2" name="fullmoney" id="fullmoney" placeholder="满 :100"/>
										<input type="text" class="span2" name="money2" id="money2" placeholder="减  :10"/>
									</div>
									<div id="divtype3" style="display:none;margin-top: 15px;">
										<input type="text" class="span4" name="discount" id="discount" placeholder="折扣 :8.5"/>
									</div>
								</div>
								<hr />
								<div class="row-fluid">
									<label for="form-field-10">目标用户</label>
									<!-- <input type="text" name="channel" id="channel" placeholder=""/>  -->
									<select class="chzn-select" name="channel" id="channel" data-placeholder="目标用户" style="vertical-align:top;width: 151px;">
										<option value="1">手工指定用户优惠券</option>
										<option value="2">新用户优惠券</option>
										<option value="3">微信注册渠道用户优惠券</option>
										<option value="4">携程优惠券</option>
								  	</select>
								</div>
								<hr />
								<div class="row-fluid">
									<label for="form-field-11">开始日期</label>
									<!-- <input type="text" name="startdate" id="startdate" placeholder=""/>  -->
									<input class="span10 date-picker span4" name="startdate" id="startdate" type="text" data-date-format="yyyy-mm-dd" placeholder="2018-01-01" />
									<div class="input-append bootstrap-timepicker">
										<input id="starttime" name="starttime" type="text" class="input-small" />
										<span class="add-on"><i class="icon-time"></i></span>
									</div>
								</div>
								<hr />
								<div class="row-fluid">
									<label for="form-field-11">有效日期</label>
									<!-- <input type="text" name="invaliddate" id="invaliddate" placeholder=""/>  -->
									<input class="span10 date-picker span4" name="invaliddate" id="invaliddate" type="text" data-date-format="yyyy-mm-dd" placeholder="2018-03-01"/>
									<div class="input-append bootstrap-timepicker">
										<input id="invalidtime" name="invalidtime" type="text" class="input-small" />
										<span class="add-on"><i class="icon-time"></i></span>
									</div>
								</div>
								<hr />
								<div class="row-fluid">
									<label for="form-field-11">数量</label>
									<input type="text" name="leftnum" id="leftnum" placeholder="100"/> 
								</div>
								<hr />
								<div class="row-fluid">
									<button type="button" class="btn btn-small btn-primary" id="createBtn" style="float :right;">&nbsp;生&nbsp;成&nbsp;</button>
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
		<script type="text/javascript" src="static/js/bootstrap-timepicker.min.js"></script>
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			$("#radio1").attr("checked","checked");
			$("#divtype1").show();
			$("#divtype2").hide();
			$("#divtype3").hide();
		});
		
		$("#radio1").click(function(){
			$("#divtype1").show();
			$("#divtype2").hide();
			$("#divtype3").hide();
		});
		$("#radio2").click(function(){
			$("#divtype1").hide();
			$("#divtype2").show();
			$("#divtype3").hide();
		});
		$("#radio3").click(function(){
			$("#divtype1").hide();
			$("#divtype2").hide();
			$("#divtype3").show();
		});		
		
		//核对code
		function hasCode(){
			var code = document.getElementById("code").value.trim();
			if(code==''){
				document.getElementById("code").value="";
				return ;
			}
			$.ajax({
			    url:"./coupon/checkCode",    //请求的url地址
			    dataType:"json",   //返回格式为json
			    data:{
			    	"code":code,
			    },    //参数值
			    type:"POST",   //请求方式
			    success:function(data){
			       if(data==1){
			    	   document.getElementById("code").value="";
			    	   toastr.error('优惠劵编码已经存在');
			    	   return ;
			       }else if(data==2){
			    	   return ;
				   }
			    }
			});
		}
		
		
		//返回上一层并刷新
		$("#close").click(function(){
			$("#Form").attr("action", "./coupon/backPage.do" );
			$("#Form").submit();		
		});
		
		//生成优惠劵
		$("#createBtn").click(function(){
			var code = $("#code").val().trim();
			var money1 = $("#money1").val().trim();
			var money2 = $("#money2").val().trim();
			var fullmoney = $("#fullmoney").val().trim();
			var discount = $("#discount").val().trim();
			var startdate = $("#startdate").val().trim();
			var invaliddate = $("#invaliddate").val().trim();
			var leftnum = $("#leftnum").val().trim();
			
			if( startdate == '' || invaliddate == '' ){
				toastr.warning('数据不能为空');
				return ;
			}
			var re = /^[0-9]*[1-9][0-9]*$/ ;  
			if(!re.test(leftnum)){
				toastr.warning('数量请输入正整数');
				return ;
			}
			if( code=='' ){
				toastr.warning('编码不能为空');
				return ;
			}
			var item = $(":radio:checked"); 
			var len=item.length; 
			if(len>0){ 
				var radio = $(":radio:checked").val();
				if( radio=='radio1' ){
					$("#type").val("directreduce");
					if( money1 == ''){
						toastr.warning('直减金额不能为空');
						return ;
					}else{
						$("#money").val(money1);
					}
				}
				if( radio=='radio2' ){
					$("#type").val("fullreduce");
					if( fullmoney == '' || money2 == '' ){
						toastr.warning('满减金额不能为空');
						return ;
					}else{
						$("#money").val(money2);
					}
				}
				if( radio=='radio3' ){
					$("#type").val("discount");
					if( discount == ''){
						toastr.warning('折扣不能为空');
						return ;
					}
				}
			} 
			bootbox.confirm("确认生成优惠劵吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>coupon/createCoupon.do";
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
			/* $('.timepicker1').datetimepicker({
			    format: 'yyyy-mm-dd hh:ii',
			    autoclose: true,
			    minView: 0,
			    minuteStep:1
			}); */
			
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
			
			$('.input-small').timepicker({
				format: 'HH:mm',
				minuteStep: 1,
				showSeconds: true,
				showMeridian: false
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

