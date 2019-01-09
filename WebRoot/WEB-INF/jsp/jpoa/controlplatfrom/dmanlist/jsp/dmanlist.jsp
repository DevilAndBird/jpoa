<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="/static/common/jsp/common.jsp"%>
		
		<style type="text/css"> 
			html{height:100%}
			body{height:100%;margin:0px;padding:0px}
			#container{height:100%}
		</style>
	</head>  
 
	<body>
		<div id="container"></div>
		
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" data-backdrop="false"  style="width:400px;  margin-left: 600px; margin-bottom: 300px;" >
	         <div class="modal-dialog">
	             <div class="modal-content" >
	                 <div class="modal-header">
						 <input autocomplete="off" type="text" name="dmanname" id="dmanname" placeholder="搜索司机姓名" style="width: 300px" value="${pd.dmanname}"/>
	                	 &nbsp;&nbsp;&nbsp;&nbsp;
	                	 <i id="nav-search-refresh" class="icon-refresh"></i>
	                 </div>
	                 
	                 <div class="modal-body" style=" max-height:700px;overflow-y:auto;overflow-x:hidden;">
	                 	<div id="dmanList">
	                 	</div>
	                 </div>
	                 <div class="modal-footer">
	                 </div>
	                 
	             </div><!-- /.modal-content -->
	         </div><!-- /.modal -->
		</div>
		<input type="hidden" value="${pd.loginperson_city_lng}" id="loginperson_city_lng">
		<input type="hidden" value="${pd.loginperson_city_lat}" id="loginperson_city_lat">
		
		<script type="text/javascript" src="${ctx}/jpoa/common/js/baidu.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/dmanlist/js/dmanlist.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/common/js/consolecommon.js?r=<%=new Random().nextInt() %>"></script>
		
	</body>
</html>