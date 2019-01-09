<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4AppUser" class="modal-title">编辑角色登陆信息</h4>
                 </div>
                 <div class="modal-body">
                 	<div id="divAppUser">
						<div class="row-fluid">
							<input type="text" id="inpName_modal" placeholder="登陆用户名"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpPassword_modal" placeholder="登陆用户密码"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpMobile_modal" placeholder="登陆手机号"/> 
						</div>
						<div class="row-fluid">
							<select id="seleAppUserType_modal" data-placeholder="是否可用" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--类型--</option>
							</select>
						</div>
						
						<div class="row-fluid">
							<select id="seleIsvalid_modal" data-placeholder="是否可用" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--是否有效--</option>
							</select>
						</div>
					</div>
					
					
					<div id="divDeliveryMan">
						<div class="row-fluid">
							<select id="seleDmanProvid_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name}</option>
								</c:forEach>
							</select>
							<select id="seleDmanCityid_modal" data-placeholder="城市" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
							</select>
						</div>
						
						<div class="row-fluid"> 
							<select id="seleDmanRegion_modal" data-placeholder="营业区域" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">-营业区域--</option>
								<c:forEach items="${regionDeliveryList}" var="obj" varStatus="vs">
									<option value="${obj.id}">${obj.regionname}</option>
								</c:forEach>
							 </select>
						</div>
					    
						<div class="row-fluid">
							<input type="text" id="inpDManName_modal" placeholder="取派员姓名"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpDManIdno_modal" placeholder="取派员证件号"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpDManMobile_modal" placeholder="取派员手机号"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpDManplatenumber_modal" placeholder="取派员车牌号"/> 
						</div>
						
					</div>
					<div id="divLinkTransitCenter">
						<select id="seleLinkTransitid_modal" data-placeholder="集散中心" style="vertical-align:top;width: 170px;">
								<option value="-1" selected="selected">--关联到集散中心--</option>
								<c:forEach items="${transitCenterList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.transitCenterid}">selected</c:if>>${obj.name}</option>
								</c:forEach>
						</select>
					</div>
					
					<div id="divPicker">
						<div class="row-fluid">
							<input type="text" id="inpPickerName_modal" placeholder="机场取件员用户名"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpPickerIdno_modal" placeholder="机场取件员证件号"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpPickerMobile_modal" placeholder="机场取件员手机号"/> 
						</div>
						<div class="row-fluid">
							<select id="seleAirportcode_modal" data-placeholder="机场" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--机场--</option>
								<c:forEach items="${airprotInfoConfigList}" var="obj" varStatus="vs">
									<option value="${obj.id}">${obj.airportname}</option>
								</c:forEach>
						  	</select>
					  	</div>
					</div>
					
					<div id="divLinkServiceCenter">
						<div class="row-fluid">
							<select id="seleLinkServicecenter_modal" data-placeholder="服务中心" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--关联服务中心--</option>
								<c:forEach items="${serviceCenterList}" var="obj" varStatus="vs">
									<option value="${obj.id}">${obj.servicecentername}</option>
								</c:forEach>
					 		</select>
					 	</div>
					</div>
					
					<div id="divDriver">
						<div class="row-fluid">
							<input type="text" id="inpDriverName_modal" placeholder="班车司机姓名"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpDriverIdno_modal" placeholder="班车司机身份证号"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="inpDriverMobile_modal" placeholder="班车司机手机号"/> 
						</div>
					</div>
                 </div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="btnUpdateAppUser" type="button" class="btn btn-primary">更改</button>
                     <button id="btnSaveAppUser" type="button" class="btn btn-primary">新增</button>
                     <button id="btnSaveDman" type="button" class="btn btn-primary">新增</button>
                     <button id="btnLinkTransitCenter" type="button" class="btn btn-primary">关联</button>
                     <button id="btnSavePicker" type="button" class="btn btn-primary">新增</button>
                     <button id="btnlinkServiceCenter" type="button" class="btn btn-primary">关联</button>
                     <button id="btnSaveDriver" type="button" class="btn btn-primary">新增</button>
                 </div>
             </div><!-- /.modal-content -->
         </div><!-- /.modal -->
</div>
