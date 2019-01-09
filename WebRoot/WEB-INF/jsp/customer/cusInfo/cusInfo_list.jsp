<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
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
	</head>
<body>
	<div class="container-fluid" id="main-container">
	 <div id="page-content" class="clearfix">
	   <div class="row-fluid">
			<form action="customer/cusInfoList" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td>
							<span class="input-icon">
								<input id="InpMobile" autocomplete="off" id="nav-search-input" type="text" name="mobile" value="${pd.mobile}" placeholder="请输入手机号" />
							</span>
						</td>
						<td>
							<span class="input-icon">
								<input id="InpName" autocomplete="off" id="nav-search-input" type="text" name="name" value="${pd.name}" placeholder="请输入客户姓名" />
							</span>
						</td>
					    <td style="vertical-align:top;"> 
						 	<select id="seleIsvite" name="isInvite" id="seleIsrecdid" data-placeholder="是否被邀请" style="vertical-align:top;width: 150px;">
								<option value="1"  selected="<c:if test="${pd.isInvite == 1}">selected</c:if>">被邀请</option>
								<option value="2"  selected="<c:if test="${pd.isInvite == 2}">selected</c:if>">未被邀请</option>
								<option value="0"  selected="<c:if test="${pd.isInvite == 0 or empty pd.isInvite}">selected</c:if>">是否被邀请(全部)</option>
						  	</select>
					    </td>
					    	<td style="vertical-align:top;">
							<input id="cusidhidParam" name="cusid" type="hidden" />
						</td>
						<td style="vertical-align:top;">
							<button class="btn btn-mini btn-light" onclick="search();"  title="检索">
							<i id="nav-search-icon" class="icon-search"></i></button>
						</td>
					</tr>
				</table>
			
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">序号</th>
							<th class="center">用户名</th>
							<th class="center">手机号码</th>
							<th class="center">账户余额</th>
							<th class="center">是否被邀请</th>
							<th class="center">邀请人</th>
							<th class="center">加入时间</th>
							<th class="center">充值记录</th>
							<th class="center">订单记录</th>
							<th class="center">退款</th>
							<th class="center">优惠券</th>
							<th class="center">状态</th>
							<th class="center">激活/冻结</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty cusInfoList}">
							<c:forEach items="${cusInfoList}" var="cusInfo" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
									<td class="center">${cusInfo.name}</td>
									<td class="center">${cusInfo.mobile}</td>
								    <td class="center">${cusInfo.money}</td>
									<td class="center"><!-- 是否被邀请 -->
										<c:choose>
										    <c:when test="${empty cusInfo.recdid}">
										    	<c:out value="否"></c:out>
										    </c:when>
										    <c:otherwise>
										    	<c:out value="是"></c:out>
										    </c:otherwise>
										</c:choose>
									</td>
									<td class="center"><!-- 邀请人 -->
									    <c:if test="${not empty cusInfo.recdid}">
									   		<button   title="邀请人查看查看" class="btn btn-link" onclick="searchByCusId(this);" value="${cusInfo.recdid}">查看邀请人</button>
									    </c:if>
									</td>
									<td class="center"><fmt:formatDate value="${cusInfo.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td class="center">
										<button title="充值记录查看" class="btn btn-link" onclick="selerchCusChargeByCusId(this);" value="${cusInfo.id}" >查看</button>
									</td>
									<td class="center">
										<button title="订单记录查看" class="btn btn-link" onclick="selerchCusOrderByCusId(this);" value="${cusInfo.id}">查看</button>
									</td>
									<td class="center">
										<button title="退款" class="btn btn-link">退款</button>
									</td>
									<td class="center">
										<button title="优惠卷查看" class="btn btn-link" onclick="selerchCusCouponByCusId(this);" value="${cusInfo.id}">查看</button>
									</td> 
									<td class="center"><!-- 状态 -->
									   	<c:choose>
										    <c:when test="${cusInfo.isvalid == 'Y'}">
										    	<c:out value="正常"></c:out>
										    </c:when>
										    <c:otherwise>
										    	<c:out value="黑名单"></c:out>
										    </c:otherwise>
										</c:choose>
									</td>
									<td class="center">
										 	<c:choose>
										    <c:when test="${cusInfo.isvalid == 'Y'}">
										    	<button title="冻结" class="btn btn"  onclick="updateFreezeByCusId(this);" value="${cusInfo.id}">冻结</button>
										    </c:when>
										    <c:otherwise>
										    	<button title="启用" class="btn btn-success"  onclick="updateStartUsingByCusId(this);" value="${cusInfo.id}">启用</button>
										    </c:otherwise>
										</c:choose>
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
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
		
		<script type="text/javascript">
		    $(top.hangge());
			$(function() {
				//下拉框
				$(".chzn-select").chosen(); 
				$(".chzn-select-deselect").chosen({allow_single_deselect:true});
				
				//日期框
				$('.date-picker').datepicker();
				
				//复选框
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox').each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
						
				});
			});
			
			/* 查看邀请人 */
			function searchByCusId(param){
			      $("#InpMobile").val('');
			      $("#InpName").val('');
			      $("#seleIsvite").find("option[value=0]").attr("selected",true); 
			      $("#cusidhidParam").val($(param).val());
			      $("#Form").submit();
			}
			
			/* 检索 */
			function search(){
				top.jzts();
				$("#Form").submit();
			}
			
			/* 冻结该客户 */
			function updateFreezeByCusId(param){
			      updateIsValidAjax($(param).val(),"N");
			}
			
			/* 启用该客户 */
			function updateStartUsingByCusId(param){
			      updateIsValidAjax($(param).val(),"Y");
			}
			
			/* 异步加载更新 */
			function updateIsValidAjax(cusid,isvalid){
               	top.jzts();
                $.ajax({
					url : "./customer/updateCusInfoIsValid",
					data : {
						"cusid":cusid,
						"isvalid":isvalid
					},
					dataType : "json",
					type : "POST",
					success : function(data) {
					    if(data == 'success'){
					    	
					    } else {
					    	
					    }
					    location.reload();
					}
				});	 
		     }
		     
		     /* 根据客户号查询客户充值记录 */
		     function selerchCusChargeByCusId(param){
		    	 var cusid = $(param).val();
		    	 var url = "<%=basePath%>customer/cusChargeList.do?cusid="+ cusid;
		    	 $("#Form").attr("action", url);
		    	 $("#Form").submit();
		     }
		     
		     /* 根据客户号查询客户订单信息记录 */
		     function selerchCusOrderByCusId(param) {
		    	 var cusid = $(param).val();
		    	 var url = "<%=basePath%>customer/cusOrderList.do?cusid="+ cusid;
		    	 $("#Form").attr("action", url);
		    	 $("#Form").submit(); 
		     }
		     
		     /* 根据客户号查询客户优惠券信息信息记录 */
		     function selerchCusCouponByCusId(param) {
		    	 var cusid = $(param).val();
		    	 var url = "<%=basePath%>customer/cusCouponList.do?cusid="+ cusid;
		    	 $("#Form").attr("action", url);
		    	 $("#Form").submit(); 
		     }
		</script>
	</body>
</html>

