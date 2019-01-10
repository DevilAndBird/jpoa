<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/static/common/jsp/common.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<form action="configcenter/configCenterlistPage" method="post" name="Form" id="Form">
					<table>
						<tr>
							<td style="vertical-align:top;"> 
							 	<select name="provid" id="seleProv" data-placeholder="省份" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--省份--</option>
									<c:forEach items="${provList}" var="obj" varStatus="vs">
										<option value="${obj.id }" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name }</option>
									</c:forEach>
							  	</select>
							</td>
							<td style="vertical-align:top;">
								<input type='hidden' id='hidden_cityid' value='${pd.cityid}'> 
							 	<select name="cityid" id="seleCity" data-placeholder="城市" style="vertical-align:top;width: 120px;" >
									<option value="-1">--城市--</option>
							  	</select>
							</td>
							<td> 
								<span class="input-icon">
									<input autocomplete="off" type="text" name="business_key" value="${pd.business_key}" placeholder="业务key" />
								</span>
							</td>
							<td style="vertical-align:top;"> 
							 	<select name="isvalid" id="isvalid" data-placeholder="是否有效" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--是否有效--</option>
									<option value="Y" <c:if test="${pd.isvalid=='Y'}">selected</c:if>>有效</option>
									<option value="N" <c:if test="${pd.isvalid=='N'}">selected</c:if>>无效</option>
							  	</select>
							</td>
							<td style="vertical-align:top;">
								<button style="height: 30px;" class="btn btn-mini btn-light" title="检 索">
									<i id="nav-search-icon" class="icon-search" onclick="search();">检索</i>
								</button>
							</td>
						</tr>
					</table>
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">序号</th>
								<th class="center">城市id</th>
								<th class="center">业务key</th>
								<th class="center">业务value</th>
								<th class="center">是否有效</th>
								<th class="center">备注</th>
								<th class="center">操作</th>
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty configCenterList}">
									<c:forEach items="${configCenterList}" var="obj" varStatus="vs">
										<tr>
											<td class="center">${vs.index+1}</td>
											<input type='hidden' id='hiddenid' value ='${obj.id}'>
											<input type='hidden' id='hiddencityid' value ='${obj.cityid}'>
											<td class="center cityname">${obj.cityname}</td>
											<td class="center business_key">${obj.business_key}</td>
											<td class="center business_value">${obj.business_value}</td>
											<input type ='hidden' id='hiddenisvalid' value='${obj.isvalid}'>
											<td class="center isvalid">
												<c:choose>
													<c:when test="${obj.isvalid == 'Y'}">
														有效
													</c:when>
													<c:otherwise>
														无效
													</c:otherwise>
												</c:choose>
											</td>
											<td class="center remark">${obj.remark}</td>
											<td class="center">
												<a title="编辑" class='btn btn-mini btn-info' onclick="edit_yes(this);" ><i class='icon-edit'>编辑</i></a>
												<a title="刷新" class='btn btn-mini btn-info' onclick="reflush_yes(this);" ><i class='icon-edit'>刷新</i></a>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td >
									<a title="新增" class="btn btn-small btn-success" onclick="save_yes(this);" >新增</a>
								</td>
								<td style="vertical-align:top;">
									<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
								</td>
							</tr>
						</table>
					</div>
					
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 class="modal-title">配置中心</h4>
      			</div>
                 <div class="modal-body">
                 	<div id="divmodify">
                 		<input autocomplete="off" type="hidden" id='model_cityid'/>
                 		<input autocomplete="off" type="hidden" id='model_businesskey'/>
						<div class="row-fluid">
	                   		<label>业务value</label>
	                   		 <span class="input-icon">
								<input autocomplete="off"  type="text" id='model_businessvalue'  placeholder="业务value" />
							 </span>
						</div>
						<div class="row-fluid">
	                   		<label>是否有效</label>
	                   		 <span class="input-icon">
								<select  id="model_isvalid" data-placeholder="是否有效" style="vertical-align:top;width: 120px;">
									<option value="Y" selected="selected">有效</option>
									<option value="N">无效</option>
							  	</select>
							 </span>
						</div>
						<div class="row-fluid">
	                   		<label>备注信息</label>
	                   		 <span class="input-icon">
								<input autocomplete="off"  type="text" id='model_remark'  placeholder="业务value" />
							 </span>
						</div>
					</div>
					<div id="divsave">
	                   	<div class="row-fluid">
						 	<select id="seleProvid_modal" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
						  	</select>
						 	<select id="seleCityid_modal" data-placeholder="城市" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
						  	</select>
						</div>
						<div class="row-fluid">
	                   		<label>业务key</label>
	                   		 <span class="input-icon">
								<input autocomplete="off" type="text" id='model_key'  placeholder="业务key" />
							 </span>
						</div>
						<div class="row-fluid">
	                   		<label>业务value</label>
	                   		 <span class="input-icon">
								<input autocomplete="off"  type="text" id='model_value'  placeholder="业务value" />
							 </span>
						</div>
						<div class="row-fluid">
	                   		<label>备注</label>
	                   		 <span class="input-icon">
								<input autocomplete="off"  type="text" id="model_remark_save"  placeholder="备注" />
							 </span>
						</div>
						<div class="row-fluid">
							<label>1:配置默认值，城市信息不需要填写</label>
							<label>2:配置默认值，key后面跟着_DEFAULT,即xx_DEFAULT</label>
						</div>
					</div>
                 </div>
                 <div class="modal-footer">
                      <button id="butmodifyconfig" type="button" class="btn btn-primary">确认修改</button>
                      <button id="butsaveconfig" type="button" class="btn btn-primary">确认保存</button>
                 </div>
        	 </div>
		</div>
		</div>		
		
					
				</form>
			</div>
		</div>
	</div>
	
	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	
	
	<script type="text/javascript" src="${ctx}/jpoa/common/js/baseoperation.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/config/configcenter/js/configcenter_list.js?r=<%=new Random().nextInt() %>"></script>
</body>
</html>

