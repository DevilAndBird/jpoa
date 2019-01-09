<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
	     <%@ include file="/static/common/jsp/common.jsp"%>
	</head>
<body>
	<div class="container-fluid" id="main-container">
	 <div id="page-content" class="clearfix">
	   <div class="row-fluid">
			<form action="reportforms/listReportForms" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td>
							<span class="input-icon">
								<input type="text" name="orderno" id="orderno"	 autocomplete="off" placeholder="请输入订单号"  value="${pd.orderno}"/>
							</span>
						</td>
						<td>
							<span class="input-icon">
								 <input type="text" name="starttime" id="starttime"autocomplete="off" placeholder="请输入开始时间" class='ECalendar' value="${pd.starttime}"/>
							</span>
						</td>
						<td>
							<span class="input-icon">
								<input type="text" name="endtime" id="endtime"   autocomplete="off"  placeholder="请输入结束时间"  class='ECalendar' value="${pd.endtime}"/>
							</span>
						</td>
					    <td style="vertical-align:top;">
					    		<button class="btn btn-mini btn-light" title="检 索">
					    			<button style='height: 30px;' class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search">检 索</i></button>
					    	</button>
					    	<button class="btn btn-mini btn-light" title="导出" type="button">
									<i id="nav-search-icon" class="icon-download-alt" onclick="toExcel();">导出</i>
							</button>
					    </td>
					</tr>
				</table>
			
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">序号</th>
								<th class="center">下单时间</th>
								<th class="center">订单来源</th>
								<th class="center">订单状态</th>
								<th class="center">城市</th>
								<th class="center">订单号</th>
								<th class="center">寄件类型</th>
								<th class="center">QR码</th>
								<th class="center">数量</th>
								<th class="center">行李规格</th>
								<th class="center">寄件人</th>
								<th class="center">寄件联系方式</th>
								<th class="center">寄件时间</th>
								<th class="center">寄件地址</th>
								<th class="center">收件人</th>
								<th class="center">收件联系方式</th>
								<th class="center">收件时间</th>
								<th class="center">收件地址</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty reportFormsList}">
							<c:forEach items="${reportFormsList}" var="reportForms" varStatus="vs">
								<tr>
									<td class="center">${vs.index+1}</td>
											<td class="center">${reportForms.adddate}</td>
											<td class="center">${reportForms.channel}</td>
											<td class="center">${reportForms.orderstatus}</td>
											<td class="center">${reportForms.city}</td>
											<td class="center">${reportForms.orderno}</td>
											<td class="center">${reportForms.type}</td>
											<td class="center">${reportForms.baggageid}</td>
											<td class="center">${reportForms.num}</td>
											<td class="center">行李箱</td>
											<td class="center">${reportForms.sendername}</td>
											<td class="center">${reportForms.senderphone}</td>
											<td class="center">${reportForms.taketime}</td>
											<td class="center">${reportForms.scrlandmark}</td>
											<td class="center">${reportForms.receivername}</td>
											<td class="center">${reportForms.receiverphone}</td>
											<td class="center">${reportForms.sendtime}</td>
											<td class="center">${reportForms.destlandmark}</td>
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
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	  </div>
	</div>
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		 <script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
		 <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		 <script type="text/javascript" src="${ctx}/jpoa/reportforms/totalreport/js/totalreport.js?r=<%=new Random().nextInt() %>"></script>
		 
		 <script type="text/javascript">
			$(function() {
				totalreport.init();
			});
			
		</script>
		
	</body>
</html>

