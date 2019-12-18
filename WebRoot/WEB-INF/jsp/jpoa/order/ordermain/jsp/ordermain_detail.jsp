<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/static/common/jsp/common.jsp"%>
</head>

<body>
	<div>
	<span style="font-weight: bold;font-size: 20px;">订单基本信息</span>
      <table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center">订单编号</th>
				<th class="center">订单状态</th>
				<th class="center">行李数量</th>
				<th class="center">寄件时间</th>
				<th class="center">收件时间</th>
				<th class="center">寄件时间修改操作</th>
				<th class="center">收件时间修改操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<input class='orderid' type='hidden' value='${resDetails.id}'>
				<td class="center orderno" >${resDetails.orderno}</td>
				<td class="center">${resDetails.statusDesc}</td>
				<td class="center">${resDetails.num}</td>
				<td class="center taketime">${resDetails.taketime}</td>
				<td class="center sendtime">${resDetails.sendtime}</td>
				<td class="center"><a title="编辑" class="btn btn-mini btn-info" onclick="edit_taketime(this)"><i class="icon-edit"></i></a></td>
				<td class="center"><a title="编辑" class="btn btn-mini btn-info" onclick="edit_sendtime(this)"><i class="icon-edit"></i></a></td>
			</tr>
		</tbody>
  	 </table>
	   
	  <span style="font-weight: bold;font-size: 20px;">客户信息</span>
      <table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center">客户姓名</th>
				<th class="center">客户手机号</th>
				<th class="center">客户身份证号</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
		        <c:when test="${not empty resDetails.cusInfo}">
					<tr>
						<td class="center">${resDetails.cusInfo.name}</td>
						<td class="center cusmobile">${resDetails.cusInfo.mobile}</td>
						<td class="center">${resDetails.cusInfo.idno}</td>
					</tr>
				</c:when>
			</c:choose>
		</tbody>
  	 </table>
	   
	  <span style="font-weight: bold;font-size: 20px;">寄件人 + 收件人</span>
      <table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center">寄件类型</th>
				<th class="center">寄件人姓名</th>
				<th class="center">寄件人手机号</th>
				<th class="center">寄件人身份证号</th>
				<th class="center">收件类型</th>
				<th class="center">收件人姓名</th>
				<th class="center">收件人手机号</th>
				<th class="center">收件人身份证号</th>
				<th class="center">修改</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
		        <c:when test="${not empty resDetails.orderAddress}">
					<tr>
						<td class="center">
							<c:choose>
								<c:when test="${resDetails.mailingway eq 'AIRPOSTCOUNTER'}">
									机场柜台
								</c:when>
								<c:when test="${resDetails.mailingway eq 'ONESELF'}">
									本人
								</c:when>
								<c:when test="${resDetails.mailingway eq 'FRONTDESK'}">
									酒店前台
								</c:when>
								<c:when test="${resDetails.mailingway eq 'OTHER'}">
									他人
								</c:when>
							</c:choose>
						</td>
						<td class="center sendername">${resDetails.orderSenderReceiver.sendername}</td>
						<td class="center senderphone">${resDetails.orderSenderReceiver.senderphone}</td>
						<td class="center senderidno">${resDetails.orderSenderReceiver.senderidno}</td>
						<td class="center ">
							<c:choose>
								<c:when test="${resDetails.backway eq 'AIRPOSTCOUNTER'}">
									机场柜台
								</c:when>
								<c:when test="${resDetails.backway eq 'ONESELF'}">
									本人
								</c:when>
								<c:when test="${resDetails.backway eq 'FRONTDESK'}">
									酒店前台
								</c:when>
								<c:when test="${resDetails.backway eq 'OTHER'}">
									他人
								</c:when>
							</c:choose>
						</td>
						<td class="center receivername">${resDetails.orderSenderReceiver.receivername}</td>
						<td class="center receiverphone">${resDetails.orderSenderReceiver.receiverphone}</td>
						<td class="center receiveridno">${resDetails.orderSenderReceiver.receiveridno}</td>
						<td class="center"><a title="编辑" class="btn btn-mini btn-info" onclick="edit(this)"><i class="icon-edit"></i></a></td>
					</tr>
				</c:when>
			</c:choose>
		</tbody>
  	 </table>
	   
	  <span style="font-weight: bold;font-size: 20px;">订单地址</span>
      <table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center">出发地省份</th>
				<th class="center">出发地城市</th>
				<th class="center">出发地类型</th>
				<th class="center">出发地地标</th>
				<th class="center">出发地地址</th>
				<th class="center">修改出发地</th>
				<th class="center">目的地省份</th>
				<th class="center">目的地城市</th>
				<th class="center">目的地类型</th>
				<th class="center">目的地地标</th>
				<th class="center">目的地地址</th>
				<th class="center">修改目的地</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
		        <c:when test="${not empty resDetails.orderAddress}">
					<tr>
						<input class='orderAddrId' type='hidden' value='${resDetails.orderAddress.id}'>
						<td class="center">${resDetails.orderAddress.srcprovname}</td>
						<td class="center">${resDetails.orderAddress.srccityname}</td>
						<input class='srcaddrtype' type='hidden' value='${resDetails.orderAddress.srcaddrtype}' />
						<td class="center srcaddrtypedesc">
							<c:choose>
								<c:when test="${resDetails.orderAddress.srcaddrtype eq 'AIRPORTCOUNTER'}">
									机场柜台
								</c:when>
								<c:when test="${resDetails.orderAddress.srcaddrtype eq 'HOTEL'}">
									酒店
								</c:when>
								<c:when test="${resDetails.orderAddress.srcaddrtype eq 'RESIDENCE'}">
									住宅
								</c:when>
							</c:choose>
						</td>
						<td class="center scrlandmark">${resDetails.orderAddress.scrlandmark}</td>
						<td class="center srcaddress">${resDetails.orderAddress.srcaddress}</td>
						<td class="center"><a title="编辑" class="btn btn-mini btn-info" onclick="modifyaddr_Send(this)"><i class="icon-edit"></i></a></td>
						<td class="center">${resDetails.orderAddress.destprovname}</td>
						<td class="center">${resDetails.orderAddress.destcityname}</td>
						<input class='destaddrtype'  type='hidden' value='${resDetails.orderAddress.destaddrtype}' />
						<td class="center destaddrtypedesc">
							<c:choose>
								<c:when test="${resDetails.orderAddress.destaddrtype eq 'AIRPORTCOUNTER'}">
									机场柜台
								</c:when>
								<c:when test="${resDetails.orderAddress.destaddrtype eq 'HOTEL'}">
									酒店
								</c:when>
								<c:when test="${resDetails.orderAddress.destaddrtype eq 'RESIDENCE'}">
									住宅
								</c:when>
							</c:choose>
						</td>
						<td class="center destlandmark">${resDetails.orderAddress.destlandmark}</td>
						<td class="center destaddress">${resDetails.orderAddress.destaddress}</td>
						<td class="center"><a title="编辑" class="btn btn-mini btn-info" onclick="modifyaddr_Dest(this)"><i class="icon-edit"></i></a></td>
					</tr>
				</c:when>
			</c:choose>
		</tbody>
  	 </table>

       <c:if test="${ resDetails.channel ne 'MU_doortoddoor'}">
	   <span style="font-weight: bold;font-size: 20px;">行李QR码 + 行李照片</span>
       <table id="table_report" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th class="center">行李码</th>
					<th class="center">查看图片</th>
				</tr>
			</thead>
			<tbody>
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty lugQRAndImginfo}">
						<c:forEach items="${lugQRAndImginfo}" var="obj_luginfo">
							<tr>
								<td class="center" style="font-weight: bold;">${obj_luginfo.QR}</td>
								<td class="center">
									<c:choose>
										<c:when test="${not empty obj_luginfo.COOLECT}">
											<button class='btn btn-mini btn-info' onclick="showImg_coolect(this)">收取行李时照片</button>
											<c:forEach items="${obj_luginfo.COOLECT}" var="obj_c">
												<input name='coolect' type='hidden' value='${obj_c}'>
											</c:forEach>
										</c:when>
									</c:choose>
									<c:choose>
										<c:when test="${not empty obj_luginfo.RELEASE}">
											<button class='btn btn-mini btn-info' onclick="showImg_release(this)">释放行李时照片</button>
											<c:forEach items="${obj_luginfo.RELEASE}" var="obj_r" >
												<input name='release' type='hidden' value='${obj_r}'>
											</c:forEach>
										</c:when>
									</c:choose>
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
	   </c:if>

		<c:if test="${ resDetails.channel eq 'MU_doortoddoor'}">
			<span style="font-weight: bold;font-size: 20px;">查看客户上传行李照片</span>
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
				<tr>
					<th class="center">上传照片</th>
				</tr>
				</thead>
				<tbody>
					<tr>
						<td class="center">
							<input type="hidden" class="remark" value='${resDetails.remark}' />
							<button class='btn btn-mini btn-info' id="lookCusLugPhoto">查看</button>
						</td>
					</tr>
				</tbody>
			</table>
		</c:if>

      <span style="font-weight: bold;font-size: 20px;">订单备注(<a class="btn btn-small btn-success" id="saveNotes">新增备注</a>)</span>
      <table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center">备注人</th>
				<th class="center">备注信息</th>
				<th class="center">修改备注</th>
			</tr>
		</thead>
		<tbody class="noteslist">
			<!-- 开始循环 -->
			<c:choose>
		        <c:when test="${not empty resDetails.orderNotesInfoList}">
					<c:forEach items="${resDetails.orderNotesInfoList}" var="orderNotes" varStatus="vs">
						<tr>
							<input id='noteid${orderNotes.id}'  type='hidden' value='${orderNotes.id}'/>
							<td class="center">${orderNotes.addusername}</td>
							<td class="center notes">${orderNotes.notes}</td>
							<td class="center"><a title="编辑" class="btn btn-mini btn-info" onclick="editNotes(this)"><i class="icon-edit"></i></a></td>
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

