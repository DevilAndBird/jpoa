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
		<div>
			<table>
				<tr >
					<td>
						&nbsp;&nbsp;
						<button class="btn btn-mini btn-light" onclick="editInterval()" title="开启实时定位">
				    		<i id="nav-search-icon" class="icon-edit">开启实时定位</i>
				    	</button>
					</td>
					<td>
						&nbsp;&nbsp;
						<button class="btn btn-mini btn-light" onclick="removeInterval()" title="关闭实时定位">
				    		<i id="nav-search-icon" class="icon-remove">关闭实时定位</i>
				    	</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="container"></div>
		<input type="hidden" value="${pd.loginperson_city_lng}" id ="loginperson_city_lng">
		<input type="hidden" value="${pd.loginperson_city_lat}" id ="loginperson_city_lat">
		
		<script type="text/javascript" src="${ctx}/jpoa/delivery/baiduMap/common/js/BAIDUMAP_ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/delivery/baiduMap/dmancurrentgps/js/viewdmancurrentgps.js?r=<%=new Random().nextInt() %>"></script>
	</body>  
</html>