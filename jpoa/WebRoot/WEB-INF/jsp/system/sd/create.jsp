<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cys" uri="/selectlib"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
</head>
<body>

	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="workInfoControl/savedate" method="post"
						name="userForm" id="userForm">
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty userList}">
										<thead>
											<tr>
												<th class="center"><label><input
														type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
												</th>
												<th>名称</th>
												<th>类型</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${userList}" var="user" varStatus="vs">
												<input type="hidden" name="fid" value="${user.fid}">
												<tr>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td>${user.label}</td>
													<c:choose>
														<c:when test="${user.typename == 'datacontrol' }">
															<td><input name="${user.label}"
																class="span10 date-picker" type="text"
																data-date-format="yyyy-mm-dd" style="width:88px;"
																placeholder="日期控件" title="日期" /></td>
														</c:when>
														<c:when test="${user.typename == 'selectcontrol' }">
															<td><cys:select itemcode="${user.itemcode}"
																	name="${user.label}" onchange="province" id="province" />
															</td>
														</c:when>
														<c:when test="${user.typename == 'textareacontrol' }">
															<td><input name="${user.label}" type="textarea"
																placeholder="文本域控件" title="文本域"></td>
														</c:when>
														<c:otherwise>
															<td><input name="${user.label}" type="text"
																placeholder="输入框控件控件" title="输入框"></td>
														</c:otherwise>
													</c:choose>
												</tr>
											</c:forEach>
											<tr>
												<c:choose>
													<c:when test="${not empty sheetList}">
														<td class="left" colspan="4"><select
															name="workflowid">
																<option value="">请选择</option>
																<c:forEach items="${sheetList}" var="sl">
																	<option value="${sl.id}">${sl.workflowname}</option>
																</c:forEach>
														</select></td>
														<tr>
															<td class="center" colspan="4"><input
																class="btn btn-mini btn-primary" type="submit"
																value="保存"> <a class="btn btn-mini btn-danger"
																href="sheetInfo/gocreatePage">取消</a></td>
													</c:when>
													<c:otherwise>
														<td class="center" colspan="4">请先为本表格添加流程</td>
													</c:otherwise>
												</c:choose>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="10" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>

						<div class="page-header position-relative">
							<table style="width:100%;">
								<tr>
									<td style="vertical-align:top;"></td>
									<td style="vertical-align:top;"><div class="pagination"
											style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>

				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->


	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		//保存
		function add(){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>sheetDetail/show6.do?fidtemp='
					+ ${fidtemp};
			diag.Width = 480
			diag.Height = 500;
			diag.CancelEvent = function() { //关闭事件
				if (diag.innerFrame.contentWindow.document
						.getElementById('zhongxin').style.display == 'none') {
					top.jzts();
					setTimeout("self.location=self.location", 100);
				}
				diag.close();
			};
			diag.show();
		}
	</script>

	<script type="text/javascript">
		$(function() {

			//日期框
			$('.date-picker').datepicker();

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//复选框
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});

		});
	</script>

</body>
</html>

