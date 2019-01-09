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
						&nbsp;&nbsp;
						<button class="btn btn-mini btn-light" onclick="editRegionDmanPoint()" title="保存所画区域" data-toggle="modal" data-target="#myModal">
				    		<i id="nav-search-icon" class="icon-save">修改所画区域</i>
				    	</button>
				    	<input id="hidId" type='hidden' value="${pd.id}" />
				    	<input id="hidDeliverymanid" type='hidden'/>
				    	<input id="hidColorid" type='hidden'/>
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
                     <h4 id="h4DeliveryMan" class="modal-title">修改市级区域划分</h4>
                 </div>
                 <div class="modal-body">
						<div>
							<input type="text" id="regionname" placeholder="请输入区域名" value="${pd.regionname}"/> 
						</div>
	                     <div>
                          	<select name="provid" id="provid" data-placeholder="省份" style="vertical-align:top;width: 120px;" disabled="disabled">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name }</option>
								</c:forEach>
						  	</select>
						  		<select name="cityid" id="cityid" data-placeholder="" style="vertical-align:top;width: 120px;" disabled="disabled">
								<option value="${pd.cityid}" selected="selected">${pd.cityname}</option>
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
                 <button id="btnEditRegionDman" type="button" class="btn btn-primary">修改</button>
             </div>
         </div>
         </div>
		
		<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/delivery/baiduMap/common/js/BAIDUMAP_ENUM.js?r=<%=new Random().nextInt() %>"></script>
		<script type="text/javascript" src="${ctx}/jpoa/delivery/baiduMap/regiondeliveryman/js/editregiondman.js?r=<%=new Random().nextInt() %>"></script>
		
		<script type="text/javascript"> 
	     	 $(function(){
	     		editRegionDmanInit();
	     	 });
		</script>
	</body>  
</html>