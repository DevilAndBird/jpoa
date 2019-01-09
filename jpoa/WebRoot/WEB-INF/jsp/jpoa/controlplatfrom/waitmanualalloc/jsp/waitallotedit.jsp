<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="/static/common/jsp/common.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/jpoa/controlplatfrom/waitmanualalloc/css/waitallotedit.css?r=<%=new Random().nextInt() %>" />
	</head>  
 
	<body class='bodywaitallotedit'>
		<div id='divfillorderinfolist'>
		</div>
		
		<hr/>
		
		<h4>选择取派员:</h4>
		<div id ='divdmanlist'>
		</div>
		
	    <div id ='divbutton'>
	    	<button title="点击订单进入自动分配列表" id='btnallotactivefinish' class="layui-btn layui-btn-radius layui-btn-danger">分派完成</button>
	    	<button id='btnsaveactive' class="layui-btn layui-btn-radius layui-btn-primary">确认分配取派员</button>
		</div>
		
		<hr/>
		
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
	    
	     <script type="text/javascript" src="${ctx}/jpoa/common/js/layuicommon.js?r=<%=new Random().nextInt() %>"></script>
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/waitmanualalloc/js/waitallotedit.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>