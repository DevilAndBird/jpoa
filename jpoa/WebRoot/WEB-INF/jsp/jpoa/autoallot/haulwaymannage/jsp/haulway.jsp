<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/static/common/jsp/common.jsp"%>
<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#container {
	height: 100%
}
</style>
</head>
<body id="page_start">
	<!-- 开启/关闭实时定位 -->
	<div>
		<table>
			<tr>
				<td>&nbsp;&nbsp;
					<button class="btn btn-mini btn-light" onclick="editInterval(this)"
						title="区域分布图" value="${orderInfoDetail.id}">

						<input type="hidden" id="lng" value="${pd.loginperson_city_lng}" />
						<input type="hidden" id="lat" value="${pd.loginperson_city_lat}" />
						<input type="hidden" id="srcprov" value="${pd.loginperson_provid}" />
						<input type="hidden" id="srccity" value="${pd.loginperson_cityid}" />
						<i id="nav-search-icon" class="icon-edit">区域分布图</i>
					</button>
				</td>
			</tr>
		</table>
	</div>

	<!-- 页面整个table -->
	<table id="table_report"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center">地图信息</th>
				<th class="center">分配信息</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<!-- 具体百度地图 -->
				<th class="center">
					<div id="container"
						style="height:800px;width:900px;border:1px solid black;"></div>
				</th>

				<!-- 具体分配信息 -->
				<th class="center">
					<div class="container-fluid" id="main-container">
						<div id="page-content" class="clearfix">
							<div class="row-fluid">

								<form action="autoallot/toHaulwayPage" method="post"
									name="Form" id="Form">
									<!-- 取派员表格 -->
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td><span class="input-icon"> <input
													autocomplete="off" type="text" name="name" id="name" value="${pd.name}" placeholder="出发地" />
											</span></td>
											<td><span class="input-icon"> <input autocomplete="off" type="text" name="mobile" id="mobile"	value="${pd.mobile}" placeholder="目的地" />
											</span></td>
											<td style="vertical-align:top;">
												<button class="btn btn-mini btn-light" title="检 索"><i id="nav-search-icon" class="icon-search" onclick="search();">查询</i>
											</td>
										</tr>
									</table>
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">序号</th>
						<th class="center">出发地</th>
						<th class="center">目的地</th>
						<th class="center">预览路线</th>
						<th class="center">操作</th>
						<!-- <th class="center">当前动作类型/目的地</th> -->
					</tr>
				</thead>
				<tbody>
					<!-- 取派员信息开始循环 -->
					<c:choose>
						<c:when test="${not empty haulwayList}">
							<c:forEach items="${haulwayList}" var="haulway"
								varStatus="vs">
								<tr>
									<td class="center">${vs.index+1} 
									</td>
									<td class="center">${haulway.srcDesc}</td>
									<td class="center">${haulway.desDesc}</td>
									<td class="center">
								    <button type="button" class="btn btn-mini btn-light" title="预览"  id="pre_${haulway.id}" >
									<i class="icon-delete" >预览</i></button>			
									</td>
									<td class="center">
									<button type="button" class="btn btn-mini btn-light" title="删除"  id="delete_${haulway.id}" >
									<i class="icon-delete" >删除</i></button>
									</td>
								</tr>
							</c:forEach>
						<tr>
						<!-- 分页 -->
						<td style="vertical-align:top;" colspan="8" height="50px">
							<div class="pagination"
								style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
						</td>
					</tr>
					</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="100" class="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
					   <td style="vertical-align:top;" colspan="8" height="50px">
							<!--  --> <a id="toSavePage" data-toggle="modal" data-target="#myModal"
							class="btn btn-small btn-success">新增</a> <a
						</td>
					</tr>
				</tbody>
			</table>

			</form>

			</div>
			</div>
			</div>
			</th>

			</tr>
		</tbody>
	</table>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" class="juzhong" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 id="fenpeiqpy" class="modal-title">各区域物流路线管理</h4>
				</div>
				<div class="modal-body">
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
							<td style="text-align:center;vertical-align:middle;">
							<select id="startTransitCenterid_modal" data-placeholder="集散中心" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--出发地--</option>
									<c:forEach items="${transitCenterList}" var="obj" varStatus="vs">
										<option value="${obj.id}" >${obj.name}</option>
									</c:forEach>
							 </select>
							</td>
							<td style="text-align:center;vertical-align:middle;">
							   <input autocomplete="off" type="text" name="startpan" id="startpan" placeholder="请输入时间间隔"  style="width:120px" />
							</td>
						   </tr>
						  <tr id="startDiv">
								     <td style="text-align:center">
										<span ><font size="2">经过:</font></span>
									</td>
									  <td style="text-align:center">
								     <img id="newLabel" src="/jpoa/static/img/add.png" class="img-circle">
									 </td>
						   </tr>
							<tr>
					    	<td style="text-align:center;vertical-align:middle;">
							<input type="hidden" id="transitCenterList"  value="${transitCenterList}">
							<select id="endTransitCenterid_modal" data-placeholder="集散中心" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--目的地--</option>
									<c:forEach items="${transitCenterList}" var="obj" varStatus="vs">
										<option value="${obj.id}">${obj.name}</option>
									</c:forEach>
							 </select>
							</td>
							<td style="text-align:center;vertical-align:middle;">
							   <input autocomplete="off" type="text" name="endspan" id="endspan"  placeholder="请输入时间间隔" style="width:120px" />
							</td>
						</tr>
						</thead>
						<tbody id="mark">
						</tbody>
					</table>
				</div>
				  <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal" id="btnSaveHaulWay">新增</button>
                 </div>
			</div>
			<!-- /.modal -->
		</div>
	</div>
	<!-- 返回顶部  -->
	<a href="#page_start" id="btn-scroll-up"
		class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	<script type="text/javascript"
		src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript"
		src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript"
		src="${ctx}/jpoa/autoallot/haulwaymannage/js/haulway.js?r=<%=new Random().nextInt() %>"></script>

	<script type="text/javascript"
		src="${ctx}/jpoa/delivery/baiduMap/common/js/BAIDUMAP_ENUM.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript"
		src="${ctx}/jpoa/autoallot/haulwaymannage/js/transitgps.js?r=<%=new Random().nextInt() %>"></script>

	<script type="text/javascript">
		$(function() {
			haulway.init();
			gpsinit(this);
		});
	</script>

</body>
</html>

