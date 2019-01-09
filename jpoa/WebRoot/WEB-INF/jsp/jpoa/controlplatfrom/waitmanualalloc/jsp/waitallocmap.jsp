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
		<table style="background-position: 20px;"></table>
		<input type="hidden" value="${pd.loginperson_city_lng}" id="loginperson_city_lng">
		<input type="hidden" value="${pd.loginperson_city_lat}" id="loginperson_city_lat">
		
		<script type="text/javascript" src="${ctx}/jpoa/common/js/baidu.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/waitmanualalloc/js/waitallocmap.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/common/js/consolecommon.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>