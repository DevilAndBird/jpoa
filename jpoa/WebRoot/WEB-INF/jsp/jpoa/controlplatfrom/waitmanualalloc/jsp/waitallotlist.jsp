<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="/static/common/jsp/common.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/jpoa/controlplatfrom/waitmanualalloc/css/waitallotlist.css?r=<%=new Random().nextInt() %>" />
	    
	</head>  
 
	<body class="bodywaitmanualallot">
		<div id='divwaitallotfindcondition'>
			<table>
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;<input autocomplete="off" type="text" name="orderno" id="orderno" placeholder="输入订单号后4位" value="${pd.orderno}"/>
	                	<input autocomplete="off" type="text" name="mobile" id="mobile" placeholder="客户手机号" value="${pd.orderno}"/>
	                	&nbsp;&nbsp;&nbsp;&nbsp;<i id="nav-search-refresh" class="icon-refresh"></i>
					</td>
				</tr>
			</table>
		</div>
		
		<hr class="layui-bg-gray">
		
		<div id ='divwaitallotlist'></div>
	 
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/waitmanualalloc/js/waitalloclist.js?r=<%=new Random().nextInt() %>"></script>
	</body>
	
	
</html>