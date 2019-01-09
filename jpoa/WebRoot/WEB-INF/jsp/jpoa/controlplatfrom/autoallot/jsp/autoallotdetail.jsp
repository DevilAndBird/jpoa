<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="/static/common/jsp/common.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/jpoa/controlplatfrom/autoallot/css/autoallotdetail.css?r=<%=new Random().nextInt() %>" />
	</head>  
 
	<body class="bodyautoallotdetail">
		<div id='divautoallotbaseinfo'>
			<table id='tableautoallottbaseinfo'>
				<tr>
					<td id='ordertypedesc'></td>
					<td id='ordersuffix'></td>
					<td id='orderlug'></td>
					<td id="mailingway"></td>
					<td id='cusname'></td>
					<td id='cusmobile'></td>
				</tr>
			</table>
		</div>
		<div id ='divAutoallotdetail'>
		</div>
		
		<button onclick="allotdman()" class="layui-btn layui-btn-radius layui-btn-primary">点击分配取派员</button>
		<button onclick="refreshautoallotdetail()" class="layui-btn layui-btn-radius layui-btn-danger">刷新列表</button>
	    
	    <input type='hidden' value='${pd.orderid}' id='orderid' >
	    
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/date.js?r=<%=new Random().nextInt() %>"></script>
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/autoallot/js/autoallotdetail.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>