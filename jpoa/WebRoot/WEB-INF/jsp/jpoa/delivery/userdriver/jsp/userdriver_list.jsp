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
		<!-- 检索  -->
		<form action="userDriver/findUserDriverlistPage" method="post" name="Form" id="Form">
		<!-- <input type="hidden" name="id" id="id" />
		<input type="hidden" name="isvalid" id="isvalid" /> -->
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input type="text" name="name" value="${pd.name}" autocomplete="off" placeholder="请输入登陆用户名" />
						</span>
					</td>
					<td>
						<span class="input-icon">
							<input type="text" name="idno" value="${pd.idno}" autocomplete="off"  placeholder="请输入身份证号" />
						</span>
					</td>
					<td>
						<span class="input-icon">
							<input type="text" name="mobile" value="${pd.mobile}" autocomplete="off"  placeholder="请输入手机号" />
						</span>
					</td>
					<td style="vertical-align:top;">
						<button class="btn btn-mini btn-light" onclick="search()" title="检索">
							<i id="nav-search-icon" class="icon-search">检 索</i>
						</button>
					</td>
				</tr>
			</table>
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class='center'>序号</th>
						<th class='center'>姓名</th>
						<th class='center'>身份证</th>
						<th class='center'>手机号码</th>
						<th class='center'>状态</th>
						<th class='center'>加入时间</th>
						<th class='center'>修改时间</th>
						<th class='center'>操作</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty userDriverlistPage}">
						<c:forEach items="${userDriverlistPage}" var="obj" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td class="center">${obj.name}</td>
								<td class="center">${obj.idno}</td>
								<td class="center">${obj.mobile}</td>
								<td class="center">
									 ${obj.isvalidDesc}
									 <input type="hidden" value="${obj.isvalid}" />
								 </td>
								<td class="center"><fmt:formatDate value="${obj.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="center"><fmt:formatDate value="${obj.modifytime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="center">
									<button onclick="fillUserDriver_modal(this)" class="btn btn-link" data-toggle="modal" data-target="#myModal" title="编辑" class="btn btn-link" value="${obj.id}">编辑</button>
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
						<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
			</div>
			
			
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4UserDriver" class="modal-title">编辑司机人员信息</h4>
                </div>
                <div class="modal-body">
					<div class="row-fluid">
						<input type="text" name="name_modal" id="inpName_modal" placeholder="司机人员名称"/> 
					</div>
					<div class="row-fluid">
						<input type="text" name="idno_modal" id="inpIdno_modal" placeholder="司机身份证件号"/> 
					</div>
					<div class="row-fluid">
						<input type="text" name="mobile_Modal" id="inpMobile_modal" placeholder="司机手机号"/> 
					</div>
					<div class="row-fluid">
						<select name="isvalid_modal" id="seleIsvalid_modal" data-placeholder="是否可用" style="vertical-align:top;width: 120px;">
							<option value="-1" selected="selected">--是否有效--</option>
						</select>
					</div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="btnUpdateUserDriver" type="button" class="btn btn-primary">更改</button>
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
		<script type="text/javascript" src="${ctx}/jpoa/delivery/userdriver/js/userdriver_list.js?r=<%=new Random().nextInt()%>"></script>
		
		<script type="text/javascript">
			$(function() {
				userDriver_list.init();
			});
		</script>
		
		
		
		<%-- <script type="text/javascript">
		$("#createUser").click(function(){
			$("#h4").text("班车司机新增");
			document.getElementById("addName").style.display="block";//隐藏
			document.getElementById("upName").style.display="none";//隐藏
			
			document.getElementById("addPID").style.display="block";//隐藏
			document.getElementById("upPID").style.display="none";//隐藏
			
			document.getElementById("addCID").style.display="block";//隐藏
			document.getElementById("upCID").style.display="none";//隐藏
			$("#saveBtn").text("新  增");
		    $('#myModal').modal();
		});
		$("#saveBtn").click(function(){
			var modelName = $("#amodelName").val().trim();
			var modelProvID = $("#amodelProvID").val().trim();
			var modelCityID = $("#amodelCityID").val().trim();
			var modelIdno = $("#modelIdno").val().trim();
			var isvalidV=$('input:radio:checked').val();
			if( !modelIdno ){
				toastr.warning('请输入数据');
				return ;
			}
			if( typeof(isvalidV)=="undefined" ){
				toastr.warning('请选择状态');
				return ;
			}
			$.ajax({
				url:"userDriver/insertUserDriver.do",
				dataType:"json",   //返回格式为json
				data:{
					"modelName":modelName,
					"modelProvID":modelProvID,
					"modelCityID":modelCityID,
					"modelIdno":modelIdno,
					"isvalid":isvalidV,
				},
				success:function(flag){
			       if(flag==1){
			    	   location.href="./userDriver/list.do";
			       }else if(flag==2){
			    	   toastr.error('班车司机已经存在');
			    	   return ;
				   }
			    }
				
			});
		});
		
		//修改
		$(".updateUser").click(function(){
			var s=$(this).parent().parent().find("td");
			$("#id").val(s.eq(1).text());
			if( s.eq(6).text().indexOf("禁用")>0  ){
				$('#radio3').prop("checked",true);
			}else{
				$('#radio4').prop("checked",true);
			}
			$("#h41").text("班车司机修改");
			$("#updateBtn1").text("修  改");
			$("#saveDiv1").hide();
			$("#updateDiv1").show();
			$('#myModal1').modal();
			
		});
		$("#updateBtn1").click(function(){
			var isvalidV=$('input:radio:checked').val();
			$("#isvalid").val(isvalidV);
			var url = "<%=basePath%>userDriver/updateStatus.do";
			$("#Form").attr("action", url );
			$("#Form").submit();
		});
		
		</script> --%>
	</body>
</html>

