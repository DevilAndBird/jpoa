<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/static/common/jsp/common.jsp"%>
  
  </head>
  
  <body>
  	
  	<input style="width:100%;height: 30px;margin-top:9px " type="text" id="arrivedtime" name="arrivedtime" autocomplete="off" placeholder="请输入最迟到达时间"/>
	<button style="width:100%;height: 30px;margin-top:9px " id="editdmanAllocDetails" onclick="editdmanAllocDetails(this)"	class="layui-btn layui-btn-fluid">修改</button>
  	
  	<input type='hidden' id ='orderid' value='${pd.orderid}'>
  	<input type='hidden' id ='timeType' value='${pd.timeType}'>
  </body>
  
  <script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
  <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
  <script type="text/javascript" src="${ctx}/jpoa/order/ordermain/js/modifytime.js?r=<%=new Random().nextInt() %>"></script>
	 
</html>
