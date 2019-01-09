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
		<div id="addTool">
			<table>
				<tr >
					<td>
						<select id="seleDrawColor" style="width: 120px;"></select>
					</td>
					<td>
						&nbsp;&nbsp;
						<button class="btn btn-mini btn-light" onclick="draw()" title="画多边形">
				    		<i id="nav-search-icon" class="icon-pencil">画多边形</i>
				    	</button>
					</td>
					<td>
						&nbsp;&nbsp;
						<button class="btn btn-mini btn-light" onclick="saveRegionCityPoint()" title="保存所画区域" data-toggle="modal" data-target="#myModal">
				    		<i id="nav-search-icon" class="icon-save">保存所画区域</i>
				    	</button>
					</td>
					<td>
						&nbsp;&nbsp;
						<button class="btn btn-mini btn-light" onclick="clearAll()" title="清除">
				    		<i id="nav-search-icon" class="icon-reply">清除</i>
				    	</button>
					</td>
					<td>
						&nbsp;&nbsp;
						<button class="btn btn-mini btn-light" onclick="Editing('enable')" title="检 索">
				    		<i id="nav-search-icon" class="icon-edit">开启区域修改</i>
				    	</button>
				    	<button class="btn btn-mini btn-light" onclick="Editing('disable')" title="检 索">
				    		<i id="nav-search-icon" class="icon-remove">关闭区域修改</i>
				    	</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="container"></div>
		
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4DeliveryMan" class="modal-title">新增市级区域划分</h4>
                    <!--  <h4 id="saveUserDeliveryManH4" class="modal-title">第二步：新增取派员信息</h4>
                     <h4 id="saveAppUserH4" class="modal-title">第一步：新增登陆用户信息</h4> -->
                 </div>
                 <div class="modal-body">
	                  <div class="row-fluid">
							<select name="provid_modal" id="seleProvid_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name}</option>
								</c:forEach>
							</select>
							<select name="cityid_modal" id="seleCityid_modal" data-placeholder="城市" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
							</select>
						</div>
						<div>
					    	<select id="seleDrawColor_modal" data-placeholder="绘制颜色" style="vertical-align:top;width: 120px;"></select>
					    </div>
					    <div>
					    	<input type="text" id="gps_modal" style="width:500px;" readonly="readonly">
					    </div>
				</div>
             </div>
             <div class="modal-footer">
                 <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                 <button id="btnSaveRegionCityGps" type="button" class="btn btn-primary">新增</button>
             </div>
         </div>
         </div>
		
		<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/delivery/baiduMap/common/js/BAIDUMAP_ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/delivery/baiduMap/regioncity/js/addregioncity.js?r=<%=new Random().nextInt() %>"></script>
		
		
		<script type="text/javascript"> 
	     	 $(function(){
	     		addregioncityMapInit();
	     	 });
		</script>
	</body>  
</html>