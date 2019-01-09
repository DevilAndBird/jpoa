<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="/static/common/jsp/common.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/jpoa/controlplatfrom/autoallot/css/autoallotdetailedit.css?r=<%=new Random().nextInt() %>" />
	</head>  
 
	<body class='bodywaitallotedit'>
		<div id='divfillautoallotdetailedit'>
		</div>
		
		<hr/>
		
		<h4><span class="layui-badge layui-bg-gray">更改取派员</span></h4>
		
		<div id ='divdmanlist'>
		</div>
		
		<button  id="ichangedman" class="layui-btn layui-btn-radius">确认更改取派员</button>
	    
	    <input type="hidden" id='orderid' value='${pd.orderid}'>
	    <input type="hidden" id='dmanuserid' value="${pd.dmanuserid}">
	    <input type="hidden" id='changedmanuserid'>
	    
	    <input type ='hidden' id= 'taskid'/>
	    <input type ='hidden' id= 'sendid'/>
	    
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/date.js?r=<%=new Random().nextInt() %>"></script>
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/autoallot/js/autoallotdetailedit.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>