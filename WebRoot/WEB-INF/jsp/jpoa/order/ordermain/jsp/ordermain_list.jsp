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
				<form action="orderinfo/listOrderInfo" method="post" name="Form" id="Form">
					<table>
						<tr>
							<td>
								<span class="input-icon">
									<input autocomplete="off" type="text" name="orderno" id="orderno" value="${pd.orderno}" placeholder="请输入订单号" />
									<input name="id" id="id" type="hidden"/>
									<input name="orderType" id="orderType" type="hidden"/>
									
									<input name="scrlandmark" id="scrlandmark" type="hidden"/>
									<input name="destlandmark" id="destlandmark" type="hidden"/>
									
									<input name="srcaddress" id="srcaddress" type="hidden"/>
									<input name="destaddress" id="destaddress" type="hidden"/>
								</span>
							</td>
							<td> 
								<span class="input-icon">
									<input autocomplete="off" type="text" name="porderno" id="porderno" value="${pd.porderno}" placeholder="请输入合作伙伴订单编号" />
								</span>
							</td>
							<td> 
								<span class="input-icon"> 
									<input type="text" name="starttime" id="starttime" autocomplete="off" placeholder="请输入开始时间" class='ECalendar' value="${pd.starttime}" />
								</span>
							</td>	
							
							<td>
								<span class="input-icon">
									<input type="text" name="endtime" id="endtime" autocomplete="off" placeholder="请输入结束时间" class='ECalendar' value="${pd.endtime}" />
								</span>
							</td>
						</tr>
						<tr>
					     	<td>
								<span class="input-icon">
									<input autocomplete="off" type="text" name="name" id="name" placeholder="客户姓名" value="${pd.name}"/>
								</span>
							</td>
							<td>
								<span class="input-icon">
									<input autocomplete="off" type="text" name="mobile" id="mobile" placeholder="客户手机号码" value="${pd.mobile}"/>
								</span>
							</td>
							<td>
								<span class="input-icon">
									<input autocomplete="off" type="text" name="baggageid" id="baggageid" placeholder="行李QR码" value="${pd.baggageid}"/>
								</span>
							</td>
							<td>
								<span class="input-icon">
								<select id="seleType_modal"  data-placeholder="任务状态" style="vertical-align:top;width: 200px;" class="classify">
									<option value="-1" selected="selected">--任务类型--</option>
								</select>
								</span>
							</td>
							<td style="vertical-align:top;">
								<button class="btn btn-mini btn-light" title="检 索">
									<i id="nav-search-icon" class="icon-search" onclick="search();">检索</i>
								</button>
