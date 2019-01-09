<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="/static/common/jsp/common.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/jpoa/controlplatfrom/dmandetails/css/dmanDetails.css?r=<%=new Random().nextInt() %>" />
		<style type="text/css"> 
			html{height:100%}
			body{height:100%;margin:0px;padding:0px}
			#container{height:100%}
		</style>
	</head>  
 
	<body class='bodydmandetails'>
		<div id="container"></div>
		
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" data-backdrop="false"  style="width:530px;  margin-left: 500px; margin-bottom: 300px;" >
	         <div class="modal-dialog">
	             <div class="modal-content" >
	                 <div class="modal-header">
	                 	 <table>
	                 	 	<tr>
	                 	 		<td id="dmanname"></td>
	                 	 		<td id="count"></td>
	                 	 	</tr>
	                 	 	<tr style="height: 5px"/>
	                 	 	<tr>
	                 	 		<td><input autocomplete="off" type="text" name="orderno" id="orderno" placeholder="输入订单号后4位" style="width: 130px" value="${pd.orderno}"/></td>
	                 	 		<td> <input autocomplete="off" type="text" name="countdown" id="countdown" placeholder="倒计时时间筛选(min)" style="width: 130px" value="${pd.orderno}"/></td>
	                 	 		<td>
                 	 			 <select id="isfinish" style="width: 130px">
			                	 	<option value="ONGOING">进行中</option>
			                	 	<option value="FINISHED">已完成</option>
			                	 </select>
	                 	 		</td>
	                 	 		<td>&nbsp;&nbsp;&nbsp;&nbsp;<i id="nav-search-refresh" class="icon-refresh"></i></td>
	                 	 	</tr>
	                 	 </table>
	                 </div>
	                 
	                 <div class="modal-body" style=" max-height:700px;overflow-y:auto;overflow-x:hidden;">
	                 	<div id="dmanDetails">
	                 	</div>
	                 </div>
	                 <div class="modal-footer">
	                 </div>
	             </div><!-- /.modal-content -->
	         </div><!-- /.modal -->
		</div>
		
		<c:import url="./orderAlloc.jsp" />
		
		<input type="hidden" value="${pd.dmanuserid}" id="dmanuserid">
		<input type="hidden" value="${pd.loginperson_city_lng}" id="loginperson_city_lng">
		<input type="hidden" value="${pd.loginperson_city_lat}" id="loginperson_city_lat">
		
		<script type="text/javascript" src="${ctx}/jpoa/common/js/baidu.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/common/js/date.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/common/js/check.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/dmandetails/js/dmanDetails.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/dmandetails/js/orderAlloc.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/common/js/consolecommon.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>