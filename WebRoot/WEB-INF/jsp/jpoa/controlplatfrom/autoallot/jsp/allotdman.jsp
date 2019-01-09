<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="/static/common/jsp/common.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/jpoa/controlplatfrom/autoallot/css/allotdman.css?r=<%=new Random().nextInt() %>" />
	</head>  
 
	<body class='bodywaitallotedit' >
		<div id='divfillorderinfolist'></div>
		
		<hr/>
		
		<h4>选择取派员:</h4>
		<div id ='divdmanlist'>
		</div>
		
		
		<!-- <h4>动作指派:</h4>
		<div id ='divactiveallot'>
			<select id="seletRoleType" name="seletRoleType" onchange="orderTypeCharge()" title="订单动作"></select>
			<select id="transitCenter" name="transitCenter" title="集散中心"></select>
			<select id="serviceCenter" name="serviceCenter" title="柜台中心"></select>
		</div>
		<hr/>
		
		<h4>最迟到达时间：</h4>
		<div id ='divaddivedtime' class="layui-inline"> 注意：这一层元素并不是必须的
		  <input type="text" class="layui-input" id="inputaddrivedtime">
		</div>-->
	    <div id ='divbutton'>
	    	<button id='btnsaveactive' class="layui-btn layui-btn-radius layui-btn-primary">确认添加</button>
		</div> 
		
		<hr/>
		
		<h4>动作展示:</h4>
		<div id='divlookaction'>
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">处理人</th>
						<th class="center">处理人电话</th>
						<th class="center">处理人类型</th>
						<th class="center">角色动作类型</th>
						<th class="center">角色动作是否完成</th>
						<th class="center">目的地</th>
						<th class="center">最迟到达时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
				<tbody id='tbodyactionlistfill'>
				</tbody>
			</table>
		</div>
	    
	    <input type="hidden" id='orderid' value='${pd.orderid}'>
	    <input type="hidden" id='dmanuserid'>
	    
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/baidu.js?r=<%=new Random().nextInt() %>"></script>
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/layuicommon.js?r=<%=new Random().nextInt() %>"></script>
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
	  <%--   <script type="text/javascript" src="${ctx}/jpoa/common/js/baseoperation.js?r=<%=new Random().nextInt() %>"></script> --%>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/autoallot/js/allotdman.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>