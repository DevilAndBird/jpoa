<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/static/common/jsp/common.jsp"%>
  	<link rel="stylesheet" type="text/css" href="${ctx}/jpoa/controlplatfrom/dmandetails/css/addrselect.css?r=<%=new Random().nextInt() %>" />
  </head>
  
  <body>
  	  <div>
		  <div class='div_addr_query_body'>
		 	<input type="text" class='inp_addr_query' style="width: 90%;height: 29px;border: 0">
		 	<div class='div_addr_query_reset'>
		 		<span class="layui-badge-rim">X</span>
		 	</div>
		  </div>
		  <div class='div_addr_query_close'>取消</div>
	  </div>
	  <div class='hotelorresidence'>
	  	<div class='div_HOTEL on'>酒店</div>
	  	<div class='div_RESIDENCE'>住宅</div>
	  </div>
	  <div id="showform" style="width: 100%;max-height: 340px;overflow-y:auto;">
      </div>
  </body>
  
  <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
  <script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/dmandetails/js/addrselect.js?r=<%=new Random().nextInt() %>"></script>
</html>