<c:if test="${ resDetails.channel eq 'MU_doortoddoor'}">
	<span style="font-weight: bold;font-size: 20px;">行李门到门短信发送</span>
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center">取件成功</th>
			<th class="center">送达深圳机场</th>
			<th class="center">从上海机场取到行李</th>
			<th class="center">开始配送行李</th>
			<th class="center">行李送达到客户</th>
		</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${not empty resDetails.orderPriceDetatils}">
				<tr>
					<td class="center">
						<button class='btn btn-mini btn-info' id="sms0">发送短信</button>
					</td>
					<td class="center">
						<button class='btn btn-mini btn-info' id="sms1">发送短信</button>
					</td>
					<td class="center">
						<button class='btn btn-mini btn-info' id="sms2">发送短信</button>
					</td>
					<td class="center">
						<button class='btn btn-mini btn-info' id="sms4">发送短信</button>
					</td>
					<td class="center">
						<button class='btn btn-mini btn-info' id="sms3">发送短信</button>
					</td>
				</tr>
			</c:when>
		</c:choose>
		</tbody>
	</table>
</c:if>

		<c:if test="${ resDetails.channel ne 'MU_doortoddoor'}">
			 <span style="font-weight: bold;font-size: 20px;">价格明细</span>
			  <table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">基础运费</th>
						<th class="center">保价费</th>
						<th class="center">其他费用</th>
						<th class="center">合计</th>
						<th class="center">优惠金额</th>
						<th class="center">实收</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty resDetails.orderPriceDetatils}">
							<tr>
								<td class="center">${resDetails.orderPriceDetatils.baseMoney}</td>
								<td class="center">${resDetails.orderPriceDetatils.prem}</td>
								<td class="center">${resDetails.orderPriceDetatils.extraMoney}</td>
								<td class="center">${resDetails.orderPriceDetatils.totalMoney}</td>
								<td class="center">${resDetails.orderPriceDetatils.cutMoney}</td>
								<td class="center" style="color: #D21736">${resDetails.orderPriceDetatils.actualMoney}</td>
							</tr>
						</c:when>
					</c:choose>
				</tbody>
			 </table>
		</c:if>

	<c:choose>
		<c:when test="${not empty resDetails.actiondetail}">
	 <span style="font-weight: bold;font-size: 20px;">动作明细</span>
      <table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center">处理人</th>
				<th class="center">处理人电话</th>
				<th class="center">处理人类型</th>
				<th class="center">角色动作类型</th>
				<th class="center">角色动作是否完成</th>
				<th class="center">最迟到达时间</th>
				<th class="center">实际完成时间</th>
				<th class="center">目的地</th>
			</tr>
		</thead>
		<tbody class='actiondetail'>
			<!-- 开始循环 -->
			
					<c:forEach items="${resDetails.actiondetail}" var="action" varStatus="vs">
						<tr>
							<td class="center">${action.name}</td>
							<td class="center">${action.mobile}</td>
							<td class="center">${action.usertypeDesc}</td>
							<td class="center">${action.roletypename}</td>
							<td class="center">${action.isfinishDesc}</td>
							<td class="center">
								<c:if test="${not empty action.arrivedtime}">
	        						${action.arrivedtime}
	        					</c:if>
						    </td>
							<td class="center">
								<c:if test="${not empty action.actionfinishtime}">
	        						${action.actionfinishtime}
	        					</c:if>
							</td>
							<td class="center">
								<c:if test="${not empty action.destaddrname}">
	        						${action.destaddrname} 
	        					</c:if>
	        					<c:if test="${not empty action.destaddrdesc}">
	        						${action.destaddrdesc}
	        					</c:if>
							</td>
						</tr>
					</c:forEach>
			
		</tbody>
  	 </table>
		</c:when>
	</c:choose>

  </div>
  
   <c:import url="./order_details_update.jsp"/>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/order/ordermain/js/ordermain_details.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/order/ordermain/js/ordermain_details_update.js?r=<%=new Random().nextInt() %>"></script>
</body>
</html>

