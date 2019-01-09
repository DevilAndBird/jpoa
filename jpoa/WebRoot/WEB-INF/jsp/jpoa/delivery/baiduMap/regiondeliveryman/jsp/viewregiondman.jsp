<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="../../common/jsp/top.jsp"%>
		
		<style type="text/css"> 
			html{height:100%}
			body{height:100%;margin:0px;padding:0px}
			#container{height:100%}
		</style>
	</head>  
 
	<body>
		<div id="container"></div>
		
		<script type="text/javascript" src="${ctx}/jpoa/delivery/baiduMap/common/js/BAIDUMAP_ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/delivery/baiduMap/regiondeliveryman/js/viewregiondman.js?r=<%=new Random().nextInt() %>"></script>
		
		<script type="text/javascript"> 
	     	 $(function(){
	     		viewregiondmaninit();
	     	 });
		</script>
	</body>  
</html>