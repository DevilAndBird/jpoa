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
			<form action="problemorder/listProblemOrder" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td>
							<span class="input-icon">
								<input type="text" name="orderno" id="orderno"	 autocomplete="off" placeholder="请输入订单号"  value="${pd.orderno}"/>
							</span>
						</td>
						<td>
							<span class="input-icon">
								 <input type="text" name="cusname" id="cusname"autocomplete="off" placeholder="客户姓名"  value="${pd.cusname}"/>
							</span>
						</td>
						<td>
							<span class="input-icon">
								<input type="text" name="cusmobile" id="cusmobile"   autocomplete="off"  placeholder="客户手机号" value="${pd.cusmobile}"/>
							</span>
						</td>
						<td>
							<span class="input-icon">
								<input type="text" name="baggageid" id="baggageid"   autocomplete="off"  placeholder="行李QR码" value="${pd.baggageid}"/>
							</span>
						</td>
						<td>
							<select name='processstatus'>
								<option value="-1" <c:if test="${pd.processstatus eq '-1' or empty pd.processstatus}">selected="selected"</c:if> >全部</option>
								<option value="UNSOLVED" <c:if test="${pd.processstatus eq 'UNSOLVED'}">selected="selected"</c:if> >未解决</option>
						  	</select>
						</td>
					    <td style="vertical-align:top;">
					    	<button class="btn btn-mini btn-light" title="检 索" >
					    		<i id="nav-search-icon" class="icon-search">检 索</i>
					    	</button>
					    </td>
					</tr>
				</table>
			
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">序号</th>
								<th class="center">订单号</th>
								<th class="center">客户姓名</th>
								<th class="center">客户电话</th>
								<th class="center">未处理</th>
								<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty problemOrderList}">
							<c:forEach items="${problemOrderList}" var="problemOrder" varStatus="vs">
								<tr class='pro${problemOrder.id}'>
									<td class="center">${vs.index+1}</td>
										<td class="center">${problemOrder.orderno}</td>
										<td class="center">${problemOrder.name}</td>
										<td class="center">${problemOrder.mobile}</td>
										<td class="center unsolved">${problemOrder.unsolved}</td>
										<td class="center">
											<p class="btn btn-mini btn-info processlist" onclick="processlist(this)">处理列表</p>
											<input type='hidden' value='${problemOrder.id}' />
											<p class="btn btn-mini btn-info orderdetails" onclick="orderdetails(this)">订单详情</p>
											<input type='hidden' value='${problemOrder.id}' />
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
		
		<script type="text/javascript" src="${ctx}/jpoa/order/problemorder/js/problemorder_list.js?r=<%=new Random().nextInt() %>"></script>
	</body>
</html>

