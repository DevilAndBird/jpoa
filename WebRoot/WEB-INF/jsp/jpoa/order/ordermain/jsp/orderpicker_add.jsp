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
				<form action="orderinfo/listOrderUserPicker" method="post" name="Form"
					id="Form">
					<table>
						<tr>
							<td><input autocomplete="off" type="text" name="name"
								id="name" placeholder="快递员关键字" /></td>
							<td style="vertical-align:top;"><button
									class="btn btn-mini btn-light" onclick="search();" title="查询">
									查询 <i id="nav-search-icon" class="icon-search"></i>
								</button> <input id="id" name="id" value="${pd.id}" type="hidden" /></td>
						</tr>
					</table>
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">序号</th>
								<th class="center">取件员名称</th>
								<th class="center">电话</th>
								<th class="center">机场</th>
								<th class="center">选择</th>
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty userPickerList}">
									<c:forEach items="${userPickerList}" var="userPicker"
										varStatus="vs">
										<tr>
											<td class="center">${vs.index+1}</td>
											<td class="center">${userPicker.name}</td>
											<td class="center">${userPicker.mobile}</td>
											<td class="center">${userPicker.airportname}</td>
											<td class='center' style="width: 30px;"><c:choose>
													<c:when test="${not empty orderUserDelivery}">
														<c:set var="isDoing" value="0" />
														<c:forEach items="${orderUserDelivery}"
															var="orderUserDelivery">
															<c:if test="${userPicker.id==orderUserDelivery.roleid}">
																<label><input type='checkbox'
																	id='checkbox${userPicker.id}' name='ids'
																	value="${userPicker.id}" checked="checked" /><span
																	class="lbl"></span></label>
																<c:set var="isDoing" value="1" />
															</c:if>
														</c:forEach>
														<c:if test="${isDoing!=1}">
															<label><input type='checkbox'
																id='checkbox${userPicker.id}' name='ids'
																value="${userPicker.id}" /><span class="lbl"></span></label>
														</c:if>
													</c:when>
													<c:otherwise>
														<label><input type='checkbox'
															id='checkbox${userPicker.id}' name='ids'
															value="${userPicker.id}" /><span class="lbl"></span></label>
													</c:otherwise>
												</c:choose></td>
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
								<td style="vertical-align:top;"><a id="btnSaveUserPicker"
									class="btn btn-small btn-success" data-toggle="modal"
									data-target="#myModal">保存</a> <a onclick="returnMainPage()"
									class="btn btn-small btn-success" data-toggle="modal"
									data-target="#myModal">返回</a></td>
								<td style="vertical-align:top;"><div class="pagination"
										style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	<script type="text/javascript"
		src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript"
		src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript"
		src="${ctx}/jpoa/order/ordermain/js/orderpicker_add.js?r=<%=new Random().nextInt() %>"></script>

	<script type="text/javascript">
		$(function() {
			orderpiker_add.init();
		});
	</script>

</body>
</html>