<!-- 								<button class="btn btn-mini btn-light" title="导出"> -->
<!-- 									<i id="nav-search-icon" class="icon-download-alt" onclick="toExcel();">导出</i> -->
<!-- 								</button> -->
								<!-- <button class="btn btn-mini btn-light" title="新增" id="saveOrderMain">
									<i id="nav-search-icon" class="icon-search" onclick="toExcel();">新增</i>
								</button> -->
							</td>
						</tr>
					</table>
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">序号</th>
							<!-- 	<th class="center">用户名</th>
								<th class="center">手机号码</th> -->
								<th class="center">订单号</th>
								<th class="center">订单来源</th>
							<!-- 	<th class="center">合作伙伴订单号</th> -->
								<th class="center">寄件人姓名</th>
								<th class="center">寄件人电话</th>
								<th class="center">支付状态</th>
								<th class="center">物流状态</th>
								<th class="center">金额</th>
								<th class="center">优惠券</th>
								<th class="center">实收</th>
								<th class="center">行李数</th>
								<th class="center">城市</th>
								<th class="center">类型</th>
								<th class="center">出发地</th>
								<th class="center">目的地</th>
								<th class="center">寄件时间</th>
								<th class="center">收件时间</th>
								<th class="center">生成日期</th>
								<th class="center">取消订单</th>
								<th class="center">订单明细</th>
							<!-- 	<th class="center">分配取派员</th> -->
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty orderInfoList}">
									<c:forEach items="${orderInfoList}" var="orderInfo"
										varStatus="vs">
										<tr>
											<td class="center">${vs.index+1}</td>
										<%-- 	<td class="center">${orderInfo.cusname}</td>
											<td class="center">${orderInfo.cusmobile}</td> --%>
											<td class="center">${orderInfo.orderno}</td>
											<td class="center">${orderInfo.channeldesc}</td>
											<%-- <td class="center">${orderInfo.porderno}</td> --%>
											<td class="center">${orderInfo.sendername}</td>
											<td class="center">${orderInfo.senderphone}</td>
											<td class="center">${orderInfo.paydesc}</td>
											<td class="center">
												${orderInfo.statusDesc}/
												<button class='btn btn-mini btn-info'  name="updateStatus" value="${orderInfo.id}" data-toggle="modal" data-target="#myModal">状态修改</button>
												<input type="hidden" value="${orderInfo.status}">
											</td>
											<td class="center">${orderInfo.totalmoney}</td>
											<td class="center">${orderInfo.cutmoney}</td>
											<td class="center">${orderInfo.actualmoney}</td>
											<td class="center">${orderInfo.num}</td>
											<td class="center">${orderInfo.srccityname}</td>
											<td class="center">${orderInfo.typedesc}</td>
											<td class="center">
												<c:if test="${!empty orderInfo.scrlandmark}">
													${orderInfo.scrlandmark}/									
												</c:if>
												${orderInfo.srcaddress}
											</td>
											<td class="center">
												<c:if test="${!empty orderInfo.destlandmark}">
													${orderInfo.destlandmark}/									
												</c:if>
												${orderInfo.destaddress}
											</td>
											<td class="center">${orderInfo.taketime}</td>
											<td class="center">${orderInfo.sendtime}</td>
											<td class="center">${orderInfo.addtime}</td>
											<td class="center">
												<button class='btn btn-mini btn-info'  name="cancelOrder" value="${orderInfo.id}" data-toggle="modal" data-target="#myModal">取消</button>
												<input type="hidden" value="${orderInfo.status}">
											</td>
											<td class="center">
												<img  onclick="selectDetail(this)" src='/jpoa/static/img/details_24px.png'>
												<input type='hidden' value='${orderInfo.id}' />
											</td>
											<%-- <td class="center">
												<a onclick="showAddPage(${orderInfo.id})" class='btn btn-mini btn-info' >分配</a>
											</td> --%>
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
								<td style="vertical-align:top;"><div class="pagination"
										style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
					</div>
					
					<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4CancelOrder" class="modal-title">取消订单</h4>
                     <h4 id="h4UpdateStatus" class="modal-title">订单状态修改</h4>
                  </div>
                 <div class="modal-body">
                 	<div id="divCancelOrder">
	                   	<div class="row-fluid">
	                   		<label>请输入退款原因</label>
	                   		 <span class="input-icon">
									<input autocomplete="off" type="text" name="refundreason" id="refundreason" placeholder="退款原因" />
								</span>
						</div>
	                   	<div class="row-fluid">
	                   		<label>请输入退款金额</label>
	                   		 <span class="input-icon">
									<input autocomplete="off" type="text" name="refundmoney" id="refundmoney" placeholder="实际退款金额" />
								</span>
						</div>
					</div>
					<div id="divUpdateStatus">
	                   	<div class="row-fluid">
	                   		<select name="status_modal" id="seleStatus_modal" data-placeholder="订单状态" style="vertical-align:top;width: 170px;">
								<option value="-1" selected="selected">--选择订单状态--</option>
							</select>
						</div>
					</div>
	                 <div class="modal-footer">
	                     <input type='hidden' id="orderroleid" name="orderroleid" />
	                     <input type="hidden" id = "status" name = "status" value="${pd.status}">
	                     <button id="btnCancelDMan" type="button" class="btn btn-primary">确认</button>
	                     <input type="hidden">
	                      <button id="btnUpdateStatus" type="button" class="btn btn-primary">修改</button>
	                 </div>
                 </div><!-- /.modal-content -->
                 
        	 </div><!-- /.modal -->
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
	<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/baseoperation.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/order/ordermain/js/ordermain_list.js?r=<%=new Random().nextInt() %>"></script>
			<script type="text/javascript">
			$(function() {
				ordermain_list.init();
			});
	</script>
</body>
</html>

