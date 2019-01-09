<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>  
<html>
	<head>
		<%@ include file="/static/common/jsp/common.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/jpoa/controlplatfrom/autoallot/css/autoallotlist.css?r=<%=new Random().nextInt() %>" />
	</head>  
 
	<body class="bodyautoallot">
		<div id='divautoallotfindcondition'>
			<table>
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;<input autocomplete="off" type="text" name="orderno" id="orderno" placeholder="订单号后4位" value="${pd.orderno}"/>
	                	<input autocomplete="off" type="text" name="mobile" id="mobile" placeholder="客户手机号" value="${pd.mobile}"/>
	                	<select name="timeScreenType" id='timeScreenType'>
						  <option value="-1">选择时间筛选类型</option>
						  <option value="COMMONSCREEN">普通</option>
						  <option value="TRANSITSCREEN">集散</option>
						</select>     
						<input autocomplete="off" type="text" name="screenmin" id="screenmin" placeholder="1~90min筛选" value="${pd.screenmin}"/>
	                	&nbsp;&nbsp;&nbsp;&nbsp;<i id="nav-search-refresh" class="icon-refresh"></i>
					</td>
				</tr>
			</table>
		</div>
		
		<hr/>
			
		<div id ='divAutoallotlist'>
		</div>
	    
	    <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/autoallot/js/autoallotlist.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>