﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<form action="district/districtList.do" method="post" name="Form" id="Form">
			<input type="hidden" name="id" id="id"/>
			<input type="hidden" name="isvalidV" id="isvalidV"/>
			<table>
				<tr>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select center" name="provID" id="provID" data-placeholder="省份" style="vertical-align:top;width: 120px;">
							<option value="-1" selected="selected">--省份--</option>
							<c:forEach items="${provList}" var="obj" varStatus="vs">
								<option value="${obj.id }" <c:if test="${obj.id==pd.provID }">selected</c:if>>${obj.name }</option>
							</c:forEach>
					  	</select>
					</td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select center" name="cityID" id="cityID" data-placeholder="省份" style="vertical-align:top;width: 120px;">
							<option value="-1" selected="selected">--城市--</option>
							<c:forEach items="${cityList}" var="obj" varStatus="vs">
								<option value="${obj.id }" <c:if test="${obj.id==pd.cityID }">selected</c:if>>${obj.name }</option>
							</c:forEach>
					  	</select>
					</td>
					<td>
						<input autocomplete="off" type="text" name="name" id="name" value="${pd.name }" placeholder="区县名称" />
					</td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="isvalid" id="isvalid" data-placeholder="状态" style="vertical-align:top;width: 120px;">
							<option value=""></option>
							<option value="-1" selected="selected">--状态--</option>
							<option value="1" <c:if test="${pd.isvalid=='1'}">selected</c:if>>开启</option>
							<option value="2" <c:if test="${pd.isvalid=='2'}">selected</c:if>>禁用</option>
					  	</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search">检 索</i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>序号</th>
						<th>编码</th>
						<th>地区名称</th>
						<th>上级地区(市)</th>
						<th>上级地区(省)</th>
						<th>状态</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty districtList}">
						<c:forEach items="${districtList}" var="obj" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td class='center' style="width: 30px;">${obj.id}</td>
								<td><b>${obj.name }</b></td>
								<td><b>${obj.cityName }</b></td>
								<td><b>${obj.provName }</b></td>
								<td>
									<c:if test="${obj.isvalid==1 }">
										<b>开启</b>
									</c:if>
									<c:if test="${obj.isvalid==2 }">
										禁用
									</c:if>
									
								</td>
								<td class="center" style="width: 120px;">
									<a title="编辑" class='btn btn-mini btn-info updateDistrict'><i class='icon-edit'></i></a>
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
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" id="districtAdd">新增区县</a></td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		
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
				<label for="form-field-12">区县ID</label>
				<input type="text" name="modelId" id="modelId" value="" onchange="checkID()" placeholder="1"/>
			</div>
			<hr />
			<div class="row-fluid">
				<label for="form-field-12">省份ID</label>
				<div id="upID"><input type="text" name="modelProvID" id="modelProvID" value=""/></div>
				<div id="addID"><select class="chzn-select" name="modelProvID" id="modelProvID" data-placeholder="省份" style="vertical-align:top;width: 120px;">
					<c:forEach items="${provList}" var="obj" varStatus="vs">
						<option value="${obj.id }" <c:if test="${obj.id==pd.modelProvV }">selected</c:if>>${obj.name }</option>
					</c:forEach>
			  	</select></div>
			</div>
			<hr />
			<div class="row-fluid">
				<label for="form-field-12">城市ID</label>
				<div id="upCityID"><input type="text" name="modelCityID" id="modelCityID" value=""/></div>
				<div id="addCityID"><select class="chzn-select" name="modelCityID" id="modelCityID" data-placeholder="城市" style="vertical-align:top;width: 120px;">
					<c:forEach items="${cityList}" var="obj" varStatus="vs">
						<option value="${obj.id }" <c:if test="${obj.name==pd.modelCityV }">selected</c:if>>${obj.name }</option>
					</c:forEach>
			  	</select></div>
			</div>
			<hr />
			<div class="row-fluid">
				<label for="form-field-12">区县名称</label>
				<input type="text" name="modelName" id="modelName" value="" placeholder="金安区"/> 
			</div>
			<hr />
			<div class="row-fluid">
				<label for="form-field-10">状态</label>
				<label style="float:left">
					<input name="form-field-radio" type="radio" name="modelIsvalid" id="radio1" value="1" />
					<span class="lbl">
						<div style="margin-top: -18.5px;margin-left: 17px" >开启</div>
					</span>
				</label>
				<label style="float:left;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="form-field-radio" type="radio" name="modelIsvalid" id="radio2" value="2" />
					<span class="lbl">
						<div style="margin-top: -18.5px;margin-left: 17px" >禁用</div>
					</span>
				</label>
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
		function chgIsvalid(id,isvalid){
			if(isvalid==1){
				$("#isvalidV").val("2");
			}else{
				$("#isvalidV").val("1");
			}
			$("#id").val(id);
			Ewin.confirm({ message: "确定要修改状态嘛吗？" }).on(function (e) {
				if(!e){
					window.location.reload();
					return ;
				}
				
				var url = "<%=basePath%>prov/chgStatus.do";
				$("#Form").attr("action", url );
				$("#Form").submit();
				
			});

		}
		
		//核对ID是否存在
		function checkID(){
			var id = document.getElementById("modelId").value;
			if( id.trim() == '' ){
				document.getElementById("modelId").value="";
				return ;
			}
			$.ajax({
				url:"district/checkID.do",
				dataType:"json",   //返回格式为json
				data:{
					"id":id
				},
				success:function(flag){
			       if(flag==1){
			    	   document.getElementById("modelId").value="";
			    	   toastr.error('城市ID已经存在');
			    	   return ;
			       }else if(flag==2){
			    	   return ;
				   }
			    }
			});
		}
		
		//添加城市信息
		$("#districtAdd").click(function(){
			$("#h4").text("区县新增");
			$("#saveBtn").text("新  增");
			$("#modelId").prop("disabled",false);
			$("#modelId").val("");
			$("#modelName").val("");
			
			document.getElementById("addID").style.display="block";//显示
			document.getElementById("upID").style.display="none";//隐藏
			
			document.getElementById("addCityID").style.display="block";//显示
			document.getElementById("upCityID").style.display="none";//隐藏
			
			$('#radio1').prop("checked",false);
			$('#radio2').prop("checked",false);
			$("#saveDiv").show();
			$("#updateDiv").hide();
		    $('#myModal').modal();
		});
		
		$("#saveBtn").click(function(){
			var modelId = $("#modelId").val().trim();
			var modelName = $("#modelName").val().trim();
			var isvalidV=$('input:radio:checked').val();
			if( modelId == '' ){
				$("#modelId").val("");
				toastr.warning('请输入数据');
				return ;
			}
			if( modelName == '' ){
				$("#modelName").val("");
				toastr.warning('请输入数据');
				return ;
			}
			if( typeof(isvalidV)=="undefined" ){
				toastr.warning('请选择状态');
				return ;
			}
			$("#isvalidV").val(isvalidV);
			var url = "<%=basePath%>district/insertDistrict.do";
			$("#Form").attr("action", url );
			$("#Form").submit();
		});
		
		
		//修改区县信息
		$(".updateDistrict").click(function(){
			var s=$(this).parent().parent().find("td");
			
			$("#modelId").val(s.eq(1).text());
			$("#modelName").val(s.eq(2).text());
			$("#modelCityID").val(s.eq(3).text());
			$("#modelProvID").val(s.eq(4).text());
			
			if( s.eq(5).text().indexOf("开启")>0  ){
				$('#radio1').prop("checked",true);
			}else{
				$('#radio2').prop("checked",true);
			}
			
			document.getElementById("upID").style.display="block";//显示
			document.getElementById("addID").style.display="none";//隐藏
			
			document.getElementById("upCityID").style.display="block";//显示
			document.getElementById("addCityID").style.display="none";//隐藏
			
			$("#h4").text("区县修改");
			$("#updateBtn").text("修  改");
			$("#modelId").prop("disabled",true);
			$("#modelProvID").prop("disabled",true);
			$("#modelCityID").prop("disabled",true);
			$("#saveDiv").hide();
			$("#updateDiv").show();
			$('#myModal').modal();
			
		});
		$("#updateBtn").click(function(){
			var modelId = $("#modelId").val().trim();
			var modelName = $("#modelName").val().trim();
			var modelCityID = $("#modelCityID").val().trim();
			var modelProvID = $("#modelProvID").val().trim();
			var isvalidV=$('input:radio:checked').val();
			if( modelId == '' ){
				$("#modelId").val("");
				toastr.warning('请输入数据');
				return ;
			}
			if( modelName == '' ){
				$("#modelName").val("");
				toastr.warning('请输入数据');
				return ;
			}
			if( typeof(isvalidV)=="undefined" ){
				toastr.warning('请选择状态');
				return ;
			}
			
			$.ajax({
				url:"district/updateDistrict.do",
				dataType:"json",   //返回格式为json
				data:{
					"modelId":modelId,
					"isvalidV":isvalidV,
					"modelName":modelName,
					"modelProvID":modelProvID,
					"modelCityID":modelCityID,
				},
				success:function(flag){
					if(flag==1){
			    	   document.getElementById("modelName").value="";
			    	   toastr.error('地区名称已经存在');
			    	   return ;
			       	}else if(flag==2){
			    	   location.href="./district/districtList.do";
			       	}  
			    }
			});
			
		});
		
		//删除
		function delProv(id){
			Ewin.confirm({ message: "确定要删除吗？" }).on(function (e) {
				if(!e){
					return ;
				}
				var url = "<%=basePath%>prov/delProv.do?idV="+id;
				$("#Form").attr("action", url );
				$("#Form").submit();
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

