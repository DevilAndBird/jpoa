<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/static/common/jsp/common.jsp"%>
  
  </head>
  
  <style type="text/css"> 
  </style>
		
  <body>
	  	<div class="layui-tab layui-tab-brief" >
		  <ul  class="layui-tab-title">
		    <li id='dmanlist' onclick='dmanlist(this)' class="layui-this">取派员列表</li>
		    <li onclick='selfMotonAlloc(this)'>自动分配列表<span id='selfMotonAlloc' class="layui-badge">0</span></li>
		    <li onclick='manualAllocation(this)'>待人工分配列表<span id='manualAllocation' class="layui-badge">0</span></li>
		    <li onclick='feedback(this)'>订单反馈列表<span id='feedback' class="layui-badge">0</span></li>
		  </ul>
		</div>     
  </body>
  
  <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
  <script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/tab/js/console_tab.js?r=<%=new Random().nextInt() %>"></script>

</html>
